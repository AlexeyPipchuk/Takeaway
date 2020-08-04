package takeaway.feature_cafe.product.presentation

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.feature_cafe.cafe.presentation.cafe
import takeaway.feature_cafe.cafe.presentation.product
import takeaway.shared_basket.domain.usecase.AddToBasketUseCase
import takeaway.shared_basket.domain.usecase.ClearBasketUseCase
import takeaway.shared_basket.domain.usecase.GetBasketCafeIdUseCase

@RunWith(MockitoJUnitRunner::class)
class ProductPresenterTest {

    private val getBasketCafeIdUseCase: GetBasketCafeIdUseCase = mock()
    private val clearBasketUseCase: ClearBasketUseCase = mock()
    private val addToBasketUseCase: AddToBasketUseCase = mock()

    private val view: ProductView = mock()
    private val presenter = ProductPresenter(
        getBasketCafeIdUseCase = getBasketCafeIdUseCase,
        clearBasketUseCase = clearBasketUseCase,
        addToBasketUseCase = addToBasketUseCase,
        product = product,
        cafe = cafe
    )

    @Test
    fun `view attached EXPECT show product info`() {
        presenter.attachView(view)

        verify(view).showProductInfo(product)
    }

    @Test
    fun `on plus button clicked EXPECT inc price`() {
        presenter.attachView(view)
        presenter.onPlusButtonClicked()

        verify(view).incPrice(product.price)
    }

    @Test
    fun `on minus button clicked EXPECT dec price`() {
        presenter.attachView(view)
        presenter.onMinusButtonClicked()

        verify(view).decPrice(product.price)
    }

    @Test
    fun `on exit button clicked EXPECT close product dialog`() {
        presenter.attachView(view)
        presenter.onExitButtonClicked()

        verify(view).closeProductDialog()
    }

    @Test
    fun `to basket button clicked and cafe is cafe in basket EXPECT add product to basket`() {
        val productCount = 2
        whenever(getBasketCafeIdUseCase()).thenReturn(cafe.id)

        presenter.onToBasketButtonClicked(productCount)

        verify(addToBasketUseCase).invoke(product, productCount, cafe)
    }

    @Test
    fun `to basket button clicked and cafe is cafe in basket EXPECT close product dialog`() {
        val productCount = 2
        whenever(getBasketCafeIdUseCase()).thenReturn(cafe.id)
        presenter.attachView(view)

        presenter.onToBasketButtonClicked(productCount)

        verify(view).closeProductDialog()
    }

    @Test
    fun `to basket button clicked and cafe is not cafe in basket EXPECT show clear basket question dialog`() {
        val productCount = 2
        whenever(getBasketCafeIdUseCase()).thenReturn(cafe.copy(id = "432532523").id)
        presenter.attachView(view)

        presenter.onToBasketButtonClicked(productCount)

        verify(view).showClearBasketQuestionDialog()
    }

    @Test
    fun `to basket button clicked and cafe in basket is null EXPECT add product to basket with cafe`() {
        val productCount = 2
        whenever(getBasketCafeIdUseCase()).thenReturn(null)

        presenter.onToBasketButtonClicked(productCount)

        verify(addToBasketUseCase).invoke(product, productCount, cafe)
    }

    @Test
    fun `to basket button clicked and cafe in basket is null EXPECT close product dialog`() {
        val productCount = 2
        whenever(getBasketCafeIdUseCase()).thenReturn(null)
        presenter.attachView(view)

        presenter.onToBasketButtonClicked(productCount)

        verify(view).closeProductDialog()
    }

    @Test
    fun `on approve to clear basket button clicked EXPECT clear basket`() {
        val productCount = 2

        presenter.onApproveToClearBasketButtonClicked(productCount)

        verify(clearBasketUseCase).invoke()
    }

    @Test
    fun `on approve to clear basket button clicked EXPECT add product to basket`() {
        val productCount = 2

        presenter.onApproveToClearBasketButtonClicked(productCount)

        verify(addToBasketUseCase).invoke(product, productCount, cafe)
    }

    @Test
    fun `on approve to clear basket button clicked EXPECT close product dialog`() {
        val productCount = 2
        presenter.attachView(view)

        presenter.onApproveToClearBasketButtonClicked(productCount)

        verify(view).closeProductDialog()
    }
}