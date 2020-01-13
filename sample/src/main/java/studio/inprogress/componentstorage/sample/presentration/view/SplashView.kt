package studio.inprogress.componentstorage.sample.presentration.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface SplashView : MvpView {
    fun onAuthorized()

    fun onNeedAuthorization()
}
