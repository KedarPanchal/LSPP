$CommandParent = Split-Path -Parent $PSCommandPath
Push-Location $CommandParent

$MovePath = Read-Host "Please enter the installation folder (enter nothing to install here)"
if (!$Movepath) {$MovePath = $CommandParent}

mvn clean package
mvn -Pnative package

Move-Item -Path "target\lspp.exe" -Destination $MovePath

[Environment]::SetEnvironmentVariable("Path", [Environment]::GetEnvironmentVariable("Path", [EnvironmentVariableTarget]::User) + ";" + $MovePath, [System.EnvironmentVariableTarget]::User)

Pop-Location