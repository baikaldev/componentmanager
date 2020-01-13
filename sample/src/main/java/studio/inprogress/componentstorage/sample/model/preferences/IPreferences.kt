package studio.inprogress.componentstorage.sample.model.preferences

import studio.inprogress.componentstorage.sample.model.entity.User

interface IPreferences {

    fun getUser(): User

    fun saveUser(user: User)

    fun clear()
}