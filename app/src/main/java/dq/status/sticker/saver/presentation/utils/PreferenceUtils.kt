package dq.status.sticker.saver.presentation.utils

import android.content.Context
import android.content.SharedPreferences
import dq.status.sticker.saver.R

object PreferenceUtils {
    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    fun init(context: Context) {
        preferences =
            context.getSharedPreferences(
                "${
                    context.getString(R.string.app_name).replace(" ", "_")
                }_prefs", Context.MODE_PRIVATE
            )
    }

    fun getPrefString(key: String, defaultValue: String): String? {
        return preferences.getString(key, defaultValue)
    }

    fun getPrefBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun getPrefInt(key: String, defaultValue: Int): Int {
        return preferences.getInt(key, defaultValue)
    }

    fun putPrefString(key: String, value: String): Boolean {
        editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
        return true
    }

    fun putPrefInt(key: String, value: Int): Boolean {
        editor = preferences.edit()
        editor.putInt(key, value)
        editor.apply()
        return true
    }

    fun putPrefBoolean(key: String, value: Boolean): Boolean {
        editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
        return true
    }
}

object PrefKeys{
    const val PREF_KEY_WP_PERMISSION_GRANTED = "PREF_KEY_PERMISSION_GRANTED"
    const val PREF_KEY_WP_TREE_URI = "PREF_KEY_TREE_URI"
    const val PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED = "PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED"
    const val PREF_KEY_WP_BUSINESS_TREE_URI = "PREF_KEY_WP_BUSINESS_TREE_URI"
}