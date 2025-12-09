# DXClient Containerized Version

Welcome to the GitHub repository for DXClient Helper Scripts. This repository contains latest scripts/bin that assist in using the containerized version of DXClient, which is openly distributed in [HCL Open Harbor](https://hclcr.io/harbor/projects/95/repositories/dxclient/artifacts-tab) and some samples for reference.

> **IMPORTANT NOTE:**
>
> This repository does not contain any kind of distribution of [DXClient]((https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/)).

DXClient is a command line tool featuring a single, unified interface to all HCL DX automation and CI/CD-related tasks. It helps developers manage several DX tasks such as uploading one or more portlets, Script Applications, and themes. Administrators can manage WCM libraries, PZN rules, shared libraries, etc.

DXClient can take artifacts developed locally and deploy them to HCL DX servers independently of whether these are deployed on-premises platforms in standalone, cluster, or farm-topologies, or in a container environment.

DXClient is meant to be the one-stop, platform-independent solution that lets you integrate HCL DX with any automation infrastructure of your choice.

## Deployment Options

1. This documentation is for the DXClient container package which is preferred for **CI/CD automation** scenarios. DXClient is packaged as a container that can be run using OCI-based runtimes such as Docker or Podman.

2. Another one, recommended for **local developers and admins**, is the Native JavaScript Code Installation Option of DXClient. Customers who want to rely on this deployment option need to install their own Node.js and NPM runtime environment in the correct version and must install necessary dependencies as needed. More information could be found in the [@hcl-software/dxclient](https://www.npmjs.com/package/@hcl-software/dxclient).

**Warning** - Recent changes to Podman introduced by RedHat have caused a compatibility issue with the container implementation of DXClient. HCL is currently investigating how this might be resolved. Until further notice, Docker is recommended for using the DXClient container implementation.

> **Important Note related to DXClient version & distribution:**
> DXClient is now available free to download/install through NpmJS and Harbor repositories. Following the decision to openly distribute DXClient, we have changed the current versioning format of DXClient from 1.xx.x to CFNumber.x.x. For example, the old release version of DXClient was "1.29.0" but the upcoming releases is reformatted to "221.0.0". The number 221 is synonymous with the CF Number version of DX deployments.

## Requirements

To use these tools you will need:

- Docker or other similar container management tools.

> Note: This document will be primarily based on using Docker.

## **DXClient Installation**

Complete the following steps to install the DXClient tool to your local development workstation or automation server.

1. In this table, find the dxclient version you're interested in, and then copy the corresponding Image Tag.

Version	Image Tag	Date
-------	------------------------	------------
CF231	v95_CF231_20251024-1354	2025-10-27
CF230	v95_CF230_20250922-1240	2025-09-23
CF229	v95_CF229_20250813-1931	2025-08-15
CF228	v95_CF228_20250606-1744	2025-06-09
CF227	v95_CF227_20250425-1919	2025-04-28

<details>
 <summary>Legacy Container Version and Tag Information</summary>
 <br>
 <table>
  <tr>
    <th>Version</th>
    <th>Image Tag</th>
    <th>Date</th>
  </tr>
  <tr>
    <td>CF223</td>
    <td>v95_CF223_20240925-1911</td>
    <td>2024-09-25</td>
  </tr>
  <tr>
    <td>CF222</td>
    <td>v95_CF222_20240814-1252</td>
    <td>2024-08-14</td>
  </tr>
  <tr>
    <td>CF221</td>
    <td>v95_CF221_20240708-2007</td>
    <td>2024-07-08</td>
  </tr>
  <tr>
    <td>CF220</td>
    <td>v95_CF220_20240522-1920</td>
    <td>2024-05-22</td>
  </tr>
  <tr>
    <td>CF219</td>
    <td>v95_CF219_20240409-1526</td>
    <td>2024-04-09</td>
  </tr>
  <tr>
    <td>CF218</td>
    <td>v95_CF218_20240226-1619</td>
    <td>2024-02-26</td>
  </tr>
  <tr>
    <td>CF217</td>
    <td>v95_CF217_20240117-2358</td>
    <td>2024-01-17</td>
  </tr>
  <tr>
    <td>CF216</td>
    <td>v95_CF216_20231114-2138</td>
    <td>2023-11-14</td>
  </tr>
  <tr>
    <td>CF215</td>
    <td>v95_CF215_20231004-1320</td>
    <td>2023-10-04</td>
  </tr>
</table>
</details>


2. On a terminal execute the following command and just change the `IMAGE_TAG` to match the `Image Tag` of the dxclient you have want.

``` shell
 docker pull hclcr.io/dx-public/dxclient:IMAGE_TAG
```

3. Update the `IMAGE_TAG` and `IMAGE_NAME` variable of the downloaded file to match the image you have pulled from the HCL Open Harbor.

 **Examples:**

 In `dxclient` file (Linux and Apple MacOS platforms)

 ``` shell
 IMAGE_NAME="hclcr.io/dx-public/dxclient"
 IMAGE_TAG="v95_CF225_20250203-2238"
 ```

 In `dxclient.bat` file (Microsoft Windows platforms)

 ``` cmd
 SET IMAGE_NAME=hclcr.io/dx-public/dxclient
 SET IMAGE_TAG=v95_CF225_20250203-2238
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
  - DXClient will be openly distributed in NPMJS & Harbor repository.

  22. If connecting to an HCL DX 9.5 CF224 deployment, following changes are to be expected.

  - LiveSync Pull and Push commands for WCM Design Library are now available for HTML and Folder Components.

  23. If connecting to an HCL DX 9.5 CF225 deployment, following changes are to be expected.

  - LiveSync now supports Presentation Templates.

  24. If connecting to an HCL DX 9.5 CF226 deployment, following changes are to be expected.

  - DXClient no longer ignores certificates that cannot be properly validated when using Transport Layer Security (TLS) connections
  - New documented limitations for LiveSync
  - Updated scripts to pass certificates to container.

  25. If connecting to an HCL DX 9.5 CF227 deployment, following changes are to be expected.

  - LiveSync now supports Style-Sheet Components.

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

| Command                                                           | Description                                            |
|-------------------------------------------------------------------|--------------------------------------------------------|
| [dxclient](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/)                                                          | Help document for the commands usage                   |
| [dxclient version-compat](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/versionCompat/)                                           | Command to show version compatibility details          |
| [dxclient accept-license](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/#dxclient-information-commands)                                           | Command to explicitly accept the license without prompt|
| [dxclient deploy-portlet](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/portlets/#deploy-portlets) [options]                                 | Command to execute the deploy portlet action           |
| [dxclient undeploy-portlet](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/portlets/#undeploy-portlets) [options]                               | Command to execute the undeploy portlet action         |
| [dxclient xmlaccess](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/xmlaccess/) [options]                                      | Command to execute the xmlaccess action                |
| [dxclient deploy-scriptapplication](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/scriptapplications/#deploy-script-applications) [options]                       | Command to execute the deploy script application action|
| [dxclient undeploy-scriptapplication](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/scriptapplications/#undeploy-script-applications) [options]                     | Command to execute the undeploy script application action|
| [dxclient restore-scriptapplication](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/scriptapplications/#restore-script-application) [options]                      | Command to execute the restore script application action|
| [dxclient deploy-theme](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/themes/#deploy-theme) [options]                                   | Command to execute the deploy theme action             |
| [dxclient undeploy-theme](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/themes/#undeploy-theme) [options]                                 | Command to execute the undeploy theme action           |
| [dxclient deploy-application](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/deployapplication/) [options]                             | Command to execute the deploy application action       |
| [dxclient undeploy-application](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/deployapplication/#undeploy-application) [options]                           | Command to execute the undeploy application action     |
| [dxclient manage-syndicator](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/syndicatorsandsubscribers/) [options]                              | Command to execute the syndicator action               |
| [dxclient manage-subscriber](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/syndicatorsandsubscribers/#managing-subscribers) [options]                              | Command to execute the subscriber action               |
| [dxclient restart-dx-core](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/dxcoreserver/) [options]                                | Command to execute the DX Core restart action          |
| [dxclient mls-export](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/wcm_mls_export_import/) [options]                                     | Command to export content of a WCM Library for translation|
| [dxclient mls-import](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/wcm_mls_export_import/) [options]                                     | Command to import the translated contents into the DX   |
| [dxclient manage-syndicator get-syndication-report](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/syndicatorsandsubscribers/#manage-syndicator-get-syndication-report) [options]       | Command to execute the syndication failed report|
| [dxclient delete-dam-schema](https://opensource.hcltechsw.com/digital-experience/latest/manage_content/digital_assets/usage/managing_dam/damschemas/#deleting-dam-schemas) [options]                              | Command to execute the delete dam schema action         |
| [dxclient list-dam-schemas](https://opensource.hcltechsw.com/digital-experience/latest/manage_content/digital_assets/usage/managing_dam/damschemas/#listing-dam-schemas) [options]                               | Command to execute the list of all dam schema action    |
| [dxclient pzn-export](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/personalization/) [options]                                     | Command to export the pzn rules from the target server  |
| [dxclient pzn-import](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/personalization/#import-pzn-rules) [options]                                     | Command to import the pzn rules into the target server  |
| [dxclient resource-env-provider](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/resourceenvironments/) [options]                          | Command to create, update or delete a custom property from an existing Resource Environment Provider|
| [dxclient manage-virtual-portal](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/virtualportals/) [options]                          | Command to manage virtual portal tasks in the DX server |
| [dxclient manage-dam-staging register-dam-subscriber](https://opensource.hcltechsw.com/digital-experience/latest/manage_content/digital_assets/configuration/staging_dam/dam_subscription_staging/#registering-or-deregistering-for-dam-staging) [options]     | Command to execute the register subscriber action|
| [dxclient manage-dam-staging trigger-staging](https://opensource.hcltechsw.com/digital-experience/latest/manage_content/digital_assets/configuration/staging_dam/dam_subscription_staging/#managing-dam-staging) [options]             | Command to execute the trigger staging action|
| [dxclient manage-dam-staging deregister-dam-subscriber](https://opensource.hcltechsw.com/digital-experience/latest/manage_content/digital_assets/configuration/staging_dam/dam_subscription_staging/#registering-or-deregistering-for-dam-staging) [options]   | Command to execute the deregister subscriber action|
| [dxclient manage-dam-staging get-all-subscribers](https://opensource.hcltechsw.com/digital-experience/latest/manage_content/digital_assets/configuration/staging_dam/dam_subscription_staging/#getting-all-subscribers-details-for-dam-staging) [options]         | Command to execute the get subscribers details action|
| [dxclient manage-dam-staging update-secrets](https://opensource.hcltechsw.com/digital-experience/latest/manage_content/digital_assets/configuration/staging_dam/dam_subscription_staging/#updating-secrets-for-dam-staging) [options]              | Command to execute the update secrets action|
| [dxclient manage-dam-staging find-staging-mismatch](https://opensource.hcltechsw.com/digital-experience/latest/manage_content/digital_assets/configuration/staging_dam/dam_staging_mismatch/) [options]       | Command to execute the find mismatch action|
| [dxclient manage-dam-staging get-staging-mismatch-report](https://opensource.hcltechsw.com/digital-experience/latest/manage_content/digital_assets/configuration/staging_dam/dam_staging_mismatch/#download-mismatch-report) [options] | Command to generate staging mismatch report|
| [dxclient manage-dam-staging start-staging-resync](https://opensource.hcltechsw.com/digital-experience/latest/manage_content/digital_assets/configuration/staging_dam/dam_staging_mismatch/#staging-resync) [options]        | Command to execute the staging resync action|
| [dxclient manage-dam-staging delete-staging-mismatch](https://opensource.hcltechsw.com/digital-experience/latest/manage_content/digital_assets/configuration/staging_dam/dam_staging_mismatch/#delete-staging-mismatch) [options]     | Command to execute the delete staging mismatch action|
| [dxclient create-credential-vault](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/credentialvaultslot/) [options]                        | Command to create credential vault in the DX server    |
| [dxclient wcm-library-export](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/wcmlibraries/) [options]                             | Command to export the wcm libraries from the target server|
| [dxclient wcm-library-import](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/wcmlibraries/#import-wcm-libraries) [options]                             | Command to import the wcm libraries from the target server|
| [dxclient dx-core-configuration-reports](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/dxcoreserver/#dx-core-server-configuration-report) [options]                  | Command to generate any dx core configuration reports|
| [dxclient manage-dam-assets export-assets](https://opensource.hcltechsw.com/digital-experience/latest/manage_content/digital_assets/usage/managing_dam/dam_exim/#export-dam-assets) [options]                | Command to export the dam assets                      |
| [dxclient manage-dam-assets validate-assets](https://opensource.hcltechsw.com/digital-experience/latest/manage_content/digital_assets/usage/managing_dam/dam_exim/#validate-exported-dam-assets) [options]              | Command to validate the dam assets                    |
| [dxclient manage-dam-assets import-assets](https://opensource.hcltechsw.com/digital-experience/latest/manage_content/digital_assets/usage/managing_dam/dam_exim/#import-dam-assets) [options]                | Command to import the dam assets                      |
| [dxclient restart-core-pods](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/dxcoreserver/#restart-dx-core-pods) [options]                              | Command to execute the Restart Core Pods action (for Kubernetes deployments)|
| [dxclient livesync push-theme](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/livesync/) [options]                            | Command for watching a theme folder path live and syncs it in DX Server|
| [dxclient livesync pull-theme](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/livesync/#livesync-pull-theme) [options]                            | Command for downloading theme files from DX Server to a target local theme folder|
| [dxclient livesync pull-wcm-design-library](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/livesync/#livesync-pull-wcm-design-library) [options]               | Command for downloading WCM Design Library files from DX Server to a target local folder|
| [dxclient livesync push-wcm-design-library](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/dxclient_artifact_types/livesync/#livesync-push-wcm-design-library) [options]               | Command watching a WCM Design Library folder path live and syncs it in DX Server|

## Command options

| Attribute                  | Description                                                                 |
|----------------------------|-----------------------------------------------------------------------------|
| -hostname [value]          | Use this attribute to specify the hostname of the target server              |
| -dxConnectProtocol [value] | Use this attribute to specify the protocol with which to connect to the CW server |
| -dxConnectUsername [value] | Use this attribute to specify the username that is required for authenticating to the cw_profile |
| -dxConnectPassword [value] | Use this attribute to specify the password that is required for authenticating to the cw_profile |
| -dxConnectPort [value]     | Use this attribute to specify the port number of the cw_profile (for Kubernetes Environment dxConnectPort is 443) |

## Logger enablement

### Enable Logger

Logger can be enabled by setting parameter "enableLogger": true in the `config.json` file. If logger enabled then logger file will be generate in logs folder of the dxclient.

If logger enabled then logger file will be available in the below location:
`store/logs/`

### Disable Logger

Logger can be disabled by setting parameter "enableLogger":false in the `config.json` file

### List of Available Images

## [DXClient Documentation](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/)

Full documentation of DXClient can be found [here](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/).
