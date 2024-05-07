echo "Installation started";

# Get location of install.sh script and move there
Command_Path="$(dirname "$(readlink -f "${BASH_SOURCE}")")";
pushd "$Command_Path" || (echo "Installation failed" && exit);

# Read installation path of lspp from user
read -p "Please enter the installation folder (enter nothing to install at $Command_Path)" Move_Path;
if [ -z "$Move_Path" ]; then
  Move_Path="$Command_Path";
fi

# Build lspp executable
mvn clean package;
mvn -Pnative package;

# Move the lspp executable to the path specified by the user
mv target/lspp "$Move_Path";

# Return to the original directory
popd || (echo "Installation completed, failed to return to original directory" && exit);

echo "Installation completed, add $Move_Path to your PATH variable to add lspp to path";