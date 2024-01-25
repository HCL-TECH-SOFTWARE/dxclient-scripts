/*
 ********************************************************************
 * Licensed Materials - Property of HCL                             *
 *                                                                  *
 * Copyright HCL Technologies Ltd. 2021. All Rights Reserved.       *
 *                                                                  *
 * Note to US Government Users Restricted Rights:                   *
 *                                                                  *
 * Use, duplication or disclosure restricted by GSA ADP Schedule    *
 ********************************************************************
 */

pipeline {
  agent {
    label "${AGENT_LABEL}"
  }
  parameters {
    // Generic parameters
      string(name: 'TOOL_PACKAGE_URL', defaultValue: '', description: 'URL from which to download dxclient zip',  trim: false)
      string(name: 'TOOL_CREDENTIALS_ID', defaultValue: '', description: 'ID in Jenkins store to user name / password credentials needed to access tool package URL',  trim: false)
      string(name: 'ARTIFACT_PATH', defaultValue: '', description: 'URL (except filename) from which to download artefacts to be deployed',  trim: false)
      string(name: 'DX_HOST', defaultValue: '', description: 'Hostname to connect to DX server',  trim: false)
      string(name: 'DX_PROTOCOL', defaultValue: '', description: 'Protocol to connect to DX server',  trim: false)
      string(name: 'DX_PORT', defaultValue: '', description: 'Port to connect to DX server (main profile)',  trim: false)
      string(name: 'DXCONNECT_PORT', defaultValue: '', description: 'Port to connect to cw_profile in DX server',  trim: false)
      string(name: 'DX_CORE_PROFILE_NAME', defaultValue: '', description: 'Profile name in DX server',  trim: false)
      string(name: 'DX_SOAP_PORT', defaultValue: '', description: 'SOAP Port to connect to DX Server',  trim: false)
    // Parameters specific to this pipeline
      string(name: 'XML_CONFIG_PATH', defaultValue: '', description: 'XML Config Path in DX Server. eg: /wps/config',  trim: false)
      string(name: 'PORTLET_WAR_ARTIFACT_NAME', defaultValue: '', description: 'Name of Portlet WAR file(war file). eg: Welcome.war',  trim: false)
      string(name: 'PORTLET_XML_ARTIFACT_NAME', defaultValue: '', description: 'Name of Portlet XML file. eg: Welcome.xml',  trim: false)
      string(name: 'CONTENT_HANDLER_PATH', defaultValue: '', description: 'Content Handler Path in DX Server. eg: /wps/mycontenthandler',  trim: false)
      string(name: 'THEME_EAR_APPLICATION_NAME', defaultValue: '', description: 'Application Name',  trim: false)
      string(name: 'THEME_EAR_ARTIFACT_NAME', defaultValue: '', description: 'File name of the EAR Application(ear file). eg: HelloWorld.ear',  trim: false)
      string(name: 'THEME_REGISTRATION_FILE', defaultValue: '', description: 'XML File to register the theme application. eg: HelloWorld.xml',  trim: false)
      string(name: 'THEME_NAME', defaultValue: '', description: 'Theme name',  trim: false)
      string(name: 'THEME_ARTIFACT_NAME', defaultValue: '', description: 'File name of WebDav theme package(zip file. eg: Simple_Theme.zip)',  trim: false)
      string(name: 'SCRIPT_APP_ARTIFACT_NAME', defaultValue: '', description: 'Script Application Name',  trim: false)
      string(name: 'MAIN_HTML_FILE', defaultValue: '', description: 'main HTML File',  trim: false)
      string(name: 'WCM_SITE_AREA', defaultValue: '', description: 'WCM Site Area or the library path. eg: Script Application Library/Script Applications/ ',  trim: false)
      string(name: 'CONTENT_NAME', defaultValue: '', description: 'WCM Content Name',  trim: false)
  }
  stages {
    // Validate minimum one artifact is selected or not
    stage('Validate prerequisites') {
      steps {
        script {
          // If any of the artifact not selected then stop the execution
          if (DEPLOY_PORTLET.toBoolean() == false && DEPLOY_THEME.toBoolean() == false && DEPLOY_SCRIPT_APPLICATION.toBoolean() == false) {
            sh "echo Please select one artifact from the list"
            error "Please select one artifact from the list"
          }
        }
      }
    }
     stage('Install DXClient') {
            steps {
                script {
                    dir("${WORKSPACE}") {
                        withCredentials([
                          [$class: 'UsernamePasswordMultiBinding', credentialsId: "${TOOL_CREDENTIALS_ID}", usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']
                        ]) {
                                sh(script: """	
                                if [ -d "dxclient" ]; then
                                  rm -rf dxclient/*
                                  rm -rf dxclient.zip
                                fi
                                curl -s -u${USERNAME}:${PASSWORD} ${TOOL_PACKAGE_URL} --output dxclient.zip
                                yes | unzip dxclient.zip
                                ls
                                """)	
                                
                         }
                    }

                    dir("${WORKSPACE}/dxclient") {
                      sh "docker load < dxclient.tar.gz"
                      sh "./bin/dxclient -V"
                    }
                }
            }
        }
    // Get the artifact files from the given artifact path.
    stage('Get the artifacts') {
      steps {
        dir("${WORKSPACE}/dxclient/") {
          withCredentials([
            [$class: 'UsernamePasswordMultiBinding', credentialsId: "${TOOL_CREDENTIALS_ID}", usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']
          ]) {
            script {
              // sh "mkdir store"
              // sh "chmod 777 store"

              // portlet artifatct
              if (DEPLOY_PORTLET.toBoolean() == true) {
                sh "curl -s -u${USERNAME}:${PASSWORD} ${ARTIFACT_PATH}/${PORTLET_WAR_ARTIFACT_NAME} --output store/${PORTLET_WAR_ARTIFACT_NAME}"
                sh "curl -s -u${USERNAME}:${PASSWORD} ${ARTIFACT_PATH}/${PORTLET_XML_ARTIFACT_NAME} --output store/${PORTLET_XML_ARTIFACT_NAME}"
              }

              // Theme artifatct
              if (DEPLOY_THEME.toBoolean() == true) {
                // Deploy EAR application artifact
                if (!THEME_EAR_APPLICATION_NAME.isEmpty() && !THEME_EAR_ARTIFACT_NAME.isEmpty()) {
                  sh "curl -s -u${USERNAME}:${PASSWORD} ${ARTIFACT_PATH}/${THEME_EAR_ARTIFACT_NAME} --output store/${THEME_EAR_ARTIFACT_NAME}"
                }

                // Theme registration xml artifatct
                if (!THEME_REGISTRATION_FILE.isEmpty()) {
                  sh "curl -s -u${USERNAME}:${PASSWORD} ${ARTIFACT_PATH}/${THEME_REGISTRATION_FILE} --output store/${THEME_REGISTRATION_FILE}"
                }

                // WEBDAV theme artifact
                if (!THEME_NAME.isEmpty() && !THEME_ARTIFACT_NAME.isEmpty()) {
                  sh "curl -s -u${USERNAME}:${PASSWORD} ${ARTIFACT_PATH}/${THEME_ARTIFACT_NAME} --output store/${THEME_ARTIFACT_NAME}"
                }
              }

              // Deploy script app artifact
              if (DEPLOY_SCRIPT_APPLICATION.toBoolean() == true) {
                sh "curl -s -u${USERNAME}:${PASSWORD} ${ARTIFACT_PATH}/${SCRIPT_APP_ARTIFACT_NAME} --output store/${SCRIPT_APP_ARTIFACT_NAME}"
                sh "mkdir store/scriptapp-artifacts"
                sh "unzip store/${SCRIPT_APP_ARTIFACT_NAME} -d store/scriptapp-artifacts"
              }
              sh "ls store/"
            }
          }
        }
      }
    }

    stage('Deploy portlet') {
      when {
        environment name: 'DEPLOY_PORTLET', value: 'true'
      }
      steps {
        dir("${WORKSPACE}/dxclient/") {
          withCredentials([
            [$class: 'UsernamePasswordMultiBinding', credentialsId: "${DX_CREDENTIALS_ID}", usernameVariable: 'DX_USERNAME', passwordVariable: 'DX_PASSWORD'],
            [$class: 'UsernamePasswordMultiBinding', credentialsId: "${DXCONNECT_CREDENTIALS_ID}", usernameVariable: 'DXCONNECT_USERNAME', passwordVariable: 'DXCONNECT_PASSWORD']
          ]) {
            script {
              Exception caughtException = null;
              catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                try {
                  command = "deploy-portlet -hostname ${DX_HOST} -dxProtocol ${DX_PROTOCOL} -dxPort ${DX_PORT} -dxUsername ${DX_USERNAME} -dxPassword ${DX_PASSWORD} -dxConnectPort ${DXCONNECT_PORT} -dxConnectUsername ${DXCONNECT_USERNAME} -dxConnectPassword ${DXCONNECT_PASSWORD} -xmlFile ${WORKSPACE}/dxclient/store/${PORTLET_XML_ARTIFACT_NAME} -warFile ${WORKSPACE}/dxclient/store/${PORTLET_WAR_ARTIFACT_NAME}"

                  if (!XML_CONFIG_PATH.isEmpty()) {
                    command = "${command}  -xmlConfigPath ${XML_CONFIG_PATH}"
                  }
                  sh "./bin/dxclient ${command}"

                } catch (Throwable e) {
                  caughtException = e;
                }
                if (fileExists('store/config.json')) {
                  sh "rm store/config.json"
                }
                if (caughtException) {
                  error caughtException.message
                }
              }
            }
          }
        }
      }
    }

    stage('Deploy theme') {
      when {
        environment name: 'DEPLOY_THEME', value: 'true'
      }
      steps {
        dir("${WORKSPACE}/dxclient/") {
          withCredentials([
            [$class: 'UsernamePasswordMultiBinding', credentialsId: "${DX_CREDENTIALS_ID}", usernameVariable: 'DX_USERNAME', passwordVariable: 'DX_PASSWORD'],
            [$class: 'UsernamePasswordMultiBinding', credentialsId: "${DXCONNECT_CREDENTIALS_ID}", usernameVariable: 'DXCONNECT_USERNAME', passwordVariable: 'DXCONNECT_PASSWORD']
          ]) {

            script {
              Exception caughtException = null;
              catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                try {
                  command = "deploy-theme -hostname ${DX_HOST} -dxProtocol ${DX_PROTOCOL} -dxPort ${DX_PORT} -dxUsername ${DX_USERNAME} -dxPassword ${DX_PASSWORD} "

                  // Add EAR theme deployment arguments
                  if (!THEME_EAR_APPLICATION_NAME.isEmpty() && !THEME_EAR_ARTIFACT_NAME.isEmpty()) {
                    command = "${command} -dxConnectPort ${DXCONNECT_PORT} -dxConnectUsername ${DXCONNECT_USERNAME} -dxConnectPassword ${DXCONNECT_PASSWORD} -applicationName ${THEME_EAR_APPLICATION_NAME} -applicationFile ${WORKSPACE}/dxclient/store/${THEME_EAR_ARTIFACT_NAME} -dxSoapPort ${DX_SOAP_PORT}"
                     if(!DX_CORE_PROFILE_NAME.isEmpty()){
                        command = "${command} -dxProfileName ${DX_PROFILE_NAME}"
                      }
                      if(!DX_CORE_PROFILE_PATH.isEmpty()){
                        command = "${command} -dxProfilePath ${DX_CORE_PROFILE_PATH}"
                      }
                  }
                  
                  // Add WEBDAV theme deployment arguments
                  if (!THEME_NAME.isEmpty() && !THEME_ARTIFACT_NAME.isEmpty()) {
                    command = "${command} -themeName ${THEME_NAME} -themePath ${WORKSPACE}/dxclient/store/${THEME_ARTIFACT_NAME}"
                    if (!CONTENT_HANDLER_PATH.isEmpty()) {
                      command = "${command}  -contenthandlerPath ${CONTENT_HANDLER_PATH}"
                    }
                  }
                  // Add theme registration arguments
                  if (!THEME_REGISTRATION_FILE.isEmpty()) {
                    command = "${command} -xmlFile ${WORKSPACE}/dxclient/store/${THEME_REGISTRATION_FILE}"
                    if (!XML_CONFIG_PATH.isEmpty()) {
                      command = "${command}  -xmlConfigPath ${XML_CONFIG_PATH}"
                    }
                  }
                  sh "./bin/dxclient ${command}"

                } catch (Throwable e) {
                  caughtException = e;
                }
                if (fileExists('store/config.json')) {
                  sh "rm store/config.json"
                }
                if (caughtException) {
                  error caughtException.message
                }
              }
            }
          }
        }
      }
    }

    stage('Deploy script application') {
      when {
        environment name: 'DEPLOY_SCRIPT_APPLICATION', value: 'true'
      }
      steps {
        dir("${WORKSPACE}/dxclient/") {
          withCredentials([
            [$class: 'UsernamePasswordMultiBinding', credentialsId: "${DX_CREDENTIALS_ID}", usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']
          ]) {
            script {
              Exception caughtException = null;
              catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                try {
                  command = "deploy-scriptapplication push -hostname ${DX_HOST} -dxProtocol ${DX_PROTOCOL} -dxPort ${DX_PORT} -dxUsername ${USERNAME} -dxPassword ${PASSWORD} -wcmSiteArea '${WCM_SITE_AREA}' -contentRoot ${WORKSPACE}/dxclient/store/scriptapp-artifacts -wcmContentName '${CONTENT_NAME}' -mainHtmlFile ${MAIN_HTML_FILE}"

                  if (!CONTENT_HANDLER_PATH.isEmpty()) {
                    command = "${command}  -contenthandlerPath ${CONTENT_HANDLER_PATH}"
                  }
                  sh "./bin/dxclient ${command}"

                } catch (Throwable e) {
                  caughtException = e;
                }
                if (caughtException) {
                  error caughtException.message
                }
              }
            }
          }
        }
      }
    }
  }

  post {
    cleanup {
      /* Remove dxclient dockr image */
      dir("${WORKSPACE}") {
        script {
          if (fileExists("${WORKSPACE}/dxclient/")) {
            sh "IMAGE_TAG=\$(sed -n -e '/IMAGE_TAG=/ s/.*\\= *//p' dxclient/bin/dxclient)"
            sh "docker image rm hcl/dx/client:${IMAGE_TAG}"
          }
        }
      }

      /* Cleanup workspace */
      dir("${WORKSPACE}") {
        deleteDir()
      }

      /* Cleanup workspace@tmp */
      dir("${WORKSPACE}@tmp") {
        deleteDir()
      }
    }
  }
}
