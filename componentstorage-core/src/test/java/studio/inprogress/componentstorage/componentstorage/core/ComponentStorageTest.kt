package studio.inprogress.componentstorage.componentstorage.core

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ComponentStorageTest {

    private lateinit var componentStorage: ComponentStorage

    @Before
    fun init() {
        componentStorage = ComponentStorage()
        componentStorage.clear()
    }

    /*
     * Тест постоянного компонента
     */
    @Test
    fun testConstantComponent() {
        val componentFactory = ConstComponentFactory(AModule())

        componentStorage.registerComponentFactory(componentFactory)

        val component1 = componentStorage.getOrCreateComponent(AComponent::class.java)

        assertNotNull(component1)
        assertEquals(1, componentStorage.holderStorage.size)
        assertEquals(0, componentStorage.holderStorage[componentFactory.getName()]?.getOwnerCount())

        val component2 = componentStorage.getOrCreateComponent(componentFactory)
        assertNotNull(component2)
        assertEquals(component1, component2)
        assertEquals(1, componentStorage.holderStorage.size)
        assertEquals(0, componentStorage.holderStorage[componentFactory.getName()]?.getOwnerCount())

        componentStorage.releaseComponent(AComponent::class)
        assertEquals(1, componentStorage.holderStorage.size)
        assertEquals(0, componentStorage.holderStorage[componentFactory.getName()]?.getOwnerCount())
    }

    /*
     * Простой тест, где создается и релизится один компонент
     */
    @Test
    fun testOneComponent() {

        val aComponentFactory = AComponentFactory(AModule())

        val component1 = componentStorage.getOrCreateComponent(aComponentFactory)

        assertNotNull(component1)
        assertEquals(1, componentStorage.holderStorage.size)
        assertEquals(
            1,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )

        val component2 = componentStorage.getOrCreateComponent(aComponentFactory)
        assertNotNull(component2)
        assertEquals(component1, component2)
        assertEquals(1, componentStorage.holderStorage.size)
        assertEquals(
            2,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )

        componentStorage.releaseComponent(aComponentFactory)
        assertEquals(1, componentStorage.holderStorage.size)
        assertEquals(
            1,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )

        componentStorage.releaseComponent(aComponentFactory)
        assertNull(componentStorage.holderStorage[aComponentFactory.getName()])
    }

    /*
    * 1. Создаем компонент A
    * 2. Создаем компонент В, который зависит от А
    */
    @Test
    fun testComponentWith1VerticalDependencies() {

        val aComponentFactory = AComponentFactory(AModule())
        val bComponentFactory = BComponentFactory(BModule())

        val aComponent = componentStorage.getOrCreateComponent(aComponentFactory)
        assertNotNull(aComponent)
        assertEquals(
            1,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )

        val bComponent = componentStorage.getOrCreateComponent(bComponentFactory)
        assertNotNull(bComponent)
        assertEquals(
            2,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )
        assertEquals(
            1,
            componentStorage.holderStorage[bComponentFactory.getName()]?.getOwnerCount()
        )

        componentStorage.releaseComponent(bComponentFactory)
        assertNull(componentStorage.holderStorage[bComponentFactory.getName()])
        assertEquals(
            1,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )

        componentStorage.releaseComponent(aComponentFactory)
        assertNull(componentStorage.holderStorage[aComponentFactory.getName()])
    }

    /*
        * 1. Создаем компонент А
        * 2. Создаем компонент В, который зависит от А
        * 3. Создаем компонент С, который зависит от В
        */
    @Test
    fun testComponentWith2VerticalDependencies() {

        val aComponentFactory = AComponentFactory(AModule())
        val bComponentFactory = BComponentFactory(BModule())
        val cComponentFactory = CComponentFactory(CModule())

        val aComponent = componentStorage.getOrCreateComponent(aComponentFactory)
        assertNotNull(aComponent)
        assertEquals(
            1,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )

        val bComponent = componentStorage.getOrCreateComponent(bComponentFactory)
        assertNotNull(bComponent)
        assertEquals(
            2,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )
        assertEquals(
            1,
            componentStorage.holderStorage[bComponentFactory.getName()]?.getOwnerCount()
        )

        val cComponent = componentStorage.getOrCreateComponent(cComponentFactory)
        assertNotNull(cComponent)
        assertEquals(
            2,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )
        assertEquals(
            2,
            componentStorage.holderStorage[bComponentFactory.getName()]?.getOwnerCount()
        )
        assertEquals(
            1,
            componentStorage.holderStorage[cComponentFactory.getName()]?.getOwnerCount()
        )

        componentStorage.releaseComponent(cComponentFactory)
        assertNull(componentStorage.holderStorage[cComponentFactory.getName()])
        assertEquals(
            2,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )
        assertEquals(
            1,
            componentStorage.holderStorage[bComponentFactory.getName()]?.getOwnerCount()
        )

        componentStorage.releaseComponent(bComponentFactory)
        assertNull(componentStorage.holderStorage[bComponentFactory.getName()])
        assertEquals(
            1,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )

        componentStorage.releaseComponent(aComponentFactory)
        assertNull(componentStorage.holderStorage[aComponentFactory.getName()])

        assertEquals(0, componentStorage.holderStorage.size)
    }

    /*
    * 1. Создаем компонент А
    * 2. Создаем компонент В, который зависит от А
    * 3. Создаем компонент СТег1 и СТег2, который зависят от компонента В
    * Данный кейс нужен, когда мы хотим передавать кастомные параметры в модули.
    * */

    @Test
    fun testComponentWith2HorizontalDependencies() {

        val aComponentFactory = AComponentFactory(AModule())
        val bComponentFactory = BComponentFactory(BModule())

        val aComponent = componentStorage.getOrCreateComponent(aComponentFactory)
        assertNotNull(aComponent)
        assertEquals(
            1,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )

        val bComponent = componentStorage.getOrCreateComponent(bComponentFactory)
        assertNotNull(bComponent)
        assertEquals(
            2,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )
        assertEquals(
            1,
            componentStorage.holderStorage[bComponentFactory.getName()]?.getOwnerCount()
        )

        val cComponentFactoryData1 = CComponentFactory(CModule("Data1"))
        val cComponentTag1 = componentStorage.getOrCreateComponent(cComponentFactoryData1)

        assertNotNull(cComponentTag1)
        assertEquals(
            2,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )
        assertEquals(
            2,
            componentStorage.holderStorage[bComponentFactory.getName()]?.getOwnerCount()
        )
        assertEquals("Data1", cComponentTag1.getData())

        val cComponentFactoryData2 = CComponentFactory(CModule("Data2"))
        val cComponentTag2 = componentStorage.getOrCreateComponent(cComponentFactoryData2)

        assertNotNull(cComponentTag2)
        assertEquals(
            2,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )
        assertEquals(
            3,
            componentStorage.holderStorage[bComponentFactory.getName()]?.getOwnerCount()
        )
        assertEquals("Data2", cComponentTag2.getData())

        assertNotEquals(cComponentTag1, cComponentTag2)

        assertEquals(4, componentStorage.holderStorage.size)

        componentStorage.releaseComponent(cComponentFactoryData1)
        assertEquals(3, componentStorage.holderStorage.size)

        componentStorage.releaseComponent(cComponentFactoryData2)
        assertEquals(2, componentStorage.holderStorage.size)

        assertEquals(
            2,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )
        assertEquals(
            1,
            componentStorage.holderStorage[bComponentFactory.getName()]?.getOwnerCount()
        )


        componentStorage.releaseComponent(bComponentFactory.getName())
        componentStorage.releaseComponent(aComponentFactory.getName())

        assertEquals(0, componentStorage.holderStorage.size)
    }

    /*
    * Пробуем создать компонент B без предварительного создания компонента A. Он должен автоматом создаться
    * */

    @Test
    fun createTwoVerticalDependenciesWithoutFirst() {
        val aComponentFactory = AComponentFactory(AModule())
        val bComponentFactory = BComponentFactory(BModule())

        val bComponent = componentStorage.getOrCreateComponent(bComponentFactory)
        assertNotNull(bComponent)

        assertEquals(2, componentStorage.holderStorage.size)
        assertEquals(
            1,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )
        assertEquals(
            1,
            componentStorage.holderStorage[bComponentFactory.getName()]?.getOwnerCount()
        )

        val aComponent = componentStorage.getOrCreateComponent(aComponentFactory)
        assertNotNull(aComponent)
        assertEquals(
            2,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )

        componentStorage.releaseComponent(aComponentFactory)
        assertEquals(
            1,
            componentStorage.holderStorage[aComponentFactory.getName()]?.getOwnerCount()
        )

        componentStorage.releaseComponent(bComponentFactory)
        assertNull(componentStorage.holderStorage[bComponentFactory.getName()])

        assertEquals(0, componentStorage.holderStorage.size)
    }

    @Test
    fun compositeComponentTest() {
        componentStorage.registerComponentFactory(
            AComponentFactory(AModule()),
            DComponentFactory(),
            CompositeComponentFactory()
        )
        val compositeComponent = componentStorage.getOrCreateComponent<CompositeComponent>()
        val aComponentOwnerCount = componentStorage
            .holderStorage[AComponent::class.java.simpleName]
            ?.getOwnerCount()
        assert(aComponentOwnerCount == 1)

        val dComponentOwnerCount = componentStorage
            .holderStorage[DComponent::class.java.simpleName]
            ?.getOwnerCount()
        assert(dComponentOwnerCount == 1)

        componentStorage.releaseComponent<CompositeComponent>()

        assert(componentStorage.holderStorage.size == 0)
    }
}