package studio.inprogress.componentstorage.sample.di.module

import dagger.Module
import dagger.Provides
import studio.inprogress.componentstorage.sample.di.scope.UserScope
import studio.inprogress.componentstorage.sample.model.IAuthListener
import studio.inprogress.componentstorage.sample.model.interactor.IUserInteractor
import studio.inprogress.componentstorage.sample.model.interactor.impl.UserInteractor
import studio.inprogress.componentstorage.sample.model.preferences.IPreferences
import studio.inprogress.componentstorage.sample.presentration.presenter.UserPresenter

@Module
class UserModule(private val authListener: IAuthListener) {

    @Provides
    @UserScope
    fun provideUserInteractor(appPreferences: IPreferences): IUserInteractor {
        return UserInteractor(appPreferences, authListener)
    }

    @Provides
    fun provideUserPresenter(userInteractor: IUserInteractor): UserPresenter {
        return UserPresenter(userInteractor)
    }
}