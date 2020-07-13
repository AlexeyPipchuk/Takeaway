package takeaway.feature.feed.promo.ui

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import com.example.takeaway.R
import base.BaseDialogFragment
import kotlinx.android.synthetic.main.promo_dialog_fragment.*
import takeaway.component_ui.extensions.fromHtml
import takeaway.feature.feed.promo.presentation.PromoPresenter
import takeaway.feature.feed.promo.presentation.PromoView
import javax.inject.Inject

class PromoDialogFragment : BaseDialogFragment(R.layout.promo_dialog_fragment), PromoView {

    @Inject
    lateinit var presenter: PromoPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initListeners()
    }

    private fun initListeners() {
        exitButton.setOnClickListener { presenter.onExitButtonClicked() }
    }

    override fun showMotivationText() {
        motivationText.text = getString(R.string.motivation_text).fromHtml()
        motivationText.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun closeDialog() {
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}