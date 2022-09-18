package com.app.ultraplus.base

import android.util.Log
import com.app.ultraplus.util.await
import com.google.android.gms.tasks.Task
import java.io.IOException



//suspend fun <T : Any> safeExecute(block: suspend () -> Task<T>): T = try {
//    block().await()
//} catch (e: Exception) {
//    Log.d(getTAG(), "safeExecute: $e")
//    Failure(NetworkException("${e.message}"))
//}