package studio.inprogress.componentstorage.sample.presentration.presenter

import com.arellomobile.mvp.InjectViewState
import studio.inprogress.componentstorage.sample.ext.applySingleSchedulers
import studio.inprogress.componentstorage.sample.model.interactor.IAuthInteractor
import studio.inprogress.componentstorage.sample.presentration.view.SplashView

@InjectViewState
class SplashPresenter(
    private val authInteractor: IAuthInteractor
) : BasePresenter<SplashView>() {

    override fun onFirstViewAttach() {
        authInteractor.isAuthorized()
            .applySingleSchedulers()
            .subscribe(this::onCheckAuthorization, {})
            .disposeOnDestroy()
    }

    private fun onCheckAuthorization(isAuthorized: Boolean) {
        if (isAuthorized) {
            viewState.onAuthorized()
        } else {
            viewState.onNeedAuthorization()
        }
    }
}