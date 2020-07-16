package takeaway.feature.cafe.presentation

import base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.terrakok.cicerone.Router
import takeaway.shared_error.ErrorConverter
import takeaway.shared_error.ErrorType
import takeaway.app.navigation.Screen
import takeaway.component_rx_extension.subscribeOver
import takeaway.component_rx_extension.zipWith
import takeaway.feature.cafe.presentation.model.CategoryItem
import takeaway.feature.cafe.ui.CafeView
import takeaway.shared.cafe.domain.usecase.GetProductListUseCase
import takeaway.shared_category.domain.entity.Category
import takeaway.shared.cafe.domain.usecase.GetCategoryListUseCase
import takeaway.shared_cafe.domain.entity.Cafe
import javax.inject.Inject

class CafePresenter @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val getBasketAmountUseCase: takeaway.shared_basket.domain.usecase.GetBasketAmountUseCase,
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val errorConverter: ErrorConverter,
    private val router: Router,
    private val cafe: Cafe

) : BasePresenter<CafeView>() {

    private var categoriesCache: List<CategoryItem>? = null
    private var productsCache: List<domain.entity.Product>? = null

    override fun onViewAttach() {
        super.onViewAttach()

        loadProductsAndCategories()
    }

    private fun loadProductsAndCategories() {
        view?.showProgress()

        getProductListUseCase(cafe.id)
            .zipWith(
                getCategoryListUseCase(
                    useCache = true,
                    cafeCategories = cafe.productCategoryIds
                )
            )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOver(
                onSuccess = { (products, cafeProductCategories) ->
                    showCafe()

                    productsCache = products

                    if (cafeProductCategories.isNotEmpty()) {
                        val categoryItemList = toDefaultCategoryItemList(cafeProductCategories)
                        categoriesCache = categoryItemList
                        view?.setCategories(categoryItemList)

                        val defaultFilteredProductList =
                            getFilteredProductList(categoryItemList.first())

                        defaultFilteredProductList?.let {
                            view?.setProducts(defaultFilteredProductList)
                        } ?: view?.setProducts(products)
                    } else {
                        view?.setProducts(products)
                    }

                    view?.hideProgress()
                },
                onError = { error ->
                    view?.hideProgress()
                    handleError(error)
                }
            )
            .addToDisposable()
    }

    private fun showCafe() {
        view?.showCafeImages(cafe.imgUrls, cafe.logoUrl)
        view?.showCafeInfo(
            cafe.cafeType,
            cafe.name,
            cafe.description,
            cafe.address,
            cafe.workFrom,
            cafe.workTo
        )
        view?.showDeliveryFreeFrom(cafe.deliveryFreeFrom)

        if (cafe.takeawayDiscount > 0) {
            view?.showTakeawayDiscount(cafe.takeawayDiscount)
        }

        if (!cafe.businessFrom.isNullOrEmpty() && !cafe.businessTo.isNullOrEmpty()) {
            view?.showBusinessLunch(cafe.businessFrom.toString(), cafe.businessTo.toString())
        }

        if (cafe.deliveryPrice != 0) {
            view?.showDeliveryPrice(cafe.deliveryPrice)
        }

        if (cafe.minDeliverySum != 0) {
            view?.showMinDeliverySum(cafe.minDeliverySum)
        }
    }

    private fun toDefaultCategoryItemList(categories: List<takeaway.shared_category.domain.entity.Category>): List<CategoryItem> =
        categories.map { CategoryItem(it) }.apply {
            first().selected = true
        }

    fun onRetryClicked() {
        loadProductsAndCategories()
    }

    fun onBackClicked() {
        router.backTo(null)
    }

    fun onProductClicked(selectedProduct: domain.entity.Product) {
        view?.showProductDialog(selectedProduct, cafe)
    }

    fun onCategoryClicked(selectedCategory: CategoryItem) {
        categoriesCache?.apply {
            first { it.selected }.selected = false
            first { it.category.id == selectedCategory.category.id }.selected = true
        }

        updateFilteredProductList(selectedCategory)
    }

    private fun updateFilteredProductList(selectedCategory: CategoryItem) {
        val filteredProductList = getFilteredProductList(selectedCategory)

        filteredProductList?.let {
            view?.updateProducts(it)

            categoriesCache?.let { categoriesList ->
                view?.updateCategories(categoriesList)
            }
        }
    }

    private fun getFilteredProductList(selectedCategory: CategoryItem): List<domain.entity.Product>? =
        productsCache?.filter { it.categoryId == selectedCategory.category.id.toString() }

    fun onBasketClick() {
        router.navigateTo(Screen.BasketScreen)
    }

    private fun handleError(error: Throwable) {
        when (errorConverter.convert(error)) {
            ErrorType.BAD_INTERNET -> view?.showNoInternetDialog()
            ErrorType.SERVICE_UNAVAILABLE -> view?.showServiceUnavailable()
        }
    }

    fun onNegativeButtonClicked() {
        router.newRootScreen(Screen.FeedScreen(noInternet = true))
    }

    fun onScreenUpdated() {
        val actualBasketAmount = getBasketAmountUseCase()
        view?.setBasketAmount(actualBasketAmount)
    }
}