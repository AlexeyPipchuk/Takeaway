package takeaway.feature.cafe.presentation

import ru.terrakok.cicerone.Router
import takeaway.app.BasePresenter
import takeaway.feature.cafe.ui.CafeView
import takeaway.feature.feed.domain.entity.Cafe
import javax.inject.Inject

class CafePresenter @Inject constructor(
    private val cafe: Cafe,
    private val router: Router
) : BasePresenter<CafeView>() {

    override fun onViewAttach() {
        super.onViewAttach()

        view?.setCafeName(cafe.name)
    }
}