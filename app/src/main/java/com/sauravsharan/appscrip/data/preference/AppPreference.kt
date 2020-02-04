package com.sauravsharan.appscrip.data.preference

import android.content.Context
import androidx.core.content.edit
import com.sauravsharan.appscrip.util.Constants.PREFERENCE_FILE_NAME

class AppPreference (context: Context, prefName: String = PREFERENCE_FILE_NAME) :
    PreferenceHelper {

    private var sharedPreference = context.getSharedPreferences(
        prefName,
        Context.MODE_PRIVATE
    )

    companion object {
        private const val IS_REGISTERED_KEY = "IS_REGISTERED_KEY"
        private const val IS_APP_FIRST_TIME_LAUNCHED_KEY = "IS_APP_FIRST_TIME_LAUNCHED_KEY"
    }

    override fun isAppFirstTimeLaunched(): Boolean {
        return sharedPreference.getBoolean(IS_APP_FIRST_TIME_LAUNCHED_KEY, true)
    }

    override fun isAppFirstTimeLaunched(isFirstTimeLaunched: Boolean) {
        sharedPreference.edit { putBoolean(IS_APP_FIRST_TIME_LAUNCHED_KEY, isFirstTimeLaunched) }
    }

    override fun resetPreferences() {
        sharedPreference.edit{ clear() }
    }


}