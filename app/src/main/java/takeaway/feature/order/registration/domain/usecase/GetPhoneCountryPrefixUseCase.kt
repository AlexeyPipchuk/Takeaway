package takeaway.feature.order.registration.domain.usecase

import android.content.Context
import android.telephony.TelephonyManager
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import java.util.*
import javax.inject.Inject

//TODO(Вынести в датасорс)
class GetPhoneCountryPrefixUseCase @Inject constructor(
    private val context: Context
) {
    private var telephonyManager =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    operator fun invoke(): String =
        PhoneNumberUtil
            .createInstance(context)
            .getCountryCodeForRegion(telephonyManager.networkCountryIso.toUpperCase(Locale.getDefault()))
            .toString()
}