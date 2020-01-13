package studio.inprogress.componentstorage.sample.model.interactor.impl

import io.reactivex.Single
import studio.inprogress.componentstorage.sample.model.entity.User
import studio.inprogress.componentstorage.sample.model.interactor.IAuthInteractor
import studio.inprogress.componentstorage.sample.model.preferences.IPreferences

class AuthInteractor(
    private val appPreferences: IPreferences
) : IAuthInteractor {

    override fun isAuthorized(): Single<Boolean> {
        return Single.fromCallable {
            val user = appPreferences.getUser()
            return@fromCallable user.name != "Unknown"
        }
    }

    override fun logIn(loginName: String, password: String): Single<User> {
        return Single.fromCallable {
            if ("admin" == loginName && "admin" == password) {
                val user = User("admin")
                appPreferences.saveUser(user)
                return@fromCallable user
            } else {
                throw IllegalStateException("User name and password will be \"admin\"")
            }
        }

    }

}