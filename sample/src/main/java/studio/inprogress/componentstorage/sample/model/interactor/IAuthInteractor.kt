package studio.inprogress.componentstorage.sample.model.interactor

import io.reactivex.Single
import studio.inprogress.componentstorage.sample.model.entity.User

interface IAuthInteractor {

    fun isAuthorized(): Single<Boolean>

    fun logIn(loginName: String, password: String): Single<User>
}