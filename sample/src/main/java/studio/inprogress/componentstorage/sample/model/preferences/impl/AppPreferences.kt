package studio.inprogress.componentstorage.sample.model.preferences.impl

import android.content.Context
import android.content.SharedPreferences
import studio.inprogress.componentstorage.sample.model.entity.User
import studio.inprogress.componentstorage.sample.model.preferences.IPreferences

class AppPreferences(appContext: Context) : IPreferences {

    companion object {
        const val USER_NAME = "USER_NAME"
    }

    private val preferences: SharedPreferences =
        appContext.getSharedPreferences("sample", Context.MODE_PRIVATE)

    override fun getUser(): User {
        val userName = preferences.getString(USER_NAME, "Unknown")
        return User(userName!!)
    }

    override fun clear() {
        preferences.edit().clear().apply()
    }

    override fun saveUser(user: User) {
        preferences.edit().putString(USER_NAME, user.name).apply()
    }
}