package studio.inprogress.componentstorage.sample

import android.app.Application
import android.util.Log
import studio.inprogress.componentstorage.componentstorage.core.ComponentStorage
import studio.inprogress.componentstorage.sample.components.factory.WorldGreetingComponentFactory

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initComponentStorage()
    }

    private fun initComponentStorage() {
        componentStorage.registerComponentFactory(
            WorldGreetingComponentFactory(
                appContext = this
            )
        )
    }

    companion object {
        val componentStorage: ComponentStorage by lazy {
            ComponentStorage().withLogger { logMessage ->
                if (BuildConfig.DEBUG) {
                    Log.d("ComponentStorage", logMessage)
                }
            }
        }
    }
}