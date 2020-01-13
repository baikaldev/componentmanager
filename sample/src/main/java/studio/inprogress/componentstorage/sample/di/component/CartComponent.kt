package studio.inprogress.componentstorage.sample.di.component

import dagger.Subcomponent
import studio.inprogress.componentstorage.sample.di.module.CartModule

@Subcomponent(
    modules = [
        CartModule::class
    ]
)
interface CartComponent