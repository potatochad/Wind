alias log='echo'
          
          make_folder() {
            mkdir -p "$1"
            log "Folder '$(pwd)/$1' added/exists"
          }
          
          try() {
            CMD=()
            FAIL=()
            MODE="cmd"

            for arg in "$@"; do
            if [[ $arg == "onFail" ]]; then
            MODE="fail"
            continue
            fi
            if [[ $MODE == "cmd" ]]; then
              CMD+=("$arg")
            else
              FAIL+=("$arg")
            fi
            done
            # Run main command
            "${CMD[@]}" || "${FAIL[@]}"
          }
