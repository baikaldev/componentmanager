package studio.inprogress.componentstorage.sample.components

import android.content.Context
import android.widget.Toast

class WorldGreetingComponent(private val appContext: Context) {

    private var counter: Int = 0

    fun sayHelloWorld() {
        counter++
        Toast.makeText(appContext, "Hello, World! Count = $counter", Toast.LENGTH_SHORT).show()
    }
}
