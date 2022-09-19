package com.app.ultraplus.base

import com.app.ultraplus.util.messageOrDefault


//suspend fun <T : Any> safeExecute(block: suspend () -> Task<T>): T = try {
//    block().await()
//} catch (e: Exception) {
//    Log.d(getTAG(), "safeExecute: $e")
//    Failure(NetworkException("${e.message}"))
//}

suspend fun safeExecute(onFailure: (String) -> Unit, block: suspend () -> Unit) = try {
    block()
} catch (e: Exception) {
    onFailure.invoke(e.messageOrDefault())
}