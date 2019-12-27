package studio.inprogress.componentstorage.sample.components.factory

import android.content.Context
import studio.inprogress.componentstorage.componentstorage.core.ComponentStorage
import studio.inprogress.componentstorage.componentstorage.core.factory.IComponentFactory
import studio.inprogress.componentstorage.sample.components.WorldGreetingComponent

class WorldGreetingComponentFactory(
    private val appContext: Context
) : IComponentFactory<WorldGreetingComponent> {

    override fun create(componentStorage: ComponentStorage): WorldGreetingComponent {
        return WorldGreetingComponent(
            appContext
        )
    }

    override fun getName() = WorldGreetingComponent::class.java.simpleName

    override fun isReleasable() = true
}