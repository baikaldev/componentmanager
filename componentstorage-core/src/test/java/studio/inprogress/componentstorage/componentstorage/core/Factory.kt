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
    IParentReleasable {

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

    override fun getParentComponentNames(): List<String> {
        return arrayListOf(aComponentFactory.getName())
    }
}

class CComponentFactory(private val cModule: CModule) : IComponentFactory<CComponent>,
    IParentReleasable {

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

    override fun getParentComponentNames(): List<String> {
        return arrayListOf(bComponentFactory.getName())
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

class CompositeComponentFactory()
    : IComponentFactory<CompositeComponent>, IParentReleasable {

    override fun create(componentStorage: ComponentStorage): CompositeComponent {
        val aComponent = componentStorage.getOrCreateComponent<AComponent>()
        val dComponent = componentStorage.getOrCreateComponent<DComponent>()
        return CompositeComponent(aComponent, dComponent)
    }

    override fun getName(): String {
        return CompositeComponent::class.java.simpleName
    }

    override fun isReleasable(): Boolean {
        return true
    }

    override fun getParentComponentNames(): List<String> {
        return arrayListOf(AComponent::class.java.simpleName, DComponent::class.java.simpleName)
    }
}

class DComponentFactory: IComponentFactory<DComponent> {
    override fun create(componentStorage: ComponentStorage): DComponent {
        return DComponent()
    }

    override fun getName(): String {
        return DComponent::class.java.simpleName
    }

    override fun isReleasable(): Boolean {
        return true
    }
}
