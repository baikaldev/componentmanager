package studio.inprogress.componentstorage.sample.di.factory

import studio.inprogress.componentstorage.componentstorage.core.ComponentStorage
import studio.inprogress.componentstorage.componentstorage.core.factory.IComponentFactory
import studio.inprogress.componentstorage.sample.di.component.AuthComponent
import studio.inprogress.componentstorage.sample.di.component.UserComponent
import studio.inprogress.componentstorage.sample.di.module.UserModule
import studio.inprogress.componentstorage.sample.model.IAuthListener

class UserComponentFactory(private val authListener: IAuthListener) :
    IComponentFactory<UserComponent> {

    override fun create(componentStorage: ComponentStorage): UserComponent {
        val authComponent = componentStorage.getOrCreateComponent<AuthComponent>()
        return authComponent.createUserComponent(UserModule(authListener))
    }

    override fun getName(): String {
        return UserComponent::class.java.simpleName
    }

    override fun isReleasable(): Boolean {
        return true
    }
}