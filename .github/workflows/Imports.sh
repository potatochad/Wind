log() { echo "🔹 $*"; }

make_folder() {
  mkdir -p "$1"
  log "Folder '$(pwd)/$1' added/exists"
}

give_gradle_permission() {
  chmod +x gradlew
  ls -lh gradlew
}

          
try() {
  local onfail=false
  local fail_cmd=()

  for arg in "$@"; do
    if $onfail; then
      fail_cmd+=("$arg")
    elif [[ $arg == "onFail" ]]; then
      onfail=true
    else
      cmd+=("$arg")
    fi
  done

  "${cmd[@]}" || "${fail_cmd[@]}"
}
