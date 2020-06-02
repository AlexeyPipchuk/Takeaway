package takeaway.feature.addcafe.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import takeaway.app.BaseFragment
import takeaway.feature.addcafe.presentation.AddCafePresenter
import takeaway.feature.addcafe.presentation.AddCafeView
import javax.inject.Inject

class AddCafeFragment : BaseFragment(R.layout.add_cafe_fragment), AddCafeView {

    companion object {
        fun getInstance(): Fragment = AddCafeFragment()
    }

    @Inject
    lateinit var presenter: AddCafePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}