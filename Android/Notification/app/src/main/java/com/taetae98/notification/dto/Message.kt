package com.taetae98.notification.dto

import java.io.Serializable

data class Message(
    var id: Int = 0,
    var title: String = "",
    var message: String = "",
) : Serializable {
    companion object {
        private var IDENTIFIER = 0
    }

    init {
        id = IDENTIFIER++
    }
}