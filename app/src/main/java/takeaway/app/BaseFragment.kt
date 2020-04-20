package takeaway.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment(
    @LayoutRes private val layoutRes: Int
) : Fragment(), TakeawayView {

    protected val toolbar: Toolbar?
        get() = view?.findViewById(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes, container, false)

    protected fun initToolbar(
        @StringRes titleId: Int = 0,
        @MenuRes menuId: Int = 0,
        toolbarNavigationIcon: Int? = R.drawable.ic_back,
        action: () -> Unit = {}
    ) {
        toolbar?.apply {

            if (titleId != 0) {
                title = getString(titleId)
            }

            if (menuId != 0) {
                inflateMenu(menuId)
            }

            toolbarNavigationIcon?.let {
                setNavigationIcon(toolbarNavigationIcon)
            }

            action()
        }
    }
}