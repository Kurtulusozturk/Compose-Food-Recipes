package com.example.composefoodrecipes.model

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
@Entity
data class FoodModel(
    @PrimaryKey(autoGenerate = true)
    var foodId: Int = 0,

    @ColumnInfo(name = "isActive")
    var isActive: Boolean,

    @ColumnInfo(name = "foodName")
    var foodName: String,

    @ColumnInfo(name = "foodImageUrl")
    var foodImageUrl: String,

    @ColumnInfo(name = "recipe")
    var recipe: String
)

/*
Nedir bu NavType?
NavType, Navigation component'imizde farklı veri türlerini argument olarak iletmemizi sağlayan bir yapıdır.
Örneğin, IntType ile integer değerleri, StringType ile string değerleri iletebiliriz. Ancak, FoodModel gibi özel bir veri türümüz olduğunda,
bunu iletmek için özel bir NavType tanımlamamız gerekir.
 */

object FoodModelNavType {
    val foodType = object : NavType<FoodModel?>(true) {
        /*
        get, parseValue, put, serializeAsValue: Bu fonksiyonlar, FoodModel nesnesinin Bundle'a nasıl kaydedileceğini,
        nasıl okunacağını ve nasıl string formatına dönüştürüleceğini tanımlar. Json sınıfı kullanılarak nesne JSON formatına dönüştürülür ve iletilir.
        Uri.encode ve Uri.decode fonksiyonları ise URL güvenliği için kullanılır.
         */

        override fun get(bundle: Bundle, key: String): FoodModel? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): FoodModel? {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: FoodModel?) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: FoodModel?): String {
            return Uri.encode(Json.encodeToString(value))
        }

    }
}