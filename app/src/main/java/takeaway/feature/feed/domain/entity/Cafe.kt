package takeaway.feature.feed.domain.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Cafe(
    val id: String,
    val name: String,
    @SerializedName("work_from") val workFrom: String,
    @SerializedName("work_to") val workTo: String,
    @SerializedName("business_from") val businessFrom: String,
    @SerializedName("business_to") val businessTo: String,
    @SerializedName("min_delivery_sum") val minDeliverySum: Int,
    val address: String,
    @SerializedName("menu_link") val menuLink: String,
    val description: String,
    val phone: String,
    @SerializedName("delivery_discount") val deliveryDiscount: Int,
    @SerializedName("delivery_price") val deliveryPrice: Int,
    @SerializedName("delivery_free_from") val deliveryFreeFrom: Int,
    @SerializedName("cafe_type") val cafeType: String,
    @SerializedName("img_urls") val imgUrls: List<String>?,
    @SerializedName("cafe_category_ids") val cafeCategoryIds: List<String>?,
    @SerializedName("logo_url") val logoUrl: String?,
    @SerializedName("is_visible") val isVisible: Boolean,
    @SerializedName("product_category_ids") val productCategoryIds: List<Int>?,
    @SerializedName("is_popular") val isPopular: Boolean,
    @SerializedName("is_closed") val isClosed: Boolean
) : Serializable