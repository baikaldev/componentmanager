package studio.inprogress.componentstorage.sample.di.factory

import android.content.Context
import studio.inprogress.componentstorage.componentstorage.core.ComponentStorage
import studio.inprogress.componentstorage.componentstorage.core.factory.IComponentFactory
import studio.inprogress.componentstorage.sample.di.component.CoreComponent
import studio.inprogress.componentstorage.sample.di.component.DaggerCoreComponent
import studio.inprogress.componentstorage.sample.di.module.PreferencesModule

class CoreComponentFactory(private val appContext: Context) : IComponentFactory<CoreComponent> {

    override fun create(componentStorage: ComponentStorage): CoreComponent {
        return DaggerCoreComponent.builder()
            .preferencesModule(PreferencesModule(appContext))
            .build()
    }

    override fun getName(): String {
        return CoreComponent::class.java.simpleName
    }

    override fun isReleasable(): Boolean {
        return false
    }
}