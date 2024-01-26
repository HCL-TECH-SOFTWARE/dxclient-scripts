# DXClient Containerized Version

Welcome to the GitHub repository for DXClient Helper Scripts. This repository contains latest scripts/bin that assist in using the containerized version of DXClient, which is openly distributed in [HCL Open Harbor](https://hclcr.io/harbor/projects/95/repositories/dxclient/artifacts-tab) and some samples for reference.

> **IMPORTANT NOTE:**
>
> This repository does not contain any kind of distribution of [DXClient]((https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/)).

DXClient is a command line tool featuring a single, unified interface to all HCL DX automation and CI/CD-related tasks. It helps developers manage several DX tasks such as uploading one or more portlets, Script Applications, and themes. Administrators can manage WCM libraries, PZN rules, shared libraries, etc.

DXClient can take artifacts developed locally and deploy them to HCL DX servers independently of whether these are deployed on-premises platforms in standalone, cluster, or farm-topologies, or in a container environment.

DXClient is meant to be the one-stop, platform-independent solution that lets you integrate HCL DX with any automation infrastructure of your choice.

> **Note:** You are encouraged to use the DXClient Docker image package from CF196 onwards for easier installation.

## Requirements

To use these tools you will need:

- Docker or other similar container management tools.

> Note: This document will be primarily based on using Docker.

## **DXClient Installation**

Complete the following steps to install the DXClient tool to your local development workstation or automation server.

1. Go to [HCL Open Harbor](https://hclcr.io/harbor/projects/95/repositories/dxclient/artifacts-tab) and then select the dxclient version you want.
2. On a terminal execute the following command and just change the `IMAGE_TAG` to match the `IMAGE_TAG` of the dxclient you have selected.

``` shell
 docker pull hclcr.io/dx/dxclient:IMAGE_TAG
```

<!-- TODO: Verify if we still need to use releases

3. In the [releases](https://github.com/HCL-TECH-SOFTWARE/DXclient/releases) of this repository, download the script (`.bat` file for windows) of the version of dxclient you have selected from harbor. You can also navigate to [bin](./bin/) of this repository to select and download the specific version you want.

-->

3. Update the `IMAGE_TAG` and `IMAGE_NAME` variable of the downloaded file to match the image you have pulled from the HCL open harbor.

	**Examples:**

	In `dxclient` file (Linux and Apple MacOS platforms)

	``` shell
	IMAGE_NAME="hclcr.io/dx/dxclient"
	IMAGE_TAG="v95_CF216_20231114"
	```

	In `dxclient.bat` file (Microsoft Windows platforms)

	``` cmd
	SET IMAGE_NAME=hclcr.io/dx/dxclient
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

> **Important:** The DXClient version is mostly forward and backward compatible with the DX CF versions. However, in some cases, it might not work as expected if the CF versions are different. Make sure that the CF versions of both DXClient and DX Core are the same in your installation. You may use "`dxclient version-compat`" command to check version compatibility between DX Core and DXClient.

## DXClient configuration

Common command arguments that are listed in `config.json` can be pre-configured via this file. The common arguments could also be provided through command line. If so, it will override the values in `config.json` and execute.

A sample configuration file that could be used on-premises platforms in standalone, cluster platforms is also available under [`samples/sample-configurations/default-config.json`](./samples/sample-configurations/default-config.json) for reference.

## **DXClient uninstall**

To uninstall the DXClient tool, follow the command below.

``` shell
docker rmi hclcr.io/dx/dxclient:IMAGE_TAG
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

* Command options

	``` mk
	-hostname <value>
		Use this attribute to specify the hostname of the target server

	-dxConnectProtocol <value>
		Use this attribute to specify the protocol with which to connect to the CW server

	-dxConnectUsername <value>
		Use this attribute to specify the username that is required for authenticating to the cw_profile

	-dxConnectPassword <value>
		Use this attribute to specify the password that is required for authenticating to the cw_profile

	-dxConnectPort <value>
		Use this attribute to specify the port number of the cw_profile(for Kubernetes Environment dxConnectPort is 443)
	```

## [DXClient Documentation](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/)

Full documentation of DXClient can be found [here](https://opensource.hcltechsw.com/digital-experience/latest/extend_dx/development_tools/dxclient/).
