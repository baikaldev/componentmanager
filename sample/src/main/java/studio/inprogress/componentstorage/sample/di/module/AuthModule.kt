package studio.inprogress.componentstorage.sample.di.module

import dagger.Module
import dagger.Provides
import studio.inprogress.componentstorage.sample.di.scope.AuthScope
import studio.inprogress.componentstorage.sample.model.interactor.IAuthInteractor
import studio.inprogress.componentstorage.sample.model.interactor.impl.AuthInteractor
import studio.inprogress.componentstorage.sample.model.preferences.IPreferences
import studio.inprogress.componentstorage.sample.presentration.presenter.LoginPresenter
import studio.inprogress.componentstorage.sample.presentration.presenter.SplashPresenter

@Module
class AuthModule {

    @Provides
    @AuthScope
    fun provideAuthInteractor(appPreferences: IPreferences): IAuthInteractor {
        return AuthInteractor(appPreferences)
    }

    @Provides
    fun provideSplashPresenter(authInteractor: IAuthInteractor): SplashPresenter {
        return SplashPresenter(authInteractor)
    }

    @Provides
    fun provideLoginPresenter(authInteractor: IAuthInteractor): LoginPresenter {
        return LoginPresenter(authInteractor)
    }
}
