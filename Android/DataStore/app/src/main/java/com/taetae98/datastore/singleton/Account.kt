package com.taetae98.datastore.singleton

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Account @Inject constructor() {
    var department: String = ""
    var studentId: String = ""
    var name: String = ""
    var phone: String = ""
}