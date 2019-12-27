package studio.inprogress.componentstorage.sample.components.factory

import android.content.Context
import studio.inprogress.componentstorage.componentstorage.core.ComponentStorage
import studio.inprogress.componentstorage.componentstorage.core.factory.IComponentFactory
import studio.inprogress.componentstorage.sample.components.UserGreetingComponent

class UserGreetingComponentFactory(
    private val appContext: Context,
    private val userName: String
) : IComponentFactory<UserGreetingComponent> {

    override fun create(componentStorage: ComponentStorage): UserGreetingComponent {
        return UserGreetingComponent(
            appContext,
            userName
        )
    }

    override fun getName(): String {
        return UserGreetingComponent::class.java.simpleName + userName
    }

    override fun isReleasable() = true
}