package takeaway.component_rx_extension

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction

fun <T, U> Single<T>.zipWith(other: SingleSource<U>): Single<Pair<T, U>> =
    zipWith(other, BiFunction { t, u -> Pair(t, u) })

fun <T : Any> Single<T>.subscribeOver(
    onError: (Throwable) -> Unit = {},
    onSuccess: (T) -> Unit = {}
): Disposable = subscribe(onSuccess, onError)