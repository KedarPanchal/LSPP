# lspp
`lspp` (ls++) is an improved form of the `ls` command that lists files in a tree-style structure.  

![LSPP example usage](images/lsppexample.JPG)

## Table of Contents

---
* [Installation](#installation)
  * [Prerequisites](#prerequisites)
  * [On Windows](#windows)
    * [Downloading the executable](#downloading-the-executables)
    * [Building from source](#building-from-source)
  * [On Linux or macOS](#linux-or-macOS)
    * [Downloading the executable jar](#downloading-the-executable-jar)
    * [Building from source](#building-from-source-1)
* [Usage](#usage)
* [Troubleshooting](#troubleshooting)
  * [On Windows](#troubleshooting-on-windows)
    * [Executing `install.ps1`](#powershell-script-execution)
    * [Charsets displaying as gibberish](#gibberish-charsets)
  * [On Linux or macOS](#troubleshooting-on-linux-or-macos)
    * [Executing `install.sh`](#shell-script-execution)
* [License](#license)

## Installation

---
[Back to top](#table-of-contents)
> ### Prerequisites
> The following tools need to be installed on your computer to build `lspp` from source.
> * [Maven 3.9.6+](https://maven.apache.org/download.cgi)
> * [GraalVM JDK 21+](https://www.graalvm.org/downloads/)

### Windows

#### Downloading the executables
Install and extract the `Windows executable + executuable .jar (.zip)` file from the
[latest release](https://github.com/KedarPanchal/). Add the `lspp.exe` executable to your path by running the following
in a powershell instance:
```powershell
[Environment]::SetEnvironmentVariable("Path", [Environment]::GetEnvironmentVariable("Path", [EnvironmentVariableTarget]::User) + ";" + "C:\path\to\lspp\parent", [System.EnvironmentVariableTarget]::User)
```
Where `C:\path\to\lspp\parent` is the path to the parent directory of the `lspp.exe` executable.  

To run the `lspp.jar` executable jar, type the following in a terminal instance (you must have JDK 21+ installed on your
computer to run the `lspp.jar` file):
```shell
java -jar "C:\path\to\lspp.jar <arguments>"
```
Where `<arguments>` are the arguments passed into the program as described in the [Usage](#usage) section of this
README.

#### Building from source
Install and extract the `lspp_source (.zip)` from the [latest release](https://github.com/KedarPanchal/releases/latest).
Navigate to the extracted directory in a powershell instance and run the `install.ps1` script. If running the
installation script doesn't work, refer to the [Windows troubleshooting](#troubleshooting-on-windows) section of this README.

### Linux or macOS

#### Downloading the executable jar
Install and extract the `Windows executable + executable .jar (.tar.gz)` file from the
[latest release](https://github.com/KedarPanchal/). To run the `lspp.jar` executable jar, type the following in a
terminal instance (you must have JDK 21+ installed on your computer to run the `lspp.jar` file):
```shell
java -jar "/path/to/lspp.jar <arguments>"
```
Where `<arguments>` are the arguments passed into the program as described in the [Usage](#usage) section of this
README.

#### Building from source
Install and extract the `lspp_source (.tar.gz)` from the [latest release](https://github.com/KedarPanchal/).
Navigate to the extracted directory in a terminal instance and run the `install.sh` shell script. Afterward add `lspp`
to your path by adding the following line to your `~/.bashrc` or `~/.bash_profile` file:
```shell
export PATH="/<path>/<to>/<lspp>/<parent>:$PATH";
```
Where `/<path>/<to>/<lspp>/<parent>` is the path to the parent directory of the `lspp` executable. If running the
installation script doesn't work, refer to the [Linux/macOS troubleshooting](#troubleshooting-on-linux-or-macos) section of this README.

## Usage

---
[Back to top](#table-of-contents)

To run `lspp`, type `lspp` in the terminal after installation. `lspp` takes in a variety of arguments to extend its
functionality.

```
Usage: lspp [-hv] [-a=<regex>] [-c=<charset>] [-d=<depth>] [-s=<fileName>] DIRECTORY
Lists the files in a folder in a tree-style output

Written by Kedar Panchal

DIRECTORY                    The directory to list files in. If none is specified, then the
                             the current working directory's contents are listed.
-s, --search=<filename>      The name of the file to search for. Only the files and their
                             parent directories will be displayed.
-r, --regex=<filename>       Searches for all files that match the specified regex and lists a
                             file tree containing only those files.
-c, --charset=<charset>      The charset to use when displaying the file tree (default: ASCII).
                             Valid values (case-insensitive): BOX, ROUND, TUBE, ASCII.
-v, --version                Outputs the version of the program.
-h, --help                   Displays this message.
-d, --depth=<depth>          The depth of the files to list in a tree.
```

## Troubleshooting

---
[Back to top](#table-of-contents)
> ### Note on building lspp
> For `lspp` to be built as an executable, Maven requires that your environment's `JAVA_HOME` variable be set
> to your GraalVM installation path. If `install.ps1` or `install.sh` exit early due to the `JAVA_HOME` variable not
> being configured correctly, ensure that the `JAVA_HOME` variable is set to your GraalVM installation location.

### Troubleshooting on Windows

#### Powershell script execution
If you can't run the `install.ps1` script in a PowerShell instance, check your user's execution policy by running:
```powershell
Get-ExecutionPolicy -Scope UserProfile
``` 
If the output of the command is not `Unrestricted` or `Bypass`, run:
```powershell
Set-ExecutionPolicy -ExecutionPolicy Unrestricted -Scope UserProfile
```
This enables the current user to run `.ps1` scripts in PowerShell.

#### Gibberish Charsets
By default, Windows Command Prompt (and other Windows terminals) do not use `UTF-8` encoding. This means that when
passing any `--charset` argument into `lspp` except for `ASCII`, the file tree is often outputted as
near-incomprehensible gibberish. To fix this, you need to set the system locale to `UTF-8`. Currently, this feature is
in beta and may impact other aspects of your computer.

To set the system locale to `UTF-8`, follow the following steps:
* Press `Win` + `R` on your keyboard to open the Run command.
   
  ![Run command menu](images/RunMenu.JPG)  
* Type `intl.cpl` and click the `OK` button to open the regional settings in Control Panel.  
* Navigate to the `Administrative` tab and click the `Change system locale...` button.  

  ![Regional settings menu](images/RegionalSettings.JPG)  
* Check the `Beta: Use Unicode UTF-8 for worldwide language support` checkbox.  

  ![UTF-8 checkbox screen](images/UTF8Menu.JPG)  
* Press the `OK` button and reboot.
### Troubleshooting on Linux or macOS

#### Shell script execution
If you can't run the `install.sh` script on your Linux or macOS machine, ensure it has executable permissions by
running:
```shell
chmod +x install.sh
```
## License

---
[Back to top](#table-of-contents)  
The BSD 3-Clause License (BSD-3) 2024 - [Kedar Panchal](https://github.com/KedarPanchal). Please look at the
[LICENSE](LICENSE) for further information.
