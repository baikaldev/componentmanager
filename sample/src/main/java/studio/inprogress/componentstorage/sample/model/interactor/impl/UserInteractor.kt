package studio.inprogress.componentstorage.sample.model.interactor.impl

import io.reactivex.Completable
import io.reactivex.Single
import studio.inprogress.componentstorage.sample.model.IAuthListener
import studio.inprogress.componentstorage.sample.model.entity.User
import studio.inprogress.componentstorage.sample.model.interactor.IUserInteractor
import studio.inprogress.componentstorage.sample.model.preferences.IPreferences

class UserInteractor(
    private val appPreferences: IPreferences,
    private val authListener: IAuthListener
) : IUserInteractor {

    override fun loadUser(): Single<User> {
        return Single.fromCallable {
            appPreferences.getUser()
        }
    }

    override fun logOut(): Completable {
        return Completable.fromCallable {
            appPreferences.clear()
            authListener.onLogOut()
        }
    }
}