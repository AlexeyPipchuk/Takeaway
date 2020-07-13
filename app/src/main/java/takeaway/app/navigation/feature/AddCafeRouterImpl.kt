package takeaway.app.navigation.feature

import ru.terrakok.cicerone.Router
import takeaway.feature_add_cafe.presentation.AddCafeRouter
import javax.inject.Inject

class AddCafeRouterImpl @Inject constructor(
    private val router: Router
) : AddCafeRouter {

    override fun back() {
        router.exit()
    }
}