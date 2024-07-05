# DXClient Containerized Version

Welcome to the GitHub repository for DXClient Helper Scripts. This repository contains latest scripts/bin that assist in using the containerized version of DXClient, which is openly distributed in [HCL Open Harbor](https://hclcr.io/harbor/projects/95/repositories/dxclient/artifacts-tab) and some samples for reference.

> **IMPORTANT NOTE:**
>
> This repository does not contain any kind of distribution of [DXClient]((https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/)).

DXClient is a command line tool featuring a single, unified interface to all HCL DX automation and CI/CD-related tasks. It helps developers manage several DX tasks such as uploading one or more portlets, Script Applications, and themes. Administrators can manage WCM libraries, PZN rules, shared libraries, etc.

DXClient can take artifacts developed locally and deploy them to HCL DX servers independently of whether these are deployed on-premises platforms in standalone, cluster, or farm-topologies, or in a container environment.

DXClient is meant to be the one-stop, platform-independent solution that lets you integrate HCL DX with any automation infrastructure of your choice.

> **Important Note related to DXClient version & distribution:**
> DXClient is now available free to download/install through NpmJS and harbor repositories. Following the decision to openly distribute DXClient, we have changed the current versioning format of DXClient from 1.xx.x to <CFNumber>.x.x. For example, the last release version of DXClient was "1.29.0" but the upcoming release is reformatted to "221.0.0". The number 221 is synonymous with the CF Number version of DX deployments.

## Requirements

To use these tools you will need:

- Docker or other similar container management tools.

> Note: This document will be primarily based on using Docker.

## **DXClient Installation**

Complete the following steps to install the DXClient tool to your local development workstation or automation server.

1. Go to [HCL Open Harbor](https://hclcr.io/harbor/projects/95/repositories/dxclient/artifacts-tab) and then select the dxclient version you want.
2. On a terminal execute the following command and just change the `IMAGE_TAG` to match the `IMAGE_TAG` of the dxclient you have selected.

``` shell
 docker pull hclcr.io/dx-public/dxclient:IMAGE_TAG
```

<!-- TODO: Verify if we still need to use releases

3. In the [releases](https://github.com/HCL-TECH-SOFTWARE/DXclient/releases) of this repository, download the script (`.bat` file for windows) of the version of dxclient you have selected from harbor. You can also navigate to [bin](./bin/) of this repository to select and download the specific version you want.

-->

3. Update the `IMAGE_TAG` and `IMAGE_NAME` variable of the downloaded file to match the image you have pulled from the HCL open harbor.

 **Examples:**

 In `dxclient` file (Linux and Apple MacOS platforms)

 ``` shell
 IMAGE_NAME="hclcr.io/dx-public/dxclient"
 IMAGE_TAG="v95_CF216_20231114"
 ```

 In `dxclient.bat` file (Microsoft Windows platforms)

 ``` cmd
 SET IMAGE_NAME=hclcr.io/dx-public/dxclient
 SET IMAGE_TAG=v95_CF216_20231114
 ```

4. Run the files below to run the application.

 For Linux and Apple MacOS platforms

 ``` shell
 dxclient
 ```

 For Microsoft Windows platforms:

 ``` cmd
 dxclient.bat
 ```

> Note: The following commands will be using `dxclient`, if you are on Microsoft Windows platform use `dxclient.bat` instead.

5. Run `dxclient -V` to verify that the dxclient command line is installed.

**Verify the DXClient installation**

Successful installation of the DXClient tool can be checked using the "`dxclient -V`" command which should show the version of the DX Client tool installed.

Once installed, commands can be executed using the DXClient tool to perform CI / CD actions on HCL DX 9.5 servers.

> **Important:**
> The DXClient version is mostly forward and backward compatible with the DX CF versions. However, in some cases, it might not work as expected if the CF versions are different. Make sure that the CF versions of both DXClient and DX Core are the same in your installation. You may use "`dxclient version-compat`" to check version compatibility between DX Core and DXClient.

### DXClient Release Timelines & Updates

<details>
  <summary>Show Changelog Details</summary>
  
  1. If connecting to an HCL DX 9.5 CF19 deployment, the DXClient tool provides commands supporting the following artifact types along with the documentation

  - deploy/undeploy portlets 
  - deploy script applications
  - xmlaccess
  - restore script application

  2. If connecting to an HCL DX 9.5 CF192 deployment, the DXClient tool provides commands supporting the following additional artifact types along with the documentation

  - undeploy script applications
  - deploy themes (EAR & WebDAV based)

  3. If connecting to an HCL DX 9.5 CF193 deployment, the DXClient tool provides commands supporting the following additional artifact types along with the documentation

  - restart application
  - deploy application
  - manage syndicator
  - manage subscriber

  4. If connecting to an HCL DX 9.5 CF195 deployment, the DXClient tool provides commands supporting the following additional artifact types along with the documentation

  - undeploy theme

  5. If connecting to an HCL DX 9.5 CF196 deployment, the DXClient tool provides commands supporting the following additional artifact types along with the documentation

  - shared library

  6. If connecting to an HCL DX 9.5 CF197 deployment, the DXClient tool provides commands supporting the following additional artifact types along with the documentation

  - Get Syndication failed report
  - Delete DAM schema

  7. If connecting to an HCL DX 9.5 CF198 deployment, the DXClient tool provides commands supporting the following additional artifact types along with the documentation

  - Resource Environment Provider Updates - Create,update and delete
  - Create/import/export/list of virtual portal
  - Import and Export PZN
  - List DAM schemas

  8. If connecting to an HCL DX 9.5 CF199 deployment, the DXClient tool provides commands supporting the following additional artifact types along with the documentation

  - Resource Environment Provider Updates - Export and Import
  - Create Credential Vault Slot
  - Create Syndication Relation
  - DAM Staging - Register, Deregister and Trigger

  9. If connecting to an HCL DX 9.5 CF200 deployment, the DXClient tool provides commands supporting the following additional artifact types along with the documentation

  - WCM Libraries - Export and Import
  - DX Core Configuration Reports - summary-report

  10. If connecting to an HCL DX 9.5 CF201 deployment, the DXClient tool provides commands supporting the following additional artifact types along with the documentation

  - An optional parameter requestId added to Deploy theme, Deploy application, Restart DX Core server, and Manage virtual portals.
  - Retrieve feature added to the Resource environment provider.
  - Accessing ConfigWizard in container environment
  Note that a few parameters are deprecated and replaced with new parameters in the DX Core configuration reports. For information, see DX Core server configuration report.

  11. If connecting to an HCL DX 9.5 CF202 deployment, the DXClient tool provides commands supporting the following additional artifact types along with the documentation

  - Deprecated parameter dxConnectHostname. It is recommended that you start using the replacement parameter -hostname starting from CF202 wherever necessary.
  - DAM Assets Export & import

  12. If connecting to an HCL DX 9.5 CF207 deployment, the DXClient tool provides commands supporting the following additional artifact types along with the documentation

  - Support to set different Container Runtimes.

  13. If connecting to an HCL DX 9.5 CF208 deployment, the DXClient tool provides commands supporting the following additional artifact types along with the documentation

  - Get all subscribers details for DAM staging

  14. If connecting to an HCL DX 9.5 CF209 deployment, the DXClient tool provides commands supporting the following additional artifact types along with the documentation

  - Restart Core Pods in a Kubernetes Deployment

  15. If connecting to an HCL DX 9.5 CF210 deployment, following changes are to be expected.

  - Removed paramaters deprecated during CF201 & CF202 deployment.
  - added ability to export/import wcm libraries in Virtual Portal.

  16. If connecting to an HCL DX 9.5 CF211 deployment, following changes are to be expected.

  - Deploy and Undeploy Applications

  17. If connecting to an HCL DX 9.5 CF213 deployment, following changes are to be expected.

  - Livesync of WebDAV based Themes

  18. If connecting to an HCL DX 9.5 CF214 deployment, following changes are to be expected.

  - Livesync Improvements
    - List themes during Pull theme in case themeName is not provided.
    - Livesync is now supported in scaled DX environment setups

  19. If connecting to an HCL DX 9.5 CF216 deployment, following changes are to be expected.

  - Show Version compatibility details between DXCore and DXClient

  20. If connecting to an HCL DX 9.5 CF219 deployment, following changes are to be expected.

  - Enabled multiple environment configuration in node version

  21. If connecting to an HCL DX 9.5 CF221 deployment, following changes are to be expected.

  - DXClient Version Type(Node/Container) information available using help/version-compat commands
  - A one time license agreement click-through is enabled. To skip the prompt, use "accept-license" command.
  - DXClient will be openly distributed in NpmJS & harbor repository.
</details>

## DXClient configuration

Common command arguments that are listed in `config.json` can be pre-configured via this file. The common arguments could also be provided through command line. If so, it will override the values in `config.json` and execute.

A sample configuration file that could be used on-premises platforms in standalone, cluster platforms is also available under [`samples/sample-configurations/default-config.json`](./samples/sample-configurations/default-config.json) for reference.

## **DXClient uninstall**

To uninstall the DXClient tool, follow the command below.

``` shell
docker rmi hclcr.io/dx-public/dxclient:IMAGE_TAG
```

## DXClient Help commands

The following commands show the Help documents for DXClient command usage.

Use the following commands to display the Help document for DXClient:

```
dxclient
```

```
dxclient -h, --help 
```

Use the following command to display the DXClient version number:

```
dxclient -V, --version
```

Use the following command to display the detailed help for a specific command:

```
dxclient help [command]
```

Use the following command to show version compatibility details between DX Core and DXClient:

```
dxclient version-compat [command] [options]

```

## All available commands

| Wrapper Script | Command                             | Description                                            |
|----------------|-------------------------------------|--------------------------------------------------------|
| dxclient       |                                     | Help document for the commands usage                   |
| dxclient       | version-compat                      | Command to show version compatibility details          |
| dxclient       | accept-license                      | Command to explicitly accept the license without pormpt|
| dxclient       | deploy-portlet [options]            | Command to execute the deploy portlet action           |
| dxclient       | undeploy-portlet [options]          | Command to execute the undeploy portlet action         |
| dxclient       | xmlaccess [options]                 | Command to execute the xmlaccess action                |
| dxclient       | deploy-scriptapplication [options]  | Command to execute the deploy script application action|
| dxclient       | undeploy-scriptapplication [options]| Command to execute the undeploy script application action|
| dxclient       | restore-scriptapplication [options] | Command to execute the restore script application action|
| dxclient       | deploy-theme [options]              | Command to execute the deploy theme action             |
| dxclient       | undeploy-theme [options]            | Command to execute the undeploy theme action           |
| dxclient       | deploy-application [options]        | Command to execute the deploy application action       |
| dxclient       | undeploy-application [options]      | Command to execute the undeploy application action     |
| dxclient       | manage-syndicator [options]         | Command to execute the syndicator action               |
| dxclient       | manage-subscriber [options]         | Command to execute the subscriber action               |
| dxclient       | restart-dx-core [options]           | Command to execute the DX Core restart action          |
| dxclient       | mls-export [options]                | Command to export content of a WCM Library for translation|
| dxclient       | mls-import [options]                | Command to import the translated contents into the DX   |
| dxclient       | manage-syndicator get-syndication-report [options] | Command to execute the syndication failed report|
| dxclient       | delete-dam-schema [options]         | Command to execute the delete dam schema action         |
| dxclient       | list-dam-schemas [options]          | Command to execute the list of all dam schema action    |
| dxclient       | pzn-export [options]                | Command to export the pzn rules from the target server  |
| dxclient       | pzn-import [options]                | Command to import the pzn rules into the target server  |
| dxclient       | resource-env-provider [options]     | Command to create, update or delete a custom property from an existing Resource Environment Provider|
| dxclient | manage-virtual-portal [options]     | Command to manage virtual portal tasks in the DX server |
| dxclient | manage-dam-staging register-dam-subscriber [options] | Command to execute the register subscriber action|
| dxclient | manage-dam-staging trigger-staging [options] | Command to execute the trigger staging action|
| dxclient | manage-dam-staging deregister-dam-subscriber [options] | Command to execute the deregister subscriber action|
| dxclient | manage-dam-staging get-all-subscribers [options] | Command to execute the get subscribers details action|
| dxclient | manage-dam-staging update-secrets [options] | Command to execute the update secrets action|
| dxclient | manage-dam-staging find-staging-mismatch [options] | Command to execute the find mismatch action|
| dxclient | manage-dam-staging get-staging-mismatch-report [options] | Command to generate staging mismatch report|
| dxclient | manage-dam-staging start-staging-resync [options] | Command to execute the staging resync action|
| dxclient | manage-dam-staging delete-staging-mismatch [options] | Command to execute the delete staging mismatch action|
| dxclient | create-credential-vault [options]   | Command to create credential vault in the DX server    |
| dxclient | wcm-library-export [options]        | Command to export the wcm libraries from the target server|
| dxclient | wcm-library-import [options]        | Command to import the wcm libraries from the target server|
| dxclient | dx-core-configuration-reports [options] | Command to generate any dx core configuration reports|
| dxclient | manage-dam-assets export-assets [options] | Command to export the dam assets                      |
| dxclient | manage-dam-assets validate-assets [options] | Command to validate the dam assets                    |
| dxclient | manage-dam-assets import-assets [options] | Command to import the dam assets                      |
| dxclient | restart-core-pods [options]         | Command to execute the Restart Core Pods action (for Kubernetes deployments)|
| dxclient | livesync push-theme [options]       | Command for watching a theme folder path live and syncs it in DX Server|
| dxclient | livesync pull-theme [options]       | Command for downloading theme files from DX Server to a target local theme folder|

## Command options

| Attribute                  | Description                                                                 |
|----------------------------|-----------------------------------------------------------------------------|
| -hostname [value]          | Use this attribute to specify the hostname of the target server              |
| -dxConnectProtocol [value] | Use this attribute to specify the protocol with which to connect to the CW server |
| -dxConnectUsername [value] | Use this attribute to specify the username that is required for authenticating to the cw_profile |
| -dxConnectPassword [value] | Use this attribute to specify the password that is required for authenticating to the cw_profile |
| -dxConnectPort [value]     | Use this attribute to specify the port number of the cw_profile (for Kubernetes Environment dxConnectPort is 443) |

## [DXClient Documentation](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/)

Full documentation of DXClient can be found [here](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/).
