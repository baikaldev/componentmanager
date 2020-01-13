package studio.inprogress.componentstorage.sample.ui

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import studio.inprogress.componentstorage.sample.App
import studio.inprogress.componentstorage.sample.R
import studio.inprogress.componentstorage.sample.di.component.AuthComponent
import studio.inprogress.componentstorage.sample.presentration.presenter.SplashPresenter
import studio.inprogress.componentstorage.sample.presentration.view.SplashView
import studio.inprogress.componentstorage.sample.ui.fragment.LoginFragment

class SplashActivity : BaseMvpActivity(), SplashView {

    @InjectPresenter
    lateinit var presenter: SplashPresenter
    private lateinit var authComponent: AuthComponent

    @ProvidePresenter
    fun provideSplashPresenter(): SplashPresenter {
        return authComponent.provideSplashPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun initComponent() {
        authComponent = App.componentStorage.getOrCreateComponent()
    }

    override fun releaseComponent() {
        //no op
    }

    override fun onAuthorized() {
        startActivity(UserActivity.createIntent(this))
        finish()
    }

    override fun onNeedAuthorization() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                LoginFragment.createInstance(),
                LoginFragment::class.java.simpleName
            )
            .commit()
    }
}
