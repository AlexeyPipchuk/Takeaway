package takeaway.feature_basket

import domain.entity.Product
import takeaway.feature_basket.presentation.model.BasketItem
import takeaway.shared_basket.domain.entity.Basket
import takeaway.shared_cafe.domain.entity.Cafe

const val takeawayDiscount = 100
const val minDeliverySum = 100
const val deliveryPrice = 300

val cafe = Cafe(
    id = "1",
    name = "cafe",
    slug = null,
    workFrom = "10:00",
    workTo = "22:00",
    businessFrom = null,
    businessTo = null,
    minDeliverySum = minDeliverySum,
    address = "Tomsk",
    menuLink = "url",
    description = "good",
    phone = "+7 999 99-99",
    takeawayDiscount = takeawayDiscount,
    deliveryPrice = deliveryPrice,
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

const val productPrice = 200
const val productName = "product"
const val productId = "12"

val product = Product(
    id = productId,
    title = productName,
    cafeId = "23",
    description = "good",
    price = productPrice,
    weight = "10",
    weightType = "kg",
    isVisible = true,
    imgUrl = null,
    categoryId = "123",
    isPopular = false
)

val productItem = BasketItem(
    productId = productId,
    productName = productName,
    amount = productPrice,
    count = 1
)

val productMap = mapOf(product to 1)

val emptyBasket = Basket(null, null)
val basket = Basket(cafe, productMap)