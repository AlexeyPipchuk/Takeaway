package takeaway.component_ui.extensions

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.annotation.StringRes
import androidx.core.content.getSystemService
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import takeaway.component_ui.R

fun Fragment.hideKeyboard() =
    requireActivity().window.decorView.hideKeyboard()

private fun View.hideKeyboard() {
    context?.let { context ->
        val inputManager = context.getSystemService<InputMethodManager>()
        inputManager?.hideSoftInputFromWindow(windowToken, 0)
        inputManager?.hideSoftInputFromWindow(applicationWindowToken, 0)
    } ?: return
}

fun MaterialButton.enable() {
    isEnabled = true
    background.setTint(androidx.core.content.ContextCompat.getColor(context, R.color.colorAccent))
}

fun MaterialButton.disable() {
    isEnabled = false
    background.setTint(
        androidx.core.content.ContextCompat.getColor(
            context,
            R.color.disabled_button_background
        )
    )
}

fun TextInputLayout.invalidateError() {
    error = null
    isErrorEnabled = false
}

fun Fragment.makeSnackbar(hostView: View? = view, @StringRes textRes: Int): Snackbar? =
    hostView?.let { Snackbar.make(it, textRes, BaseTransientBottomBar.LENGTH_LONG) }
        ?.apply(::decorateSnackBar)

private fun Fragment.decorateSnackBar(snackBar: Snackbar) {
    with(snackBar) {
        ViewCompat.setElevation(view, resources.getDimension(R.dimen.spacing_2))
    }
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

fun String.fromHtml(): Spanned =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }