# ------------IMPORTS---------------
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



# [------------APK LOGIC---------------]

#(when publisching to PlayStore) DELETE ALL THIS (use secrets and new passwords here instead)
#CHANGING ANY THING HERE -> NEW APK, MUST DELETE AND REINSTALL APP
KEYSTORE_PASSWORD="12345678123"
KEY_ALIAS="myKeyAliass"
KEY_PASSWORD="12345678123"
KEYSTORE_PATH="$(pwd)/.github/GeneratedKey.keystore"

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




# [------------COMMIT LOGIC---------------]
set -euo pipefail

FILE="$CurrentFile"

log "::group::Reading target commit"
log "Control file: $FILE"

COMMIT=$(grep -oP 'GotoCommit\[\K[^\]]*' "$FILE" | head -n 1 | tr -d '[:space:]' || true)

if [ -z "$COMMIT" ]; then
   log "No GotoCommit value found. Keeping current checkout."
  exit 0
fi

log "Target commit: $COMMIT"
log "::endgroup::"

log "::group::Fetching repository history"
git fetch --no-tags --unshallow origin || true
git fetch --no-tags origin
git cat-file -e "$COMMIT^{commit}"
log "Target commit exists locally."
log "::endgroup::"
log "::group::Checking out target commit"

git checkout --detach "$COMMIT"

ACTUAL=$(git rev-parse HEAD)
TARGET=$(git rev-parse "$COMMIT")

log "Target:   $TARGET"
log "Checked out: $ACTUAL"

if [ "$ACTUAL" != "$TARGET" ]; then
   log "::error::Checkout verification failed!"
   exit 1
fi

log "Verified: repository is now at the requested commit."
log "Working directory: $(pwd)"
log "Subsequent steps will use this checkout unless they change it."
log "::endgroup::"

















          
