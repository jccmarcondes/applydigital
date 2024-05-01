package com.example.applydigitalchallenge.data.local

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import com.example.applydigitalchallenge.util.PREFERENCES_APPLY_DIGITAL
import com.example.applydigitalchallenge.util.PREFERENCES_OLD_CARD_ID_LIST_KEY

/**
 * The PreferenceKeys object contains constant values representing keys used in the application preferences.
 * It includes the OLD_CARD_ID_LIST_KEY, which is used to retrieve and store a set of old card IDs in preferences.
 */
object PreferenceKeys {
    const val OLD_CARD_ID_LIST_KEY = PREFERENCES_OLD_CARD_ID_LIST_KEY
}

/**
 * The AppPreferences class represents the application preferences, providing methods to read and write preferences data.
 * It uses the SharedPreferences API to interact with the underlying storage.
 */
class AppPreferences(context: Context) {
    /**
     * The SharedPreferences instance used for accessing preference data.
     */
    private val sharedPreferences = context.getSharedPreferences(
        PREFERENCES_APPLY_DIGITAL,
        Context.MODE_PRIVATE
    )

    /**
     * Retrieves a string set from preferences based on the specified key.
     *
     * @param key The key used to retrieve the string set.
     * @param defaultValue The default value to return if the key is not found.
     * @return The retrieved string set, or the default value if not found.
     */
    fun getStringSet(key: String, defaultValue: Set<String>): Set<String> {
        return sharedPreferences.getStringSet(key, defaultValue) ?: defaultValue
    }

    /**
     * Stores a string set in preferences based on the specified key.
     *
     * @param key The key used to store the string set.
     * @param value The string set to store.
     */
    fun putStringSet(key: String, value: Set<String>) {
        sharedPreferences.edit().putStringSet(key, value).apply()
    }
}

/**
 * A composable function that provides access to the application preferences.
 * It creates and remembers an instance of AppPreferences based on the current context.
 *
 * @return An instance of AppPreferences.
 */
@Composable
fun rememberAppPreferences(): AppPreferences {
    val context = LocalContext.current
    return remember { AppPreferences(context) }
}

/**
 * A composable function that provides access to a string set preference stored in the application preferences.
 * It retrieves the preference value and automatically updates it when modified.
 *
 * @param key The key used to identify the preference.
 * @param defaultValue The default value to use if the preference is not found.
 * @return A SnapshotStateList containing the string set preference value.
 */
@Composable
fun rememberStringSetPreference(
    key: String,
    defaultValue: Set<String>
): SnapshotStateList<String> {
    val appPreferences = rememberAppPreferences()
    val storedValue = appPreferences.getStringSet(key, defaultValue)
    val state = remember { mutableStateListOf(*storedValue.toTypedArray()) }

    if (state != storedValue) {
        appPreferences.putStringSet(key, state.toSet())
    }

    return state
}