package takeaway.app

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import java.text.SimpleDateFormat
import java.util.*

val Fragment.args: Bundle
    get() = arguments
        ?: throw IllegalArgumentException("Fragment has no arguments")

fun Fragment.hideKeyboard() =
    requireActivity().window.decorView.hideKeyboard()

fun View.hideKeyboard() {
    context?.let { context ->
        val inputManager = context.getSystemService<InputMethodManager>()
        inputManager?.hideSoftInputFromWindow(windowToken, 0)
        inputManager?.hideSoftInputFromWindow(applicationWindowToken, 0)
    } ?: return
}

fun String.fromHtml(): Spanned =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }

fun Fragment.showServiceUnavailableDialog(positiveResult: () -> Unit, negativeResult: () -> Unit) {
    MaterialAlertDialogBuilder(context, R.style.AlertDialog)
        .setTitle(R.string.service_unavailable_dialog_title)
        .setMessage(R.string.service_unavailable_dialog_message)
        .setPositiveButton(R.string.repeat_dialog_button_text) { _, _ ->
            positiveResult()
        }
        .setNegativeButton(R.string.cancel_dialog_button_text) { _, _ ->
            negativeResult()
        }
        .setCancelable(false)
        .show()
}

fun Fragment.showNoInternetDialog(positiveResult: () -> Unit, negativeResult: () -> Unit) {
    MaterialAlertDialogBuilder(context, R.style.AlertDialog)
        .setTitle(R.string.no_internet_dialog_title)
        .setMessage(R.string.no_internet_dialog_message)
        .setPositiveButton(R.string.ok_dialog_button_text) { _, _ ->
            positiveResult()
        }
        .setNegativeButton(R.string.cancel_dialog_button_text) { _, _ ->
            negativeResult()
        }
        .setCancelable(false)
        .show()
}

fun Fragment.showSeveralCafeBasketWarningDialog(positiveResult: () -> Unit) {
    MaterialAlertDialogBuilder(context, R.style.AlertDialog)
        .setTitle(getString(R.string.several_cafe_basket_warning_title))
        .setMessage(getString(R.string.several_cafe_basket_warning_message))
        .setPositiveButton(getString(R.string.several_cafe_basket_warning_clear_approve)) { _, _ ->
            positiveResult()
        }
        .setNegativeButton(getString(R.string.several_cafe_basket_warning_cancel_clear)) { _, _ ->
            Unit
        }
        .setCancelable(false)
        .show()
}

fun <T, U> Single<T>.zipWith(other: SingleSource<U>): Single<Pair<T, U>> =
    zipWith(other, BiFunction { t, u -> Pair(t, u) })

fun <T : Any> Single<T>.subscribeOver(
    onError: (Throwable) -> Unit = {},
    onSuccess: (T) -> Unit = {}
): Disposable = subscribe(onSuccess, onError)

fun MaterialButton.enable() {
    isEnabled = true
    background.setTint(ContextCompat.getColor(context, R.color.colorAccent))
}

fun MaterialButton.disable() {
    isEnabled = false
    background.setTint(
        ContextCompat.getColor(
            context,
            R.color.disabled_button_background
        )
    )
}

fun TextInputLayout.invalidateError() {
    error = null
    isErrorEnabled = false
}

fun View.showPopup(
    itemList: List<String>,
    itemClickListener: (item: String) -> Unit
) {
    val popupMenu = PopupMenu(context, this)

    itemList.forEach { item ->
        popupMenu.menu
            .add(item)
            .setOnMenuItemClickListener {
                itemClickListener(it.toString())
                false
            }
    }

    popupMenu.show()
}

//TODO (Подумать, как это использовать правильно, сейчас не правильно = вызывает презентер)
const val TIME_PATTERN = "HH:mm"
const val interval = 900000L // 15 мин

fun String.getIntervalListBetween(workTo: String): List<String> {

    fun getHours(time: String): Int =
        time.substring(0, 2).toInt()

    fun getMinutes(time: String): Int =
        time.substring(3, 5).toInt()

    val fromDateCalendar = Calendar.getInstance()
    fromDateCalendar.set(Calendar.HOUR_OF_DAY, getHours(this))
    fromDateCalendar.set(Calendar.MINUTE, getMinutes(this))
    fromDateCalendar.set(Calendar.SECOND, 0)
    val fromMillis = fromDateCalendar.time.time

    val workToCalendar = Calendar.getInstance()
    workToCalendar.set(Calendar.HOUR_OF_DAY, getHours(workTo))
    workToCalendar.set(Calendar.MINUTE, getMinutes(workTo))
    workToCalendar.set(Calendar.SECOND, 0)
    val toMillis = workToCalendar.time.time

    val timeFormatter = SimpleDateFormat(TIME_PATTERN, Locale.getDefault())
    val currentDate = Date()

    var start = currentDate.time - fromMillis
    start = if (start >= 0) currentDate.time
    else fromMillis

    val workTimeTodayMillis = toMillis - start
    return if (workTimeTodayMillis <= 0 || workTimeTodayMillis < interval) emptyList()
    else {
        val timeList = mutableListOf<String>()
        val intervalCounts = workTimeTodayMillis / interval - 1
        for (i in 1..intervalCounts) {
            val item = toMillis - interval * i
            timeList.add(timeFormatter.format(Date(item)))
        }
        timeList
    }
}

fun Fragment.addBackPressedListener(
    enabledCallback: Boolean = true,
    action: OnBackPressedCallback.() -> Unit
): OnBackPressedCallback {
    val callback: OnBackPressedCallback = object : OnBackPressedCallback(enabledCallback) {
        override fun handleOnBackPressed() {
            action.invoke(this)
        }
    }
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    return callback
}