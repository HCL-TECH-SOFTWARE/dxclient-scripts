@echo off
SETLOCAL EnableDelayedExpansion
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:: Licensed to the Apache Software Foundation (ASF) under one  
:: or more contributor license agreements.  See the NOTICE file
:: distributed with this work for additional information       
:: regarding copyright ownership.  The ASF licenses this file  
:: to you under the Apache License, Version 2.0 (the           
:: "License"); you may not use this file except in compliance  
:: with the License.  You may obtain a copy of the License at  
::                                                             
::   http://www.apache.org/licenses/LICENSE-2.0                
::                                                             
:: Unless required by applicable law or agreed to in writing,  
:: software distributed under the License is distributed on an 
:: "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY      
:: KIND, either express or implied.  See the License for the   
:: specific language governing permissions and limitations     
:: under the License.                                          
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:: The script is a wrapper to run dxclient tool. 
::

SET IMAGE_NAME=hclcr.io/dx-public/dxclient
SET IMAGE_TAG=<IMAGE_TAG>
SET DXCLIENT=dxclient
SET DATA_DIR=store

IF [!IMAGE_NAME!]==[] (
	echo "Please set dxclient image name."
	EXIT /B 0
)
IF [!IMAGE_TAG!]==[] (
	echo "Please set dxclient image tag."
	EXIT /B 0
)
IF [!CONTAINER_RUNTIME!]==[] (
	SET CONTAINER_RUNTIME=docker
)
IF [!VOLUME_DIR!]==[] (
	SET VOLUME_DIR=store
)
:: allows calling program (i.e: gradle) to not use interactive param for non-TTY type env
SET "INTERACTIVE=-it"
IF ["!DXCLIENT_DISABLE_INTERACTIVE!"]==["true"] (
	SET "INTERACTIVE="
)

:: Guards (checks for dependencies)
CALL :check_cmd_in_path !CONTAINER_RUNTIME!

:: extract execution command arguments into a variable
CALL SET execCmd=%*

:: creates volume directory if not present
if NOT EXIST "%cd%"/!VOLUME_DIR! (
    mkdir "!VOLUME_DIR!"
)

:: mount volume directory
CALL SET volumeParams=-v "%cd%\!VOLUME_DIR!\:/!DXCLIENT!/!DATA_DIR!":Z

CALL SET "newCmd="

:: curated set of environmental variables
CALL SET environmentVars= -e TZ=!Timezone! -e VOLUME_DIR="!VOLUME_DIR!"

:: Setting search value "\" for validating filepath
CALL SET searchVal=\

:: Generate a separate volume point for each folder or file in the parameter
:: Replace the folders in the parameter with the assigned container folder in volume parameter
 FOR %%i IN (!execCmd!) DO (
	CALL SET input=%%i
	IF EXIST %%i (
		:: If input doesnot contain "\" it should not replace the host path with container path
        IF NOT "!input:%searchVal%=!"=="!input!" (
            CALL SET "newCmd=!newCmd! ^"/!DXCLIENT!/!DATA_DIR!/%%~nxi^""
            CALL SET volumeParams=!volumeParams! -v "%%~fi:/!DXCLIENT!/!DATA_DIR!/%%~nxi":Z
        ) ELSE (
            CALL SET "newCmd=!newCmd! %%i"
        )
	) ELSE (
		CALL SET "newCmd=!newCmd! %%i"
	)
	IF [%%i]==[livesync] (
		CALL SET environmentVars=!environmentVars! -e CHOKIDAR_USEPOLLING=true
	)
)

CALL !CONTAINER_RUNTIME! run !INTERACTIVE! !environmentVars! !volumeParams! --network="host" --name !DXCLIENT! --rm !IMAGE_NAME!:!IMAGE_TAG! ./bin/dxclient !newCmd!

:: Cleanup the files created in local volume/mount point if the command is livesync
IF %1 == livesync (
	for %%i in (!execCmd!) do (
		IF EXIST %%~si\NUL (
			rmdir /s /q "%cd%\!VOLUME_DIR!\%%~nxi"
		) ELSE IF EXIST %%i (
			del "%cd%\!VOLUME_DIR!\%%~nxi"
		)
	)
)

EXIT /B %ERRORLEVEL%

:check_cmd_in_path
  SET cmd=%1%
  where !cmd! > NUL || if %errorlevel% GEQ 1 ECHO "!cmd! not found!"
EXIT /B 0
