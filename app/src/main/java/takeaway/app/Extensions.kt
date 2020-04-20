package takeaway.app

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment

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