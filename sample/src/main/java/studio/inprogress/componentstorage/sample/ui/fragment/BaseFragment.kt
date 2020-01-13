package studio.inprogress.componentstorage.sample.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import studio.inprogress.componentstorage.componentstorage.core.ComponentStorage
import studio.inprogress.componentstorage.sample.App
import studio.inprogress.componentstorage.sample.moxy.MvpAppCompatFragment

abstract class BaseFragment : MvpAppCompatFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        initComponents()
        super.onCreate(savedInstanceState)
    }

    open fun initComponents() {}

    protected fun getComponentStorage(): ComponentStorage {
        return App.componentStorage
    }

    override fun onDestroy() {
        releaseComponents()
        super.onDestroy()
    }

    open fun releaseComponents() {}

    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    protected fun showToast(
        @StringRes
        resId: Int
    ) {
        Toast.makeText(requireContext(), resId, Toast.LENGTH_SHORT).show()
    }

    protected fun hideKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = requireActivity().currentFocus
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

}