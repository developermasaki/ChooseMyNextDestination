package com.example.choosemynextdestination.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.choosemynextdestination.R

@Immutable
data class Destination (
    val dayInt: Int,
    @StringRes val nameRes: Int,
    @DrawableRes val imageRes: Int,
    @StringRes val areaRes: Int,
    @StringRes val explanationRes: Int,
    @StringRes val urlRes: Int?
)

@Stable
val destinations = listOf(
    Destination(1, R.string.name1, R.drawable.dsc1, R.string.area1 ,R.string.explanation1, R.string.uri1),
    Destination(2, R.string.name2, R.drawable.dsc2, R.string.area2, R.string.explanation2, R.string.url2),
    Destination(3, R.string.name3, R.drawable.dsc3, R.string.area3, R.string.explanation3, R.string.url3),
    Destination(4, R.string.name4, R.drawable.dsc4, R.string.area4, R.string.explanation4, R.string.url4),
    Destination(5, R.string.name5, R.drawable.dsc5, R.string.area5, R.string.explanation5, R.string.url5),
    Destination(6, R.string.name6, R.drawable.dsc6, R.string.area6, R.string.explanation6, R.string.url6),
    Destination(7, R.string.name7, R.drawable.dsc7, R.string.area7, R.string.explanation7, R.string.url7),
    Destination(8, R.string.name8, R.drawable.dsc8, R.string.area8, R.string.explanation8, R.string.url8),
    Destination(9, R.string.name9, R.drawable.dsc9, R.string.area9, R.string.explanation9, R.string.url9),
    Destination(10, R.string.name10, R.drawable.dsc10, R.string.area10, R.string.explanation10, R.string.url10),
    Destination(11, R.string.name11, R.drawable.dsc11, R.string.area11, R.string.explanation11, R.string.url11),
    Destination(12, R.string.name12, R.drawable.dsc12, R.string.area12, R.string.explanation12, R.string.url12),
    Destination(13, R.string.name13, R.drawable.dsc13, R.string.area13, R.string.explanation13, R.string.url13),
    Destination(14, R.string.name14, R.drawable.dsc14, R.string.area14, R.string.explanation14, R.string.url14),
    Destination(15, R.string.name15, R.drawable.dsc15, R.string.area15, R.string.explanation15, R.string.url15),
    Destination(16, R.string.name16, R.drawable.dsc16, R.string.area16, R.string.explanation16, R.string.url16),
    Destination(17, R.string.name17, R.drawable.dsc17, R.string.area17, R.string.explanation17, R.string.url17),
    Destination(18, R.string.name18, R.drawable.dsc18, R.string.area18, R.string.explanation18, R.string.url18),
    Destination(19, R.string.name19, R.drawable.dsc19, R.string.area19, R.string.explanation19, R.string.url19),
    Destination(20, R.string.name20, R.drawable.dsc20, R.string.area20, R.string.explanation20, R.string.url20),
    Destination(21, R.string.name21, R.drawable.dsc21, R.string.area21, R.string.explanation21, R.string.url21),
    Destination(22, R.string.name22, R.drawable.dsc22, R.string.area22, R.string.explanation22, R.string.url22),
    Destination(23, R.string.name23, R.drawable.dsc23, R.string.area23, R.string.explanation23, R.string.url23),
    Destination(24, R.string.name24, R.drawable.dsc24, R.string.area24, R.string.explanation24, R.string.url24),
    Destination(25, R.string.name25, R.drawable.dsc25, R.string.area25, R.string.explanation25, R.string.url25),
    Destination(26, R.string.name26, R.drawable.dsc26, R.string.area26, R.string.explanation26, R.string.url26),
    Destination(27, R.string.name27, R.drawable.dsc27, R.string.area27, R.string.explanation27, R.string.url27),
    Destination(28, R.string.name28, R.drawable.dsc28, R.string.area28, R.string.explanation28, R.string.url28),
    Destination(29, R.string.name29, R.drawable.dsc29, R.string.area29, R.string.explanation29, R.string.url29),
    Destination(30, R.string.name30, R.drawable.dsc30, R.string.area30, R.string.explanation30, R.string.url30)
)
