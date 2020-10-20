package com.adedom.teg.domain.usecase

import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.presentation.usercase.ChangeProfileUseCase
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ChangeProfileUseCaseImplTest {

    private val repository = mockk<DefaultTegRepository>(relaxed = true)
    private lateinit var usecase: ChangeProfileUseCase

    @Before
    fun setup() {
        usecase = ChangeProfileUseCaseImpl(repository)
    }

    @Test
    fun getIntCalendarYear_returnYear() {
        // given
        val date = "30/10/1994"

        // when
        val year = usecase.getIntCalendarYear(date)

        // then
        assertEquals(1994, year)
    }

    @Test
    fun getIntCalendarYear_returnMonth() {
        // given
        val date = "30/10/1994"

        // when
        val month = usecase.getIntCalendarMonth(date)

        // then
        assertEquals(9, month)
    }

    @Test
    fun getIntCalendarYear_returnDayOfMonth() {
        // given
        val date = "30/10/1994"

        // when
        val dayOfMonth = usecase.getIntCalendarDayOfMonth(date)

        // then
        assertEquals(30, dayOfMonth)
    }

}
