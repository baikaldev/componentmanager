package studio.inprogress.componentstorage.sample.presentration.presenter

import com.arellomobile.mvp.InjectViewState
import studio.inprogress.componentstorage.sample.ext.applyCompletableSchedulers
import studio.inprogress.componentstorage.sample.ext.applySingleSchedulers
import studio.inprogress.componentstorage.sample.model.entity.User
import studio.inprogress.componentstorage.sample.model.interactor.IUserInteractor
import studio.inprogress.componentstorage.sample.presentration.view.UserView

@InjectViewState
class UserPresenter(
    private val userInteractor: IUserInteractor
) : BasePresenter<UserView>() {

    override fun onFirstViewAttach() {
        userInteractor.loadUser()
            .applySingleSchedulers()
            .subscribe(this::onLoadUserSuccess, this::onLoadUserFailed)
            .disposeOnDestroy()
    }

    private fun onLoadUserSuccess(user: User) {
        viewState.onLoadUserSuccess(user)
    }

    private fun onLoadUserFailed(throwable: Throwable) {
        viewState.onLoadUserFailed(throwable)
    }

    fun logOut() {
        userInteractor.logOut()
            .applyCompletableSchedulers()
            .subscribe()
            .disposeOnDestroy()
    }
}