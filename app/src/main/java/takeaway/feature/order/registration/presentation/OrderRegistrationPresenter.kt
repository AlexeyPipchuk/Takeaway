package takeaway.feature.order.registration.presentation

import ru.terrakok.cicerone.Router
import takeaway.app.BasePresenter
import takeaway.app.navigation.Screen
import takeaway.shared.order.registration.domain.entity.OrderSketch
import javax.inject.Inject

class OrderRegistrationPresenter @Inject constructor(
    private val orderSketch: OrderSketch,
    private val router: Router
) : BasePresenter<OrderRegistrationView>() {

    override fun onViewAttach() {
        super.onViewAttach()

        view?.showOrderCafeInfo(orderSketch.cafe)
        view?.showBasketAmount(orderSketch.basketAmountWithoutAll)
        view?.setPrivacyPolicyText()

        setUpSubtitles()

        setUpTakeawayAndDeliveryCalculatedValues()

        showOrderAmountWithTakeaway()
    }

    private fun setUpSubtitles() {
        if (orderSketch.cafe.takeawayDiscount != 0) {
            view?.showTakeawayRadioButtonSubtitle(orderSketch.cafe.takeawayDiscount)
        }

        when {
            orderSketch.deliveryCostCalculated == 0 && orderSketch.deliveryAllowed -> Unit
            orderSketch.deliveryAllowed -> view?.showDeliveryValueToFreeDescription(orderSketch.valueToFreeDelivery)
            else -> view?.showDeliveryMinSumDescription(orderSketch.cafe.minDeliverySum)
        }
    }

    private fun setUpTakeawayAndDeliveryCalculatedValues() {
        view?.setTakeawayDiscountResult(
            orderSketch.cafe.takeawayDiscount,
            orderSketch.takeawayDiscountCalculated
        )

        view?.setDeliveryCostResult(
            orderSketch.deliveryCostCalculated
        )
    }

    private fun showOrderAmountWithTakeaway() {
        view?.showOrderAmountWithTakeaway(orderSketch.orderWithTakeawayAmount)
        if (orderSketch.cafe.takeawayDiscount != 0) {
            view?.showTakeawayDiscountResult()
        }
        view?.hideDeliveryCostResult()
        view?.enableResultAndButton()
    }

    private fun showOrderAmountWithDelivery() {
        if (orderSketch.deliveryAllowed) {
            view?.showOrderAmountWithDelivery(orderSketch.orderWithDeliveryAmount)
            view?.showDeliveryCostResult()
            view?.hideTakeawayDiscountResult()
        } else {
            view?.hideTakeawayDiscountResult()
            view?.hideDeliveryCostResult()
            view?.disableResultAndButton()
        }
    }

    fun onBackToBasketButtonClicked() {
        router.exit()
    }

    fun onTakeawayRadioButtonClicked() {
        showOrderAmountWithTakeaway()
    }

    fun onDeliveryRadioButtonClicked() {
        showOrderAmountWithDelivery()
    }

    fun onCreateOrderButtonClicked() {
        //TODO(Формирование ордера)
    }

    fun onPrivacyPolicyClicked() {
        router.navigateTo(Screen.PrivacyPolicyScreen)
    }
}