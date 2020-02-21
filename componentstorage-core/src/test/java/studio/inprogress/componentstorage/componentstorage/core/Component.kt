package studio.inprogress.componentstorage.componentstorage.core

class AComponent(val aModule: AModule) {
    fun createBComponent(bModule: BModule): BComponent {
        return BComponent(bModule)
    }
}

class AModule

class BComponent(val bModule: BModule) {
    fun createCComponent(cModule: CModule): CComponent {
        return CComponent(cModule)
    }
}

class BModule

class CComponent(val cModule: CModule) {
    fun getData() = cModule.someData
}

class CModule(var someData: String = "")

class CompositeComponent(val aComponent: AComponent, val dComponent: DComponent)

class DComponent