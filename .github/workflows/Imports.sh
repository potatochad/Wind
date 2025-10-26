alias log='echo 🔹'

make_folder() {
  mkdir -p "$1"
  log "Folder '$(pwd)/$1' added/exists"
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
