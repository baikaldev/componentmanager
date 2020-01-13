package studio.inprogress.componentstorage.sample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_login.*
import studio.inprogress.componentstorage.sample.R
import studio.inprogress.componentstorage.sample.di.component.AuthComponent
import studio.inprogress.componentstorage.sample.presentration.presenter.LoginPresenter
import studio.inprogress.componentstorage.sample.presentration.view.LoginView
import studio.inprogress.componentstorage.sample.ui.UserActivity

class LoginFragment : BaseFragment(), LoginView {

    companion object {
        fun createInstance(): LoginFragment = LoginFragment()
    }

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    private lateinit var authComponent: AuthComponent

    @ProvidePresenter
    fun provideLoginPresenter(): LoginPresenter {
        return authComponent.provideLoginPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        logInButton.setOnClickListener {
            presenter.logIn(
                loginName = loginNameEditText.text.toString(),
                password = passwordEditText.text.toString()
            )
        }
    }

    override fun initComponents() {
        authComponent = getComponentStorage().getOrCreateComponent()
    }

    override fun releaseComponents() {
        //no op
    }


    override fun onLogInProgress() {
        progressBar.visibility = View.VISIBLE
        content.visibility = View.GONE
    }

    override fun onLogInSuccess() {
        hideKeyboard()
        with(requireActivity()) {
            startActivity(UserActivity.createIntent(this))
            finish()
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLogInFailed(throwable: Throwable) {
        progressBar.visibility = View.GONE
        content.visibility = View.VISIBLE
        Toast.makeText(
            requireContext(),
            "Login failed: ${throwable.message}",
            Toast.LENGTH_SHORT
        ).show()
    }
}