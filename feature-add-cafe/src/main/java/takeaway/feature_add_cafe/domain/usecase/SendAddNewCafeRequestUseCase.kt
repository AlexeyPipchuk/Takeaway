package takeaway.feature_add_cafe.domain.usecase

import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import takeaway.feature_add_cafe.domain.entity.NewCafeRequest
import javax.inject.Inject

class SendAddNewCafeRequestUseCase @Inject constructor() {

    operator fun invoke(newCafeRequest: NewCafeRequest): Completable =
        Completable.complete()
            .subscribeOn(Schedulers.io())
}