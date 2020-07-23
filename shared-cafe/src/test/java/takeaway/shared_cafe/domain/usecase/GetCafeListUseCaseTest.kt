package takeaway.shared_cafe.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import takeaway.shared_cafe.cafe
import takeaway.shared_cafe.domain.repository.CafeRepository

@RunWith(MockitoJUnitRunner::class)
class GetCafeListUseCaseTest {

    private val cafeRepository: CafeRepository = mock()
    private val useCase = GetCafeListUseCase(cafeRepository)

    @Test
    fun `invoke EXPECT visible and open cafe list`() {
        val cafeList = listOf(
            cafe,
            cafe.copy(isClosed = true),
            cafe.copy(isVisible = false)
        )
        whenever(cafeRepository.getList(false)).thenReturn(Single.just(cafeList))

        useCase(false)
            .test()
            .assertValue(listOf(cafe))
    }
}