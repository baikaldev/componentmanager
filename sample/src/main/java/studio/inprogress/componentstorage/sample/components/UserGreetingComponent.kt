package studio.inprogress.componentstorage.sample.components

import android.content.Context
import android.widget.Toast

class UserGreetingComponent(
    private val appContext: Context,
    private val userName: String
) {

    private var counter: Int = 0

    fun sayHello() {
        counter++
        Toast.makeText(appContext, "Hello, $userName! Count = $counter", Toast.LENGTH_SHORT).show()
    }
}