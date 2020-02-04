package com.sauravsharan.appscrip.data.preference

interface PreferenceHelper {

    fun isAppFirstTimeLaunched(): Boolean

    fun isAppFirstTimeLaunched(isFirstTimeLaunched: Boolean)

    fun resetPreferences()

}