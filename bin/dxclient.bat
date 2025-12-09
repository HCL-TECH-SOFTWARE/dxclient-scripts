@echo off
SETLOCAL EnableDelayedExpansion
:: 
::********************************************************************
:: Licensed Materials - Property of HCL                              *
::                                                                   *
::  Copyright HCL Technologies Ltd. 2021, 2025. All Rights Reserved. *
::                                                                   *
::  Note to US Government Users Restricted Rights:                   *
::                                                                   *
::  Use, duplication or disclosure restricted by GSA ADP Schedule    *
:: *******************************************************************
::
:: The script is a wrapper to run dxclient tool.
::

SET IMAGE_NAME=hclcr.io/dx-public/dxclient
set IMAGE_TAG=v95_CF231_20251024-1354
SET DXCLIENT=dxclient
SET DATA_DIR=store
SET MOUNTED_VOL=

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
CALL SET volumeParams=-v "%cd%\!VOLUME_DIR!\:/!DXCLIENT!/!VOLUME_DIR!":Z

CALL SET "newCmd="

:: curated set of environmental variables
CALL SET environmentVars= -e TZ=!Timezone! -e VOLUME_DIR="!VOLUME_DIR!"

:: Check if the environment variable NODE_EXTRA_CA_CERTS is set
if defined NODE_EXTRA_CA_CERTS (
    for %%F in ("!NODE_EXTRA_CA_CERTS!") do (
        set "filename=%%~nxF"
    ) 
    CALL set "volumeParams=!volumeParams! -v "!NODE_EXTRA_CA_CERTS!:/etc/ssl/certs/!filename!":Z"
)

:: Setting search value "\" for validating filepath
CALL SET searchVal=\
SET hostPath=%CD%
SET hostImportPath=""
set exportPathExists=0
set count=0

:loop
if "%~1"=="" goto :done
set /A count+=1
if /I "%~1"=="-exportPath" (
    set exportPathExists=1
    if not "%~2"=="" (
        set hostPath="%~2"
        shift
    )
) else if /I "%~1"=="--exportPath" (
    set exportPathExists=1
    if not "%~2"=="" (
        set hostPath="%~2"
        shift
    )
) else if /I "%~1"=="-themePath" (
    set exportPathExists=1
    if not "%~2"=="" (
        set hostPath="%~2"
        shift
    )
) else if /I "%~1"=="--themePath" (
    set exportPathExists=1
    if not "%~2"=="" (
        set hostPath="%~2"
        shift
    )
) else if /I "%~1"=="-wcmLibraryPath" (
    set exportPathExists=1
    if not "%~2"=="" (
        set hostPath="%~2"
        shift
    )
) else if /I "%~1"=="--libraryPath" (
    set exportPathExists=1
    if not "%~2"=="" (
        set hostPath="%~2"
        shift
    )
)else if /I "%~1"=="-importPath" (
    set exportPathExists=1
    if not "%~2"=="" (
        set hostImportPath="%~2"
        shift
    )
) else if /I "%~1"=="--importPath" (
    set exportPathExists=1
    if not "%~2"=="" (
        set hostImportPath="%~2"
        shift
    )
)
if "%~1"=="" goto :done
set /A count+=1
shift
goto :loop
:done

:: Generate a separate volume point for each folder or file in the parameter
:: Replace the folders in the parameter with the assigned container folder in volume parameter
 FOR %%i IN (!execCmd!) DO (
	CALL SET input=%%i
	IF EXIST %%i (
		:: If input doesnot contain "\" it should not replace the host path with container path
        IF NOT "!input:%searchVal%=!"=="!input!" (
            CALL SET "newCmd=!newCmd! ^"/!DXCLIENT!/!VOLUME_DIR!/%%~nxi^""
            CALL SET volumeParams=!volumeParams! -v "%%~fi:/!DXCLIENT!/!VOLUME_DIR!/%%~nxi":Z
			SET MOUNTED_VOL=!MOUNTED_VOL! "%%~fi"
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

CALL SET environmentVars=!environmentVars! -e DXCLIENT_RUNTIME=!CONTAINER_RUNTIME! -e HOST_PATH=!hostPath! -e HOST_IMPORT_PATH=!hostImportPath!
CALL !CONTAINER_RUNTIME! run !INTERACTIVE! !environmentVars! !volumeParams! --network="host" --name !DXCLIENT! --rm !IMAGE_NAME!:!IMAGE_TAG! ./bin/dxclient !newCmd!

:: Cleanup the files created in local volume/mount point if the command is livesync
IF %1 == livesync set result=true
IF %2 == export-assets set result=true
IF %result% == true (
	for %%i in (!MOUNTED_VOL!) do (
		IF EXIST %%~si\NUL (
			SET ABS_PATH=%%~fi
			for %%A in (!ABS_PATH!) do (
				SET ORIGIN="%%~dpA"
			)
			IF NOT !ORIGIN! == "%cd%\!VOLUME_DIR!\" (
				IF EXIST "%cd%\!VOLUME_DIR!\%%~nxi" (
					rmdir /s /q "%cd%\!VOLUME_DIR!\%%~nxi"
				)
			)
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
