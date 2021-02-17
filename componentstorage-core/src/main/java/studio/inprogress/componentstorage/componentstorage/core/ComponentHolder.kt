package studio.inprogress.componentstorage.componentstorage.core

import studio.inprogress.componentstorage.componentstorage.core.factory.IComponentFactory

class ComponentHolder<T : Any>(
    val componentFactory: IComponentFactory<T>,
    private val componentStorage: ComponentStorage,
    private val logger: (logMessage: String) -> Unit = {}
) {

    private var component: T? = null

    private var ownerCount = 0

    @Synchronized
    fun getOrCreate(): T {
        if (component == null) {
            component = componentFactory.create(componentStorage)
            logger("Component $component created. Is releasable ${isReleasable()}")
        }
        if (isReleasable()) {
            ownerCount++
            logger("Component $component owner count $ownerCount")
        }
        return component!!
    }

    private fun isReleasable() = componentFactory.isReleasable()

    @Synchronized
    fun release() {
        if (isReleasable()) {
            if (component == null) return
            logger("release on  $component invoked")
            ownerCount--
            logger("Component $component owner count after release $ownerCount")
            if (ownerCount <= 0) {
                releaseComponent()
            }
        }
    }

    fun isReleased() = ownerCount <= 0 && isReleasable()

    fun getOwnerCount() = ownerCount

    private fun releaseComponent() {
        logger("Component $component released")
        component = null
    }
}