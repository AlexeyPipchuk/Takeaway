package takeaway.feature.cafe.presentation

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.terrakok.cicerone.Router
import takeaway.app.BasePresenter
import takeaway.app.navigation.Screen
import takeaway.app.subscribeOver
import takeaway.app.zipWith
import takeaway.feature.cafe.presentation.model.CategoryItem
import takeaway.feature.cafe.ui.CafeView
import takeaway.feature.feed.domain.entity.Cafe
import takeaway.shared.basket.domian.usecase.GetBasketAmountUseCase
import takeaway.shared.cafe.domain.entity.Product
import takeaway.shared.cafe.domain.usecase.GetProductListUseCase
import takeaway.shared.category.domain.entity.Category
import takeaway.shared.category.domain.usecase.GetCategoryListUseCase
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class CafePresenter @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val cafe: Cafe,
    private val router: Router,
    private val getBasketAmountUseCase: GetBasketAmountUseCase,
    private val getCategoryListUseCase: GetCategoryListUseCase
) : BasePresenter<CafeView>() {

    private var categoriesCache: List<CategoryItem>? = null
    private var productsCache: List<Product>? = null

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
                    view?.showCafeInfo(cafe)
                    productsCache = products

                    if (!cafe.productCategoryIds.isNullOrEmpty()) {
                        // TODO(Выглядит, как костыль, этот ифчик -> в юзкейс null уходит
                        //  и для загрузки всего, и в случае отсутствия категорий, мб сделать 2 юзкейза)
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

    private fun toDefaultCategoryItemList(categories: List<Category>): List<CategoryItem> =
        categories.map { CategoryItem(it) }.apply {
            first().selected = true
        }

    fun onRetryClicked() {
        loadProductsAndCategories()
    }

    fun onBackClicked() {
        router.backTo(null)
    }

    fun onProductClicked(selectedProduct: Product) {
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

    private fun getFilteredProductList(selectedCategory: CategoryItem): List<Product>? =
        productsCache?.filter { it.categoryId == selectedCategory.category.id.toString() }

    fun onBasketClick() {
        router.navigateTo(Screen.BasketScreen)
    }

    private fun handleError(error: Throwable) {
        //TODO(Сделать ErrorConverter)
        if (error is UnknownHostException || error is SocketTimeoutException) {
            view?.showNoInternetDialog()
        } else {
            view?.showServiceUnavailable()
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