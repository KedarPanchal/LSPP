"Installation started"

# Get location of install.ps1 script and move there
$CommandParent = Split-Path -Parent $PSCommandPath
Push-Location $CommandParent

# Read installation path of lspp.exe from user
$MovePath = Read-Host "Please enter the installation folder (enter nothing to install at $CommandParent)"
if (!$Movepath) {$MovePath = $CommandParent}

# Build lspp.exe
mvn clean package
mvn -Pnative package

# Move lspp.exe to installation path specified by user
Move-Item -Path "target\lspp.exe" -Destination $MovePath

# Add lspp.exe to path
[Environment]::SetEnvironmentVariable("Path", [Environment]::GetEnvironmentVariable("Path", [EnvironmentVariableTarget]::User) + ";" + $MovePath, [System.EnvironmentVariableTarget]::User)

# Move back to original directory
Pop-Location

"Installation completed"