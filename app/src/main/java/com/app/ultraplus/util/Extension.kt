package com.app.ultraplus.util

import androidx.annotation.NonNull
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resumeWithException

suspend fun <T> Task<T>.await(): T {
    if (isComplete) {
        val e = exception
        return if (e == null) {
            if (isCanceled) {
                throw CancellationException(
                    "Task $this was cancelled normally.")
            } else {
                result
            }
        } else {
            throw e
        }
    }

    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            val e = exception
            if (e == null) {
                if (isCanceled) cont.cancel() else cont.resume(result, onCancellation = {
                    cont.cancel()
                })
            } else {
                cont.resumeWithException(e)
            }
        }
    }
}

fun Exception.messageOrDefault() = this.message ?: Constant.UNKNOWN_ERROR_TEXT

fun Date.inDisplayFormat(): String = SimpleDateFormat("dd MMMM hh:mm a").format(this)