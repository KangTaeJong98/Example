package com.taetae98.oauth.viewmodel

import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    var email: String? = null
    var birth: String? = null
    var name: String? = null
    var gender: String? = null
    var type: String? = null

    companion object {
        const val GOOGLE = "Google"
        const val KAKAO = "Kakao"
        const val NAVER = "Naver"
    }

    fun reset() {
        email = null
        birth = null
        name = null
        gender = null
    }
}