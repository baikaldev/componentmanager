package studio.inprogress.componentstorage.componentstorage.core

import studio.inprogress.componentstorage.componentstorage.core.factory.IComponentFactory
import kotlin.reflect.KClass

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
    fun <T: Any> getOrCreateComponent(componentFactory: IComponentFactory<T>): T {

        val holderKey = componentFactory.getName()

        val targetComponentHolder = holderStorage.getOrElse(holderKey) {
            val componentHolder = ComponentHolder(componentFactory, this, logger)
            holderStorage[holderKey] = componentHolder
            return@getOrElse componentHolder
        }
        return targetComponentHolder.getOrCreate() as T
    }

    fun <T: Any> getOrCreateComponent(kClass: KClass<T>): T {
        return getOrCreateComponent(kClass.java.simpleName)
    }

    fun <T: Any> getOrCreateComponent(clazz: Class<T>): T {
        return getOrCreateComponent(clazz.simpleName)
    }

    private fun <T: Any> getOrCreateComponent(factoryKey: String): T {
        val factory = factoryStorage[factoryKey]
        if (factory == null) {
            throw IllegalStateException("Factory for $factoryKey not registered")
        } else {
            return getOrCreateComponent(factory) as T
        }
    }

    inline fun <reified T: Any> getOrCreateComponent(): T {
        return getOrCreateComponent(T::class)
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

    fun <T: Any> releaseComponent(kClass: KClass<T>) {
        releaseComponent(kClass.java.simpleName)
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
                if (holder.componentFactory is IParentReleasable) {
                    for (parentName in holder.componentFactory.getParentComponentNames()) {
                        releaseComponent(parentName)
                    }
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