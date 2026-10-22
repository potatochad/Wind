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

    [[ -f "$FILE" ]] || { echo "::error::File not found: $FILE"; exit 1; }
    COMMIT=$(grep -oP 'GotoCommit\[\K[^\]]*' "$FILE" | head -n 1 | tr -d '[:space:]' || true)

    if [[ -z "$COMMIT" ]]; then
      echo "No target commit; keeping current checkout."
      exit 0
    fi

    echo "::group::Fetching history"
    if [[ "$(git rev-parse --is-shallow-repository)" == true ]]; then
      git fetch --no-tags --unshallow origin
    fi
    git cat-file -e "${COMMIT}^{commit}" 2>/dev/null || git fetch --no-tags origin "$COMMIT"
    git cat-file -e "${COMMIT}^{commit}" || { echo "::error::Commit not found: $COMMIT"; exit 1; }
    echo "::endgroup::"

    echo "::group::Checking out target"
    git checkout --detach "$COMMIT"
    TARGET=$(git rev-parse "${COMMIT}^{commit}")
    ACTUAL=$(git rev-parse HEAD)

    echo "Branch: ${GITHUB_REF_NAME:-unknown}"
    echo "Current branch: $(git branch --show-current)"
    echo "Target: $TARGET"
    echo "HEAD:   $ACTUAL"
    git log -1 --oneline --decorate

    [[ "$ACTUAL" == "$TARGET" ]] || { echo "::error::Checkout verification failed"; exit 1; }
    echo "Verified target checkout at $(pwd)"
    echo "::endgroup::"



