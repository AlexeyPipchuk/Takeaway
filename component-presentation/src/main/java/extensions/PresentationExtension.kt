package extensions

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

val Fragment.args: Bundle
    get() = arguments
        ?: throw IllegalArgumentException("Fragment has no arguments")

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