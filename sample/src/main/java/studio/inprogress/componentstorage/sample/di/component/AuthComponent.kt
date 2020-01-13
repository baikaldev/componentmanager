package studio.inprogress.componentstorage.sample.di.component

import dagger.Subcomponent
import studio.inprogress.componentstorage.sample.ui.SplashActivity
import studio.inprogress.componentstorage.sample.di.module.AuthModule
import studio.inprogress.componentstorage.sample.di.module.UserModule
import studio.inprogress.componentstorage.sample.di.scope.AuthScope
import studio.inprogress.componentstorage.sample.presentration.presenter.LoginPresenter
import studio.inprogress.componentstorage.sample.presentration.presenter.SplashPresenter

@Subcomponent(modules = [AuthModule::class])
@AuthScope
interface AuthComponent {
    fun createUserComponent(userModule: UserModule): UserComponent
    fun provideSplashPresenter(): SplashPresenter
    fun provideLoginPresenter(): LoginPresenter
}