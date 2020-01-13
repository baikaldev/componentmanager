package studio.inprogress.componentstorage.sample.di.factory

import studio.inprogress.componentstorage.componentstorage.core.ComponentStorage
import studio.inprogress.componentstorage.componentstorage.core.factory.IComponentFactory
import studio.inprogress.componentstorage.sample.di.component.AuthComponent
import studio.inprogress.componentstorage.sample.di.component.CoreComponent
import studio.inprogress.componentstorage.sample.di.module.AuthModule

class AuthComponentFactory : IComponentFactory<AuthComponent> {

    override fun create(componentStorage: ComponentStorage): AuthComponent {
        val coreComponent = componentStorage.getOrCreateComponent<CoreComponent>()
        return coreComponent.createAuthComponent(AuthModule())
    }

    override fun getName(): String {
        return AuthComponent::class.java.simpleName
    }

    override fun isReleasable(): Boolean {
        return false
    }
}