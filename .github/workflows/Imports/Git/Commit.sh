source .github/workflows/Imports/Any.sh



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

# Restore current .github/ files after going back to an old commit.
Git.goToCommitInDiskWithCurrentGitWorkflows() {
    local current_main="$1"
    local file

    log "🔄 Preserving .github/ from main: $current_main"

    git restore --source="$current_main" --staged --worktree -- .github/ || {
        log "❌ Failed to restore .github/."
        return 1
    }

    while IFS= read -r -d '' file; do
        if ! git diff --quiet "$current_main" -- "$file" ||
           ! git diff --cached --quiet "$current_main" -- "$file"; then
            log "❌ Verification failed: $file"
            return 1
        fi
    done < <(git ls-tree -r --name-only -z "$current_main" -- .github/)

    log "✅ Current main's .github/ files verified."
}

# Restore the target commit's .github/ files.
Git.goToCommitInDisk() {
    local commit="$1"
    local file

    log "🔄 Restoring .github/ from $commit..."

    git restore --source="$commit" --staged --worktree -- .github/ || {
        log "❌ Failed to restore .github/."
        return 1
    }

    while IFS= read -r -d '' file; do
        if ! git diff --quiet "$commit" -- "$file" ||
           ! git diff --cached --quiet "$commit" -- "$file"; then
            log "❌ Verification failed: $file"
            return 1
        fi
    done < <(git ls-tree -r --name-only -z "$commit" -- .github/)

    log "✅ .github/ files verified."
}

Git.commitAndPush() {
    local commit="$1"

    git add -A || return 1

    if ! git diff --cached --quiet; then
        git -c user.name="github-actions[bot]" \
            -c user.email="41898282+github-actions[bot]@users.noreply.github.com" \
            commit -m "Restore project to $commit while preserving .github" || return 1
    else
        log "✅ Nothing to commit."
    fi

    git push --force-with-lease origin HEAD:main || return 1
}

#force project to a commit, ignores github
Git.forceMainToCommit() {
    if [[ $# -eq 0 ]] || isEmpty "${1:-}"; then
        log "❌ No target commit provided."
        return 2
    fi

    local commit="$1"
    local current_main
    local tree

    if ! git cat-file -e "$commit^{commit}" 2>/dev/null; then
        log "❌ Target commit doesn't exist."
        return 1
    fi

    # gets the COMMITS ID?
    current_main=$(git rev-parse refs/remotes/origin/main) || return 1

    # loads files in linux
    git checkout --detach "$commit" || return 1

    Git.goToCommitInDiskWithCurrentGitWorkflows "$current_main"

    Git.commitAndPush "$commit"
    
    log "✅ Main updated; .github/ preserved."
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

    Git.forceMainToCommit "$COMMIT"
}
















          
