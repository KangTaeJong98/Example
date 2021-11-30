package com.taetae98.widget.dto

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import java.io.Serializable

@Xml
data class CovidStatusResponse(
    @Element
    val header: Header = Header(),
    @Element
    val body: Body = Body()
) : Serializable {
    @Xml
    data class Header(
        @PropertyElement(name = "resultCode")
        val code: String = "",
        @PropertyElement(name = "resultMsg")
        val message: String = ""
    ) : Serializable

    @Xml
    data class Body(
        @Element
        val items: Items = Items(),
    ) : Serializable {
        val cityList: List<String>
            get() {
                return items.item.map { it.name }.distinct()
            }

        operator fun get(key: String): Items.Item? {
            return items.item.find {
                it.name == key
            }
        }

        @Xml
        data class Items(
            @Element
            val item: List<Item> = emptyList()
        ) : Serializable {
            @Xml
            data class Item(
                @PropertyElement(name = "gubun")
                val name: String = "",
                @PropertyElement(name = "incDec")
                val positive: Long = 0L,
                @PropertyElement(name = "isolClearCnt")
                val isolation: Long = 0L,
                @PropertyElement(name = "deathCnt")
                val death: Long = 0L,
                @PropertyElement(name = "defCnt")
                val totalPositive: Long = 0L,
                @PropertyElement(name = "isolIngCnt")
                val totalIsolation: Long = 0L,
            ) : Serializable
        }
    }
}