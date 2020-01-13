package studio.inprogress.componentstorage.sample.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import studio.inprogress.componentstorage.sample.model.preferences.IPreferences
import studio.inprogress.componentstorage.sample.model.preferences.impl.AppPreferences
import javax.inject.Singleton

@Module
class PreferencesModule(private val appContext: Context) {

    @Provides
    @Singleton
    fun providePreferences(): IPreferences {
        return AppPreferences(appContext)

    }
}
