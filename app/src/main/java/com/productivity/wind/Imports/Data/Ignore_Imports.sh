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


KEYSTORE_PASSWORD="123456"
KEY_ALIAS="my-key"
KEY_PASSWORD="123456"
KEYSTORE_PATH="$(pwd)/app/my-release-key.keystore"

Create_Keystore() {
    mkdir -p app

    if [ ! -f "$KEYSTORE_PATH" ]; then
        echo "🔹 Keystore not found, creating new one..."
        keytool -genkeypair -v \
            -keystore "$KEYSTORE_PATH" \
            -storepass "$KEYSTORE_PASSWORD" \
            -alias "$KEY_ALIAS" \
            -keypass "$KEY_PASSWORD" \
            -keyalg RSA \
            -keysize 2048 \
            -validity 10000 \
            -dname "CN=Temp, OU=Temp, O=Temp, L=Temp, S=Temp, C=US"
        echo "✅ Keystore created at $KEYSTORE_PATH"

 

        git config user.email "narvydas.burinskas@gmail.com"
        git config user.name "potatochad"

        # Commit & push automatically
        echo "📦 Adding keystore to git..."
        git add -f "$KEYSTORE_PATH"
        git commit -m "Add generated keystore"
        git push
        echo "✅ Keystore committed and pushed!"
    else
        echo "🔹 Keystore already exists, using existing file."
    fi
}


Build_APK() {
    Create_Keystore

    log "🚀 Building signed APK..."

    ./gradlew assembleRelease -x ktlintCheck -x ktlintKotlinScriptCheck \
        -Pandroid.injected.signing.store.file="$KEYSTORE_PATH" \
        -Pandroid.injected.signing.store.password="$KEYSTORE_PASSWORD" \
        -Pandroid.injected.signing.key.alias="$KEY_ALIAS" \
        -Pandroid.injected.signing.key.password="$KEY_PASSWORD"


        
}






