package studio.inprogress.componentstorage.sample.di.factory

import studio.inprogress.componentstorage.componentstorage.core.ComponentStorage
import studio.inprogress.componentstorage.componentstorage.core.factory.IComponentFactory
import studio.inprogress.componentstorage.sample.di.component.CartComponent
import studio.inprogress.componentstorage.sample.di.component.UserComponent
import studio.inprogress.componentstorage.sample.di.module.CartModule

class CartComponentFactory : IComponentFactory<CartComponent> {
    override fun create(componentStorage: ComponentStorage): CartComponent {
        val userComponent = componentStorage.getOrCreateComponent<UserComponent>()
        return userComponent.createCartComponent(CartModule())
    }

    override fun getName(): String {
        return CartComponent::class.java.simpleName
    }

    override fun isReleasable(): Boolean {
        return true
    }
}