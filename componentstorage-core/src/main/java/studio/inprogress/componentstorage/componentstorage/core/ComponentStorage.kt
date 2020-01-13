package studio.inprogress.componentstorage.componentstorage.core

import studio.inprogress.componentstorage.componentstorage.core.factory.IComponentFactory

class ComponentStorage {

    val holderStorage = mutableMapOf<String, ComponentHolder<*>>()
    private var logger: (String) -> Unit = {}
    val factoryStorage = mutableMapOf<String, IComponentFactory<*>>()

    fun registerComponentFactory(vararg componentFactory: IComponentFactory<*>) {
        for (factory in componentFactory) {
            factoryStorage[factory.getName()] = factory
        }
    }

    @Suppress("UNCHECKED_CAST")
    @Synchronized
    fun <T> getOrCreateComponent(componentFactory: IComponentFactory<T>): T {

        val holderKey = componentFactory.getName()

        val targetComponentHolder = holderStorage.getOrElse(holderKey) {
            val componentHolder = ComponentHolder(componentFactory, this, logger)
            holderStorage[holderKey] = componentHolder
            return@getOrElse componentHolder
        }
        return targetComponentHolder.getOrCreate() as T
    }

    inline fun <reified T> getOrCreateComponent(): T {
        val factory = factoryStorage[T::class.java.simpleName]
        if (factory == null) {
            throw IllegalStateException("Factory for ${T::class.java.simpleName} not registered")
        } else {
            return getOrCreateComponent(factory) as T
        }
    }

    inline fun <reified T> releaseComponent() {
        val key = T::class.java.simpleName
        val factory = factoryStorage[key]
        if (factory == null) {
            throw IllegalStateException("Factory for ${T::class.java.simpleName} not registered")
        } else {
            releaseComponent(factory)
        }
    }

    @Synchronized
    fun releaseComponent(componentFactory: IComponentFactory<*>) {
        releaseComponent(componentFactory.getName())
    }

    @Synchronized
    fun releaseComponent(holderKey: String) {
        val holder = holderStorage[holderKey]
        if (holder != null) {
            holder.release()
            if (holder.isReleased()) {
                if (holder.componentFactory is ParentReleasable) {
                    releaseComponent(holder.componentFactory.getParentComponentName())
                }
                holderStorage.remove(holderKey)
            }
        }
    }

    @Synchronized
    fun clear() {
        holderStorage.clear()
        factoryStorage.clear()
    }

    fun withLogger(onLogMessage: (String) -> Unit): ComponentStorage {
        logger = onLogMessage
        return this
    }
}