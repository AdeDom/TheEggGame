package com.adedom.teg.domain.usecase

import com.adedom.teg.data.models.SingleItemDb
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.TegLatLng
import com.adedom.teg.presentation.usercase.SingleUseCase
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SingleUseCaseImplTest {

    private lateinit var useCase: SingleUseCase
    private val repository = mockk<DefaultTegRepository>(relaxed = true)

    @Before
    fun setup() {
        useCase = SingleUseCaseImpl(repository)
    }

    @Test
    fun isValidateDistanceBetween_near_returnTrue() {
        // given
        val latLng = TegLatLng(latitude = 13.66464, longitude = 100.614645)
        val singleItems = listOf(
            SingleItemDb(
                singleId = 241,
                itemTypeId = 2,
                latitude = 13.66278,
                longitude = 100.5989933,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605520245135,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 251,
                itemTypeId = 2,
                latitude = 13.66478,
                longitude = 100.5989933,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605520246725,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 261,
                itemTypeId = 2,
                latitude = 13.66478,
                longitude = 100.6139933,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605520248404,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 271,
                itemTypeId = 4,
                latitude = 13.66678,
                longitude = 100.6009933,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605520250006,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 311,
                itemTypeId = 1,
                latitude = 13.67578,
                longitude = 100.5989933,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605520256770,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 321,
                itemTypeId = 3,
                latitude = 13.67478,
                longitude = 100.59699330000001,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605520258323,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 331,
                itemTypeId = 2,
                latitude = 13.66478,
                longitude = 100.61499330000001,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605520259923,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 411,
                itemTypeId = 3,
                latitude = 13.676314600000001,
                longitude = 100.61850439999999,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605850342955,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 421,
                itemTypeId = 2,
                latitude = 13.669839999999999,
                longitude = 100.601935,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605850357939,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 441,
                itemTypeId = 4,
                latitude = 13.6626617,
                longitude = 100.6195833,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605853394110,
                dateTimeUpdated = null
            )
        )

        // when
        val result = useCase.isValidateDistanceBetween(latLng, singleItems)

        // then
        assertTrue(result)
    }

    @Test
    fun getSingleItemId_itemOverThanOne_return261() {
        // given
        val latLng = TegLatLng(latitude = 13.66464, longitude = 100.614645)
        val singleItems = listOf(
            SingleItemDb(
                singleId = 241,
                itemTypeId = 2,
                latitude = 13.66278,
                longitude = 100.5989933,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605520245135,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 251,
                itemTypeId = 2,
                latitude = 13.66478,
                longitude = 100.5989933,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605520246725,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 261,
                itemTypeId = 2,
                latitude = 13.66478,
                longitude = 100.6139933,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605520248404,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 271,
                itemTypeId = 4,
                latitude = 13.66678,
                longitude = 100.6009933,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605520250006,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 311,
                itemTypeId = 1,
                latitude = 13.67578,
                longitude = 100.5989933,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605520256770,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 321,
                itemTypeId = 3,
                latitude = 13.67478,
                longitude = 100.59699330000001,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605520258323,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 331,
                itemTypeId = 2,
                latitude = 13.66478,
                longitude = 100.61499330000001,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605520259923,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 411,
                itemTypeId = 3,
                latitude = 13.676314600000001,
                longitude = 100.61850439999999,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605850342955,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 421,
                itemTypeId = 2,
                latitude = 13.669839999999999,
                longitude = 100.601935,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605850357939,
                dateTimeUpdated = null
            ),
            SingleItemDb(
                singleId = 441,
                itemTypeId = 4,
                latitude = 13.6626617,
                longitude = 100.6195833,
                playerId = null,
                status = "on",
                dateTimeCreated = 1605853394110,
                dateTimeUpdated = null
            )
        )

        // when
        val result = useCase.getSingleItemId(latLng, singleItems)

        // then
        assertEquals(261, result)
    }

}
