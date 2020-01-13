package studio.inprogress.componentstorage.sample.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_user.*
import studio.inprogress.componentstorage.sample.App
import studio.inprogress.componentstorage.sample.R
import studio.inprogress.componentstorage.sample.di.component.UserComponent
import studio.inprogress.componentstorage.sample.model.entity.User
import studio.inprogress.componentstorage.sample.presentration.presenter.UserPresenter
import studio.inprogress.componentstorage.sample.presentration.view.UserView

class UserActivity : BaseMvpActivity(), UserView {

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, UserActivity::class.java)
    }

    @InjectPresenter
    lateinit var presenter: UserPresenter

    @ProvidePresenter
    fun provideUSerPresenter(): UserPresenter {
        return userComponent.provideUserPresenter()
    }

    private lateinit var userComponent: UserComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        initUI()
    }

    private fun initUI() {
        logOutButton.setOnClickListener {
            presenter.logOut()
            finishAffinity()
        }
    }

    override fun initComponent() {
        userComponent = getComponentStorage().getOrCreateComponent()
    }

    override fun releaseComponent() {
        getComponentStorage().releaseComponent<UserComponent>()
    }

    override fun onLoadUserSuccess(user: User) {
        userNameTextView.text = "Hello, ${user.name}"
    }

    override fun onLoadUserFailed(throwable: Throwable) {
        Toast.makeText(this, "Load user failed", Toast.LENGTH_SHORT).show()
    }

}
