package studio.inprogress.componentstorage.componentstorage.core

import studio.inprogress.componentstorage.componentstorage.core.factory.IComponentFactory


class AComponentFactory(private val aModule: AModule) : IComponentFactory<AComponent> {

    override fun isReleasable(): Boolean {
        return true
    }

    override fun getName(): String {
        return AComponent::class.java.simpleName
    }

    override fun create(componentStorage: ComponentStorage): AComponent {
        return AComponent(aModule)
    }
}

class BComponentFactory(private val bModule: BModule) : IComponentFactory<BComponent>,
    ParentReleasable {

    private val aComponentFactory = AComponentFactory(AModule())

    override fun isReleasable(): Boolean {
        return true
    }

    override fun getName(): String {
        return BComponent::class.java.simpleName
    }

    override fun create(componentStorage: ComponentStorage): BComponent {
        return componentStorage.getOrCreateComponent(aComponentFactory).createBComponent(bModule)
    }

    override fun getParentComponentName(): String {
        return aComponentFactory.getName()
    }
}

class CComponentFactory(private val cModule: CModule) : IComponentFactory<CComponent>,
    ParentReleasable {

    private val bComponentFactory: BComponentFactory = BComponentFactory(BModule())

    override fun isReleasable(): Boolean {
        return true
    }

    override fun getName(): String {
        return CComponent::class.java.simpleName + cModule.someData
    }

    override fun create(componentStorage: ComponentStorage): CComponent {
        return componentStorage.getOrCreateComponent(bComponentFactory).createCComponent(cModule)
    }

    override fun getParentComponentName(): String {
        return bComponentFactory.getName()
    }
}

class ConstComponentFactory(private val aModule: AModule) : IComponentFactory<AComponent> {

    override fun isReleasable(): Boolean {
        return false
    }

    override fun getName(): String {
        return AComponent::class.java.simpleName
    }

    override fun create(componentStorage: ComponentStorage): AComponent {
        return AComponent(aModule)
    }
}

