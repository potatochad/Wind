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




# [------------COMMIT LOGIC & IMPORTS FOR IT---------------]

Fun_ErrorCatcher() {
    set -Eeuo pipefail

    trap 'rc=$?; printf "\n💥 BROKEN 💥\n🔹 Line: $LINENO\n🔹 Exit code: $rc\n🔹 Command: %s\n🫡 Mission failed, we got em.\n\n" "$BASH_COMMAND" >&2; exit "$rc"' ERR
}

File_firstString_insideBrackets() {
    grep -oP '(?<=GotoCommit\[)[^]]+' "$1" |
        head -n 1 || true
}

isEmpty() {
    [[ -z "${1:-}" ]]
}

#dots are allowed in Bash function names
Git.allCommits() {
    if [[ "$(git rev-parse --is-shallow-repository)" != "true" ]]; then
        log "✅ Repository already has full history."
        return 0
    fi

    log "📦 Downloading full commit history..."

    if git fetch --no-tags --unshallow origin; then
        log "✅ Full history downloaded!"
    else
        log "⚠️ Couldn't download full history. Continuing anyway."
        return 0
    fi
}

Git.latestCommits() {
    log "🔄 Checking for new commits..."

    if git fetch --no-tags origin; then
        log "✅ All caught up!"
    else
        local rc=$?
        log "❌ Couldn't fetch new commits (exit code $rc)."
        return "$rc"
    fi
}

Git.commitExists() {
    if [[ $# -eq 0 ]] || isEmpty "${1:-}"; then
        log "❌ No commit was provided."
        return 2
    fi

    if git cat-file -e "$1^{commit}" 2>/dev/null; then
        return 0
    else
        log "⚠️ Commit not found: $1"
        return 1
    fi
}


Git.checkoutCommit() {
    if [[ $# -eq 0 ]] || isEmpty "${1:-}"; then
        log "❌ No commit was provided."
        return 2
    fi

    log "🔄 Checking out commit: $1"

    if git checkout --detach "$1"; then
        log "✅ Checked out target commit."
    else
        local rc=$?
        log "❌ Couldn't check out commit (exit code $rc)."
        return "$rc"
    fi
}

#force project to a commit, ignores github (but doesnt actually do anything about main)
Git.forceMainToCommit() {
    if [[ $# -eq 0 ]] || isEmpty "${1:-}"; then
        log "❌ No target commit provided."
        return 2
    fi

    local commit="$1"
    local file

    if ! git cat-file -e "$commit^{commit}" 2>/dev/null; then
        log "❌ Target commit doesn't exist."
        return 1
    fi

    log "🔨 Forcing project to match target commit..."
    log "🔒 Keeping .github/ untouched."

    # Remove tracked and untracked files outside .github/
    # that aren't present in the target commit.
    while IFS= read -r -d '' file; do
        if ! git cat-file -e "$commit:$file" 2>/dev/null; then
            if [[ -d "$file" && ! -L "$file" ]]; then
                rm -rf -- "$file"
            else
                rm -f -- "$file"
            fi
        fi
    done < <(find . -mindepth 1 -not -path './.git' \
        -not -path './.git/*' \
        -not -path './.github' \
        -not -path './.github/*' -print0)

    # Restore target files without touching .github/.
    if ! git restore --source="$commit" --worktree -- \
        . ':(exclude).github' ':(exclude).github/**'; then
        log "❌ Failed to restore project files."
        return 1
    fi

    log "✅ Project now matches the target commit!"
}


GoToCommit(){
   Fun_ErrorCatcher
   
   #local variable, if null-> ""
   local COMMIT="${1:-}"
   
   if isEmpty "$COMMIT"; then
      log "No GotoCommit value found. Keeping current checkout."
      exit 0 #stop
    fi
    
    log "Target commit: $COMMIT"

    Git.allCommits
    Git.latestCommits
    
    if Git.commitExists "$COMMIT"; then
       log "Target commit exists locally."
    else
       log "❌ Target commit wasn't found locally."
       exit 1 #leaves
    fi

    Git.checkoutCommit "$COMMIT"
}
















          
