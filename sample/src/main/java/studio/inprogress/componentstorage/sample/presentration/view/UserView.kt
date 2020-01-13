package studio.inprogress.componentstorage.sample.presentration.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import studio.inprogress.componentstorage.sample.model.entity.User

@StateStrategyType(SingleStateStrategy::class)
interface UserView : MvpView {
    fun onLoadUserSuccess(user: User)

    fun onLoadUserFailed(throwable: Throwable)
}
