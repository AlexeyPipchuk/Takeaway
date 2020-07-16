package takeaway.feature_cafe.severalcafe.ui

import android.os.Bundle
import android.view.View
import base.BaseDialogFragment
import kotlinx.android.synthetic.main.several_cafe_warning_dialog_fragment.*
import takeaway.feature_cafe.R
import takeaway.feature_cafe.severalcafe.presentation.SeveralCafeWarningDialogPresenter
import takeaway.feature_cafe.severalcafe.presentation.SeveralCafeWarningView
import javax.inject.Inject

class SeveralCafeWarningDialogFragment :
    BaseDialogFragment(R.layout.several_cafe_warning_dialog_fragment), SeveralCafeWarningView {

    companion object {
        const val ACCEPT_RESULT = 0
        const val REJECT_RESULT = 1
    }

    @Inject
    lateinit var presenter: SeveralCafeWarningDialogPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initListeners()
    }

    private fun initListeners() {
        clearBasketButton.setOnClickListener { presenter.onClearBasketClicked() }
        cancelButton.setOnClickListener { presenter.onCancelClicked() }
    }

    override fun closeDialogWithAcceptResult() {
        targetFragment?.onActivityResult(
            targetRequestCode,
            ACCEPT_RESULT, null
        )
        dismiss()
    }

    override fun closeDialogWithRejectResult() {
        targetFragment?.onActivityResult(
            targetRequestCode,
            REJECT_RESULT, null
        )
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}