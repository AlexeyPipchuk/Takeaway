package takeaway.feature_order_registration

import takeaway.shared_cafe.data.model.CafeListResponse
import takeaway.shared_cafe.domain.entity.Cafe

val cafe = Cafe(
    id = "1",
    name = "cafe",
    slug = null,
    workFrom = "10:00",
    workTo = "22:00",
    businessFrom = null,
    businessTo = null,
    minDeliverySum = 100,
    address = "Tomsk",
    menuLink = "url",
    description = "good",
    phone = "+7 999 99-99",
    takeawayDiscount = 100,
    deliveryPrice = 300,
    deliveryFreeFrom = 50,
    cafeType = "zbs",
    imgUrls = null,
    cafeCategoryIds = null,
    logoUrl = null,
    isVisible = true,
    productCategoryIds = null,
    isPopular = false,
    isClosed = false
)

val cafeListResponse = CafeListResponse(
    listOf(cafe), listOf(cafe.copy(isPopular = true))
)