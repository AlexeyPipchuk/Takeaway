package takeaway.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment(
    @LayoutRes private val layoutRes: Int
) : Fragment(), TakeawayView {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes, container, false)

    protected fun requestFocusOnFirstError(formLayout: ViewGroup): Boolean {
        formLayout.forEach { child ->
            if (child is TextInputLayout && child.error != null && child.editText?.isFocusable == true) {
                child.editText?.requestFocus()
                return false
            }

            formLayout.requestFocus()
        }
        return true
    }
}