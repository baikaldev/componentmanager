package studio.inprogress.componentstorage.componentstorage.core.factory

import studio.inprogress.componentstorage.componentstorage.core.ComponentStorage

interface IComponentFactory<T: Any> {

    fun create(componentStorage: ComponentStorage): T

    fun getName(): String

    fun isReleasable(): Boolean
}