package takeaway.feature_feed.feed.presentation

import takeaway.feature_feed.feed.presentation.model.CafeItem
import takeaway.shared_cafe.domain.entity.Cafe

private const val cafeName = "cafe"
private const val takeawayDiscount = 100
private const val deliveryFreeFrom = 10
private const val isPopular = false

val cafe = Cafe(
    id = "1",
    name = cafeName,
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
    takeawayDiscount = takeawayDiscount,
    deliveryPrice = 300,
    deliveryFreeFrom = deliveryFreeFrom,
    cafeType = "zbs",
    imgUrls = null,
    cafeCategoryIds = null,
    logoUrl = null,
    isVisible = true,
    productCategoryIds = null,
    isPopular = isPopular,
    isClosed = false
)

val cafeItem = CafeItem(
    cafeName = cafeName,
    takeawayDiscount = takeawayDiscount,
    deliveryFreeFrom = deliveryFreeFrom,
    imageUrl = null,
    logoUrl = null,
    isPopular = isPopular
)