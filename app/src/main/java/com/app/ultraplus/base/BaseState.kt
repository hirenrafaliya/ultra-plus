package com.app.ultraplus.base

import android.util.Log
import com.app.ultraplus.util.messageOrDefault
import com.google.firebase.messaging.Constants


//suspend fun <T : Any> safeExecute(block: suspend () -> Task<T>): T = try {
//    block().await()
//} catch (e: Exception) {
//    Log.d(getTAG(), "safeExecute: $e")
//    Failure(NetworkException("${e.message}"))
//}

suspend fun safeExecute(onFailure: (String) -> Unit, block: suspend () -> Unit) = try {
    block()
} catch (e: Exception) {
    Log.d(Constants.TAG, "safeExecute: ${e.message}")
    onFailure.invoke(e.messageOrDefault(    ))
}