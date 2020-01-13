package studio.inprogress.componentstorage.sample.model.interactor

import io.reactivex.Completable
import io.reactivex.Single
import studio.inprogress.componentstorage.sample.model.entity.User

interface IUserInteractor {

    fun loadUser(): Single<User>

    fun logOut(): Completable
}