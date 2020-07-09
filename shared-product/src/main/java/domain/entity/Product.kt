package domain.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(
    val id: String,
    val title: String,
    val description: String,
    @SerializedName("cafe_id") val cafeId: String,
    val price: Int,
    val weight: String,
    @SerializedName("weight_type") val weightType: String,
    @SerializedName("is_visible") val isVisible: Boolean,
    @SerializedName("img_url") val imgUrl: String?,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("is_popular") val isPopular: Boolean
) : Serializable