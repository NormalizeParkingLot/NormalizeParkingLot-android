package com.kick.npl.ui.app

import androidx.annotation.DrawableRes
import com.kick.npl.R

enum class NPLBottomRoute(
    val route: String,
    val title: String,
    @DrawableRes val iconId: Int,
) {
    Map("map", "지도", R.drawable.ic_map),
    Favorite("favorite", "즐겨찾기", R.drawable.ic_star_filled),
    Provider("provider", "제공자용", R.drawable.ic_setting),
    Setting("setting", "더보기", R.drawable.ic_setting)
}