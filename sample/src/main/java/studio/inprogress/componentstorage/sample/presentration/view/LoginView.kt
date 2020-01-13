package studio.inprogress.componentstorage.sample.presentration.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface LoginView : MvpView {

    fun onLogInProgress()

    fun onLogInSuccess()

    fun onLogInFailed(throwable: Throwable)

}
