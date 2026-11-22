source .github/workflows/Imports/Any.sh



#(when publisching to PlayStore) DELETE ALL THIS (use secrets and new passwords here instead)
#CHANGING ANY THING HERE -> NEW APK, MUST DELETE AND REINSTALL APP
KEYSTORE_PASSWORD="12345678123"
KEY_ALIAS="myKeyAliass"
KEY_PASSWORD="12345678123"
KEYSTORE_PATH="$(pwd)/app/keyyStore.keystore"

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




   
