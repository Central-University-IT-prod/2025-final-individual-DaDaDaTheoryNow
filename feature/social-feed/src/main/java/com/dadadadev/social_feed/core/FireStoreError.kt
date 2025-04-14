package com.dadadadev.social_feed.core

import com.dadadadev.result.ResponseError

sealed class FireStoreError : ResponseError {
    data class CustomError(val message: String?) : FireStoreError()
    data class PermissionDenied(val details: String) : FireStoreError()
}