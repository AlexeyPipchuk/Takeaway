package takeaway.feature_cafe.cafe.presentation

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.component_test.TestSchedulerRule
import takeaway.feature_cafe.cafe.domain.usecase.GetCategoryListUseCase
import takeaway.feature_cafe.cafe.domain.usecase.GetProductListUseCase
import takeaway.feature_cafe.cafe.presentation.model.CategoryItem
import takeaway.shared_basket.domain.usecase.GetBasketAmountUseCase
import takeaway.shared_cafe.domain.entity.Cafe
import takeaway.shared_error.ErrorConverter
import takeaway.shared_error.ErrorType

@RunWith(MockitoJUnitRunner::class)
class CafePresenterTest {

    @Rule
    @JvmField
    val testRule = TestSchedulerRule()

    private val view: CafeView = mock()

    private val getProductListUseCase: GetProductListUseCase = mock()
    private val getBasketAmountUseCase: GetBasketAmountUseCase = mock()
    private val getCategoryListUseCase: GetCategoryListUseCase = mock()
    private val errorConverter: ErrorConverter = mock()
    private val router: CafeRouter = mock()

    private val presenter = CafePresenter(
        getProductListUseCase,
        getBasketAmountUseCase,
        getCategoryListUseCase,
        errorConverter,
        router,
        cafe
    )

    @Test
    fun `view attached EXPECT show progress`() {
        whenever(getProductListUseCase(any())).thenReturn(Single.never())
        whenever(
            getCategoryListUseCase(
                useCache = true,
                cafeCategories = cafe.productCategoryIds
            )
        ).thenReturn(Single.never())

        presenter.attachView(view)

        verify(view).showProgress()
    }

    @Test
    fun `view attached EXPECT load product list and category list from cache`() {
        whenever(getProductListUseCase(any())).thenReturn(Single.never())
        whenever(
            getCategoryListUseCase(
                useCache = true,
                cafeCategories = cafe.productCategoryIds
            )
        ).thenReturn(Single.never())

        presenter.attachView(view)

        verify(getProductListUseCase).invoke(cafeId = cafe.id)
        verify(getCategoryListUseCase).invoke(
            useCache = true,
            cafeCategories = cafe.productCategoryIds
        )
    }

    @Test
    fun `product list and category list loaded EXPECT show cafe images`() {
        whenever(getProductListUseCase(any())).thenReturn(Single.just(productList))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.just(categoryList))

        presenter.attachView(view)

        verify(view).showCafeImages(cafe.imgUrls, cafe.logoUrl)
    }

    @Test
    fun `product list and category list loaded EXPECT show cafe info`() {
        whenever(getProductListUseCase(any())).thenReturn(Single.just(productList))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.just(categoryList))

        presenter.attachView(view)

        verify(view).showCafeInfo(
            cafe.cafeType,
            cafe.name,
            cafe.description,
            cafe.address,
            cafe.workFrom,
            cafe.workTo
        )
    }

    @Test
    fun `product list and category list loaded EXPECT show delivery free from`() {
        whenever(getProductListUseCase(any())).thenReturn(Single.just(productList))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.just(categoryList))

        presenter.attachView(view)

        verify(view).showDeliveryFreeFrom(cafe.deliveryFreeFrom)
    }

    @Test
    fun `product list and category list loaded and cafe discount more than 0 EXPECT show takeaway discount`() {
        whenever(getProductListUseCase(any())).thenReturn(Single.just(productList))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.just(categoryList))
        val takeawayDiscount = 100
        val presenter = createPresenter(cafe = cafe.copy(takeawayDiscount = takeawayDiscount))

        presenter.attachView(view)

        verify(view).showTakeawayDiscount(takeawayDiscount)
    }

    @Test
    fun `product list and category list loaded and cafe discount is 0 EXPECT newer show takeaway discount`() {
        whenever(getProductListUseCase(any())).thenReturn(Single.just(productList))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.just(categoryList))
        val takeawayDiscount = 0
        val presenter = createPresenter(cafe = cafe.copy(takeawayDiscount = takeawayDiscount))

        presenter.attachView(view)

        verify(view, never()).showTakeawayDiscount(any())
    }

    @Test
    fun `product list and category list loaded and business from and business to is not empty EXPECT show business lunch`() {
        whenever(getProductListUseCase(any())).thenReturn(Single.just(productList))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.just(categoryList))
        val businessFrom = "10:00"
        val businessTo = "12:00"
        val presenter = createPresenter(cafe = cafe.copy(businessFrom = businessFrom, businessTo = businessTo))

        presenter.attachView(view)

        verify(view).showBusinessLunch(businessFrom, businessTo)
    }

    @Test
    fun `product list and category list loaded and business from or business to is empty EXPECT never show business lunch`() {
        whenever(getProductListUseCase(any())).thenReturn(Single.just(productList))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.just(categoryList))
        val businessFrom = "10:00"
        val businessTo = ""
        val presenter = createPresenter(cafe = cafe.copy(businessFrom = businessFrom, businessTo = businessTo))

        presenter.attachView(view)

        verify(view, never()).showBusinessLunch(any(), any())
    }

    @Test
    fun `product list and category list loaded and delivery price is not 0 EXPECT show delivery price`() {
        whenever(getProductListUseCase(any())).thenReturn(Single.just(productList))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.just(categoryList))
        val deliveryPrice = 100
        val presenter = createPresenter(cafe = cafe.copy(deliveryPrice = deliveryPrice))

        presenter.attachView(view)

        verify(view).showDeliveryPrice(deliveryPrice)
    }

    @Test
    fun `product list and category list loaded and delivery price is 0 EXPECT never show delivery price`() {
        whenever(getProductListUseCase(any())).thenReturn(Single.just(productList))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.just(categoryList))
        val deliveryPrice = 0
        val presenter = createPresenter(cafe = cafe.copy(deliveryPrice = deliveryPrice))

        presenter.attachView(view)

        verify(view, never()).showDeliveryPrice(deliveryPrice)
    }

    @Test
    fun `product list and category list loaded and min delivery sum is not 0 EXPECT show min delivery sum`() {
        whenever(getProductListUseCase(any())).thenReturn(Single.just(productList))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.just(categoryList))
        val minDeliverySum = 100
        val presenter = createPresenter(cafe = cafe.copy(minDeliverySum = minDeliverySum))

        presenter.attachView(view)

        verify(view).showMinDeliverySum(minDeliverySum)
    }

    @Test
    fun `product list and category list loaded and min delivery sum is 0 EXPECT never show min delivery sum`() {
        whenever(getProductListUseCase(any())).thenReturn(Single.just(productList))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.just(categoryList))
        val minDeliverySum = 0
        val presenter = createPresenter(cafe = cafe.copy(minDeliverySum = minDeliverySum))

        presenter.attachView(view)

        verify(view, never()).showMinDeliverySum(minDeliverySum)
    }

    @Test
    fun `product list and category list loaded EXPECT hide progress`() {
        whenever(getProductListUseCase(any())).thenReturn(Single.just(productList))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.just(categoryList))

        presenter.attachView(view)

        verify(view).hideProgress()
    }

    @Test
    fun `product list and category list loaded and categories list is empty EXPECT set products`() {
        whenever(getProductListUseCase(any())).thenReturn(Single.just(productList))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.just(emptyList()))

        presenter.attachView(view)

        verify(view).setProducts(productList)
    }

    @Test
    fun `product list and category list loaded and categories list is not empty EXPECT set categories which first is selected`() {
        val secondCategory = category.copy(id = 434543)
        val categoryItem = CategoryItem(category = category, selected = true)
        val secondCategoryItem = CategoryItem(category = secondCategory, selected = false)
        whenever(getProductListUseCase(any())).thenReturn(Single.just(productList))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.just(listOf(category, secondCategory)))


        presenter.attachView(view)

        verify(view).setCategories(listOf(categoryItem, secondCategoryItem))
    }

    @Test
    fun `product list and category list loaded and categories list is not empty EXPECT set filtered on default category products`() {
        val categoryId = 1
        val secondCategoryId = 2
        val product = product.copy(categoryId = categoryId.toString())
        val secondProduct = product.copy(categoryId = secondCategoryId.toString())
        whenever(getProductListUseCase(any())).thenReturn(Single.just(listOf(product, secondProduct)))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.just(listOf(category.copy(id = categoryId))))

        presenter.attachView(view)

        verify(view).setProducts(listOf(product))
    }

    @Test
    fun `product list and category list failed EXPECT hide progress`() {
        val errorType = ErrorType.BAD_INTERNET
        val error = Throwable("error")
        whenever(getProductListUseCase(any())).thenReturn(Single.error(error))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.error(error))
        whenever(errorConverter.convert(error)).thenReturn(errorType)

        presenter.attachView(view)

        verify(view).hideProgress()
    }

    @Test
    fun `product list and category list failed with no internet error EXPECT show no internet dialog`() {
        val errorType = ErrorType.BAD_INTERNET
        val error = Throwable("error")
        whenever(getProductListUseCase(any())).thenReturn(Single.error(error))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.error(error))
        whenever(errorConverter.convert(error)).thenReturn(errorType)

        presenter.attachView(view)

        verify(view).showNoInternetDialog()
    }

    @Test
    fun `product list and category list failed with service unavailable error EXPECT show service unavailable dialog`() {
        val errorType = ErrorType.SERVICE_UNAVAILABLE
        val error = Throwable("error")
        whenever(getProductListUseCase(any())).thenReturn(Single.error(error))
        whenever(
            getCategoryListUseCase(useCache = true, cafeCategories = cafe.productCategoryIds)
        ).thenReturn(Single.error(error))
        whenever(errorConverter.convert(error)).thenReturn(errorType)

        presenter.attachView(view)

        verify(view).showServiceUnavailable()
    }

    @Test
    fun `on retry clicked EXPECT load product and categories`() {
        whenever(getProductListUseCase(any())).thenReturn(Single.never())
        whenever(
            getCategoryListUseCase(
                useCache = true,
                cafeCategories = cafe.productCategoryIds
            )
        ).thenReturn(Single.never())

        presenter.onRetryClicked()

        verify(getProductListUseCase).invoke(cafeId = cafe.id)
        verify(getCategoryListUseCase).invoke(
            useCache = true,
            cafeCategories = cafe.productCategoryIds
        )
    }

    @Test
    fun `on back clicked EXPECT route back`() {
        presenter.onBackClicked()

        verify(router).back()
    }

    @Test
    fun `on product clicked EXPECT show product dialog with selected product`() {
        whenever(getProductListUseCase(any())).thenReturn(Single.never())
        whenever(
            getCategoryListUseCase(
                useCache = true,
                cafeCategories = cafe.productCategoryIds
            )
        ).thenReturn(Single.never())
        presenter.attachView(view)

        presenter.onProductClicked(product)

        verify(view).showProductDialog(product, cafe)
    }

    @Test
    fun `on category clicked EXPECT update products`() {
        val categoryId = 1
        val secondCategoryId = 2
        val category = category.copy(id = categoryId)
        val secondCategory = category.copy(id = secondCategoryId)
        val secondCategoryItem = CategoryItem(category = secondCategory, selected = true)
        val product = product.copy(categoryId = categoryId.toString())
        val secondProduct = product.copy(categoryId = secondCategoryId.toString())
        whenever(getProductListUseCase(any())).thenReturn(Single.just(listOf(product, secondProduct)))
        whenever(
            getCategoryListUseCase(
                useCache = true,
                cafeCategories = cafe.productCategoryIds
            )
        ).thenReturn(Single.just(listOf(category, secondCategory)))
        presenter.attachView(view)

        presenter.onCategoryClicked(secondCategoryItem)

        verify(view).updateProducts(listOf(secondProduct))
    }

    @Test
    fun `on category clicked EXPECT update categories`() {
        val categoryId = 1
        val secondCategoryId = 2
        val category = category.copy(id = categoryId)
        val secondCategory = category.copy(id = secondCategoryId)
        val categoryItem = CategoryItem(category = category, selected = false)
        val secondCategoryItem = CategoryItem(category = secondCategory, selected = true)
        whenever(getProductListUseCase(any())).thenReturn(Single.just(listOf(product)))
        whenever(
            getCategoryListUseCase(
                useCache = true,
                cafeCategories = cafe.productCategoryIds
            )
        ).thenReturn(Single.just(listOf(category, secondCategory)))
        presenter.attachView(view)

        presenter.onCategoryClicked(secondCategoryItem)

        verify(view).updateCategories(listOf(categoryItem, secondCategoryItem))
    }

    @Test
    fun `on basket click EXPECT route to basket`() {
        presenter.onBasketClick()

        verify(router).toBasket()
    }

    @Test
    fun `on negative button clicked EXPECT route to error`() {
        presenter.onNegativeButtonClicked()

        verify(router).toError()
    }

    @Test
    fun `on screen updated EXPECT set basket amount`() {
        val actualBasketAmount = 1000
        whenever(getProductListUseCase(any())).thenReturn(Single.never())
        whenever(
            getCategoryListUseCase(
                useCache = true,
                cafeCategories = cafe.productCategoryIds
            )
        ).thenReturn(Single.never())
        whenever(getBasketAmountUseCase()).thenReturn(actualBasketAmount)
        presenter.attachView(view)

        presenter.onScreenUpdated()

        verify(view).setBasketAmount(actualBasketAmount)
    }

    private fun createPresenter(cafe: Cafe): CafePresenter =
        CafePresenter(
            getProductListUseCase,
            getBasketAmountUseCase,
            getCategoryListUseCase,
            errorConverter,
            router,
            cafe
        )
}