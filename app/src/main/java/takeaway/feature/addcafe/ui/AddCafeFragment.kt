package takeaway.feature.addcafe.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.takeaway.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.add_cafe_fragment.*
import kotlinx.android.synthetic.main.info_fragment.toolbar
import takeaway.app.BaseFragment
import takeaway.app.disable
import takeaway.app.makeSnackbar
import takeaway.feature.addcafe.presentation.AddCafePresenter
import takeaway.feature.addcafe.presentation.AddCafeView
import javax.inject.Inject

class AddCafeFragment : BaseFragment(R.layout.add_cafe_fragment), AddCafeView {

    companion object {
        fun getInstance(): Fragment = AddCafeFragment()
    }

    @Inject
    lateinit var presenter: AddCafePresenter

    private var snackbar: Snackbar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initToolbar()
        initListeners()
    }

    private fun initToolbar() {
        toolbar.setBackButtonListener {
            presenter.onBackClicked()
        }
    }

    private fun initListeners() {
        sendAddNewCafeRequestButton.setOnClickListener {
            presenter.onSendAddNewCafeClicked()
        }
    }

    override fun showSuccessSend() {
        snackbar = makeSnackbar(snackBarParent, R.string.send_add_new_cafe_request_snackbar_success)
        snackbar?.show()
    }

    override fun showFailSend() {
        snackbar = makeSnackbar(snackBarParent, R.string.send_add_new_cafe_request_snackbar_fail)
            ?.setAction(getString(R.string.send_add_new_cafe_request_snackbar_repeat)) {
                presenter.onSendAddNewCafeRepeatClick()
            }
        snackbar?.show()
    }

    override fun disableSendButton() {
        sendAddNewCafeRequestButton.disable()
    }

    override fun onDestroyView() {
        snackbar?.dismiss()
        super.onDestroyView()
        presenter.detachView()
    }
}