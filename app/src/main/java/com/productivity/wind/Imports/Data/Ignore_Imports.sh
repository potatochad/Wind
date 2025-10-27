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




find_and_source() {
  local filename="$1"
  echo "🔍 Searching for $filename ..."
  local found
  found=$(find . -type f -name "$filename" 2>/dev/null | head -n 1)
          
  if [ -n "$found" ]; then
    echo "✅ Found: $found"
    source "$found"
  else
    echo "❌ File '$filename' not found."
    echo "📂 Listing all files for debugging:"
    find . -type f | sort
  exit 1
  fi
}
