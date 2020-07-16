package takeaway.feature_info.ui

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.fragment.app.Fragment
import base.BaseFragment
import kotlinx.android.synthetic.main.info_fragment.*
import takeaway.component_ui.extensions.fromHtml
import takeaway.feature_info.BuildConfig
import takeaway.feature_info.R
import takeaway.feature_info.presentation.InfoPresenter
import takeaway.feature_info.presentation.InfoView
import javax.inject.Inject

class InfoFragment : BaseFragment(R.layout.info_fragment), InfoView {

    companion object {
        fun getInstance(): Fragment = InfoFragment()
    }

    @Inject
    lateinit var presenter: InfoPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initListeners()
        initToolbar()
        initTextViews()
    }

    private fun initListeners() {
        privacyPolicyLink.setOnClickListener {
            presenter.onPrivacyPolicyClicked()
        }
    }

    private fun initToolbar() {
        toolbar.setBackButtonListener {
            presenter.onBackClicked()
        }
    }

    private fun initTextViews() {
        appVersionText.text = getString(R.string.info_version_name, BuildConfig.VERSION_NAME)
        privacyPolicyLink.text = getString(R.string.info_privacy_policy_link_text).fromHtml()

        takeawayInst.text = getString(R.string.info_takeaway_instagram).fromHtml()
        takeawayInst.movementMethod = LinkMovementMethod.getInstance()

        statistInst.text = getString(R.string.info_statist_instagram).fromHtml()
        statistInst.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}