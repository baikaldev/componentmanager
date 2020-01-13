package studio.inprogress.componentstorage.sample.ui

import android.os.Bundle
import android.widget.Toast
import studio.inprogress.componentstorage.sample.App
import studio.inprogress.componentstorage.sample.moxy.MvpAppCompatActivity

abstract class BaseMvpActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        initComponent()
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            releaseComponent()
        }
    }

    open fun releaseComponent() {}

    abstract fun initComponent()

    fun getComponentStorage() = App.componentStorage

    fun showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, duration).show()
    }
}