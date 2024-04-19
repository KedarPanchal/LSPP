$CommandParent = Split-Path -Parent $PSCommandPath
Push-Location $CommandParent

mvn clean package
mvn -Pnative -Dagent exec:exec@java-agent
mvn -Pnative -Dagent package
$MovePath = Read-Host "Please enter the installation folder (enter nothing to install here): "

if (!$Movepath) {$MovePath = $CommandParent}

Move-Item -Path "target\lspp.exe" -Destination $MovePath
[Environment]::SetEnvironmentVariable("Path", [Environment]::GetEnvironmentVariable("Path", [EnvironmentVariableTarget]::User) + ";" + $MovePath + "\lspp.exe", [System.EnvironmentVariableTarget]::User)

Pop-Location