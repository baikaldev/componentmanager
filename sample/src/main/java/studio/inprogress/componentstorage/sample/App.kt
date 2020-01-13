package studio.inprogress.componentstorage.sample

import android.app.Application
import android.content.Intent
import android.util.Log
import studio.inprogress.componentstorage.componentstorage.core.ComponentStorage
import studio.inprogress.componentstorage.sample.di.factory.AuthComponentFactory
import studio.inprogress.componentstorage.sample.di.factory.CartComponentFactory
import studio.inprogress.componentstorage.sample.di.factory.CoreComponentFactory
import studio.inprogress.componentstorage.sample.di.factory.UserComponentFactory
import studio.inprogress.componentstorage.sample.model.IAuthListener
import studio.inprogress.componentstorage.sample.ui.SplashActivity

class App : Application(), IAuthListener {

    override fun onCreate() {
        super.onCreate()
        initComponentStorage()
    }

    private fun initComponentStorage() {
        componentStorage.registerComponentFactory(
            CoreComponentFactory(this),
            AuthComponentFactory(),
            UserComponentFactory(this),
            CartComponentFactory()
        )
    }

    companion object {
        val componentStorage: ComponentStorage =
            ComponentStorage().withLogger { logMessage ->
                if (BuildConfig.DEBUG) {
                    Log.d("ComponentStorage", logMessage)
                }
            }
    }

    override fun onLogOut() {
        componentStorage.clear()
        initComponentStorage()
        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}