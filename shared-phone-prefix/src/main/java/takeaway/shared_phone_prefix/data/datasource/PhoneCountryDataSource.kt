package takeaway.shared_phone_prefix.data.datasource

import android.content.Context
import android.telephony.TelephonyManager
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import java.util.*
import javax.inject.Inject

class PhoneCountryDataSource @Inject constructor(
    private val context: Context
) {

    private var telephonyManager =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    fun getPhoneCountryPrefix(): String =
        PhoneNumberUtil
            .createInstance(context)
            .getCountryCodeForRegion(telephonyManager.networkCountryIso.toUpperCase(Locale.getDefault()))
            .toString()
}