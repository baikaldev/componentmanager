package studio.inprogress.componentstorage.sample.presentration.presenter

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<T : MvpView> : MvpPresenter<T>() {

    private val onDestroyCompositeSubscription = CompositeDisposable()

    protected fun disposeOnDestroy(subscription: Disposable) {
        onDestroyCompositeSubscription.add(subscription)
    }

    override fun onDestroy() {
        super.onDestroy()
        onDestroyCompositeSubscription.clear()
    }

    protected fun Disposable.disposeOnDestroy(): Disposable {
        disposeOnDestroy(this)
        return this
    }

    protected fun dispose(vararg disposableList: Disposable?) {
        for (disposable in disposableList) {
            if (disposable != null && disposable.isDisposed.not()) {
                disposable.dispose()
            }
        }
    }
}
