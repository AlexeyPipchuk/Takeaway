package takeaway.shared.privacy.policy.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import kotlinx.android.synthetic.main.privacy_policy_appbar.view.*
import kotlinx.android.synthetic.main.privacy_policy_fragment.*
import takeaway.app.BaseFragment
import takeaway.shared.privacy.policy.presentation.PrivacyPolicyPresenter
import takeaway.shared.privacy.policy.presentation.PrivacyPolicyView
import javax.inject.Inject

class PrivacyPolicyFragment : BaseFragment(R.layout.privacy_policy_fragment), PrivacyPolicyView {

    companion object {
        fun getInstance(): Fragment = PrivacyPolicyFragment()
    }

    @Inject
    lateinit var presenter: PrivacyPolicyPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initListeners()
    }

    private fun initListeners() {
        privacyPolicyAppbar.backButton.setOnClickListener {
            presenter.onBackClicked()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        presenter.detachView()
    }
}