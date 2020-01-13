package studio.inprogress.componentstorage.sample.di.component

import dagger.Component
import studio.inprogress.componentstorage.sample.di.module.AuthModule
import studio.inprogress.componentstorage.sample.di.module.DatabaseModule
import studio.inprogress.componentstorage.sample.di.module.NetworkModule
import studio.inprogress.componentstorage.sample.di.module.PreferencesModule
import javax.inject.Singleton

@Component(
    modules = [
        NetworkModule::class,
        DatabaseModule::class,
        PreferencesModule::class
    ]
)
@Singleton
interface CoreComponent {
    fun createAuthComponent(authModule: AuthModule): AuthComponent
}