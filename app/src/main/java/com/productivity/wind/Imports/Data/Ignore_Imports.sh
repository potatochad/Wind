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













Build_APK() {
    set -e

    # Make sure the app folder exists
    mkdir -p app
    KEYSTORE_FILE="$(pwd)/app/my-release-key.keystore"

    # Restore keystore from base64 secret if it exists
    if [ -f "$KEYSTORE_FILE" ]; then
        echo "✅ Keystore exists locally."
    elif [ -n "$KEYSTORE_BASE64" ]; then
        echo "🔹 Restoring keystore from GitHub secret..."
        echo "$KEYSTORE_BASE64" | tr -d '\n' | base64 --decode > "$KEYSTORE_FILE"
    else
        echo "🔹 Creating new keystore..."
        keytool -genkeypair -v \
            -keystore "$KEYSTORE_FILE" \
            -storepass "$KEYSTORE_PASSWORD" \
            -alias "$KEY_ALIAS" \
            -keypass "$KEY_PASSWORD" \
            -keyalg RSA \
            -keysize 2048 \
            -validity 10000 \
            -dname "CN=Temp, OU=Temp, O=Temp, L=Temp, S=Temp, C=US"
        echo "📦 Base64 of keystore (copy into KEYSTORE_BASE64 secret):"
        base64 "$KEYSTORE_FILE"
    fi

    # Build the APK
    ./gradlew assembleRelease -x ktlintCheck -x ktlintKotlinScriptCheck \
        -Pandroid.injected.signing.store.file="$KEYSTORE_FILE" \
        -Pandroid.injected.signing.store.password="$KEYSTORE_PASSWORD" \
        -Pandroid.injected.signing.key.alias="$KEY_ALIAS" \
        -Pandroid.injected.signing.key.password="$KEY_PASSWORD"

    echo "✅ APK build finished!"
}








