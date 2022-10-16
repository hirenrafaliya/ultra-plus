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
                    "Task $this was cancelled normally."
                )
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

fun Date.inShortDisplayFormat(): String = SimpleDateFormat("dd MMM").format(this)

fun Date.inFormat(format: String): String = SimpleDateFormat(format).format(this)

fun Date.startDayTime(): Date {
    val calender = Calendar.getInstance()
    calender.time = this
    calender[Calendar.HOUR_OF_DAY] = 0
    calender[Calendar.MINUTE] = 0
    calender[Calendar.SECOND] = 0

    return calender.time
}

fun Date.endDayTime(): Date {
    val calender = Calendar.getInstance()
    calender.time = this
    calender[Calendar.HOUR_OF_DAY] = 24
    calender[Calendar.MINUTE] = 59
    calender[Calendar.SECOND] = 59

    return calender.time
}