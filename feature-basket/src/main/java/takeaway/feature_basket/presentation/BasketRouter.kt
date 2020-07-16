package takeaway.feature_basket.presentation

import domain.entity.OrderSketch

interface BasketRouter {

    fun backToCafe()

    fun toOrderRegistration(orderSketch: OrderSketch)
}