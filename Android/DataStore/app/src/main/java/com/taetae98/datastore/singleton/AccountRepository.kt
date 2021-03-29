package com.taetae98.datastore.singleton

import androidx.datastore.core.DataStore
import com.taetae98.datastore.Account
import com.taetae98.datastore.dto.Information
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor() {
    @Inject
    lateinit var accountDataStore: DataStore<Account>

    val studentId: Flow<String>
        get() {
            return accountDataStore.data.map { it.studentId }
        }

    suspend fun set(information: Information) {
        accountDataStore.updateData {
            it.toBuilder()
                .setDepartment(information.department)
                .setName(information.name)
                .setPhone(information.phone)
                .setStudentId(information.studentId)
                .build()
        }
    }
}