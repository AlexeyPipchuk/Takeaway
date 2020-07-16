package takeaway.feature_order_registration.presentation

interface OrderRegistrationRouter {

    fun toConfirmation(orderId: String)

    fun backToBasket()

    fun backToStartPoint()

    fun toPrivacyPolicy()
}