package studio.inprogress.componentstorage.sample.presentration.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.disposables.Disposable
import studio.inprogress.componentstorage.sample.ext.applySingleSchedulers
import studio.inprogress.componentstorage.sample.ext.safeDispose
import studio.inprogress.componentstorage.sample.model.entity.User
import studio.inprogress.componentstorage.sample.model.interactor.IAuthInteractor
import studio.inprogress.componentstorage.sample.presentration.view.LoginView
import java.util.concurrent.TimeUnit

@InjectViewState
class LoginPresenter(
    private val authInteractor: IAuthInteractor
) : BasePresenter<LoginView>() {

    private var logInDisposable: Disposable? = null

    fun logIn(loginName: String, password: String) {
        logInDisposable.safeDispose()
        onLogInProgress()
        logInDisposable = authInteractor.logIn(loginName, password)
            .delay(1500L, TimeUnit.MILLISECONDS)
            .applySingleSchedulers()
            .subscribe(this::onLogInSuccess, this::onLogInFailed)
            .disposeOnDestroy()
    }

    private fun onLogInProgress() {
        viewState.onLogInProgress()
    }

    private fun onLogInSuccess(user: User) {
        viewState.onLogInSuccess()
    }

    private fun onLogInFailed(throwable: Throwable) {
        viewState.onLogInFailed(throwable)
    }

}
