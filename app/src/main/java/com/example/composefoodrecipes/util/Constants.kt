package com.example.composefoodrecipes.util

import androidx.compose.ui.text.font.Font
import com.example.composefoodrecipes.R

object Constants {
    //https://foodish-api.com/api
    const val BASE_URL = "https://foodish-api.com/"
    const val CALL_ATTRIBUTES = "api"

    //region Font UI
    val lightFont = Font(R.font.fredoka_light)
    val regularFont = Font(R.font.fredoka_regular)
    val mediumFont = Font(R.font.fredoka_medium)
    val semiBoldFont = Font(R.font.fredoka_semibold)
    val boldFont = Font(R.font.fredoka_bold)
    //endregion
}