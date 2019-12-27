package studio.inprogress.componentstorage.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import studio.inprogress.componentstorage.sample.components.UserGreetingComponent
import studio.inprogress.componentstorage.sample.components.WorldGreetingComponent
import studio.inprogress.componentstorage.sample.components.factory.UserGreetingComponentFactory

class MainActivity : AppCompatActivity() {

    private lateinit var worldGreetingComponent: WorldGreetingComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWorldGreetingComponent()
        initUI()
    }

    private fun initWorldGreetingComponent() {
        worldGreetingComponent = App.componentStorage.getOrCreateComponent()
    }

    private fun initUI() {
        greetingButton.setOnClickListener {
            val userGreetingComponent: UserGreetingComponent =
                App.componentStorage.getOrCreateComponent(
                    UserGreetingComponentFactory(
                        applicationContext,
                        getUserName()
                    )
                )
            userGreetingComponent.sayHello()
        }
        defaultGreetingButton.setOnClickListener {
            worldGreetingComponent.sayHelloWorld()
        }
        releaseComponentButton.setOnClickListener {
            App.componentStorage.releaseComponent(
                UserGreetingComponentFactory(
                    applicationContext,
                    getUserName()
                )
            )
        }
    }

    override fun onDestroy() {
        if (isFinishing) {
            App.componentStorage.releaseComponent<WorldGreetingComponent>()
        }
        super.onDestroy()
    }

    private fun getUserName() = userNameEditText.text.toString()
}
