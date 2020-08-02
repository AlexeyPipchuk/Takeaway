package takeaway.feature_splash

import takeaway.shared_cafe.domain.entity.Cafe
import takeaway.shared_category.domain.entity.Category

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

val category = Category(
    id = 324,
    title = "category"
)