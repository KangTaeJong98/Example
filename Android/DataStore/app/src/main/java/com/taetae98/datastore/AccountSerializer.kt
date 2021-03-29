package com.taetae98.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream

object AccountSerializer : Serializer<Account> {
    override val defaultValue: Account
        get() = Account.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Account {
        return try {
            Account.parseFrom(input)
        } catch (e: Exception) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: Account, output: OutputStream) {
        t.writeTo(output)
    }
}