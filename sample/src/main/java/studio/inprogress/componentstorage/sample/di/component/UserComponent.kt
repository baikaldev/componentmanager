package studio.inprogress.componentstorage.sample.di.component

import dagger.Subcomponent
import studio.inprogress.componentstorage.sample.di.module.CartModule
import studio.inprogress.componentstorage.sample.di.module.UserModule
import studio.inprogress.componentstorage.sample.di.scope.UserScope
import studio.inprogress.componentstorage.sample.presentration.presenter.UserPresenter

@Subcomponent(
    modules = [
        UserModule::class
    ]
)
@UserScope
interface UserComponent {
    fun createCartComponent(cartModule: CartModule): CartComponent
    fun provideUserPresenter(): UserPresenter
}