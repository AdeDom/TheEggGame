package com.adedom.teg.domain.usecase

import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.model.ValidateSignIn
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.SignInRequest
import com.adedom.teg.models.response.SignInResponse
import com.adedom.teg.models.response.Token
import com.adedom.teg.presentation.usercase.SignInUseCase
import com.adedom.teg.sharedpreference.service.PreferenceConfig
import com.adedom.teg.sharedpreference.service.SessionManagerService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SignInUseCaseImplTest {

    private lateinit var useCase: SignInUseCase
    private val repository = mockk<DefaultTegRepository>(relaxed = true)
    private val sessionManagerService = mockk<SessionManagerService>(relaxed = true)
    private val preferenceConfig = mockk<PreferenceConfig>(relaxed = true)

    @Before
    fun setup() {
        useCase = SignInUseCaseImpl(
            repository = repository,
            sessionManagerService = sessionManagerService,
            preferenceConfig = preferenceConfig,
        )
    }

    @Test
    fun callSignIn_success() = runBlockingTest {
        // given
        val username = "admin"
        val accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
        val refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
        val signInRequest = SignInRequest(username = username, password = "1234")
        val signInResponse = SignInResponse(
            success = true,
            message = "OK",
            token = Token(accessToken = accessToken, refreshToken = refreshToken)
        )
        val success = Resource.Success(signInResponse)
        coEvery { repository.callSignIn(any()) } returns success
        every { sessionManagerService.accessToken } returns accessToken
        every { sessionManagerService.refreshToken } returns refreshToken
        every { preferenceConfig.signOut } returns false
        every { preferenceConfig.username } returns username

        // when
        val resource = useCase.callSignIn(signInRequest)

        // then
        assertEquals(success, resource)
        assertEquals(accessToken, sessionManagerService.accessToken)
        assertEquals(refreshToken, sessionManagerService.refreshToken)
        assertFalse(preferenceConfig.signOut)
        assertEquals(signInRequest.username, preferenceConfig.username)
    }

    @Test
    fun callSignIn_error() = runBlockingTest {
        // given
        val throwable = Throwable("Api error")
        val error = Resource.Error(throwable)
        coEvery { repository.callSignIn(any()) } returns error

        // when
        val result = useCase.callSignIn(SignInRequest())

        // then
        assertEquals(error, result)
    }

    @Test
    fun validateSignIn_incorrectUsernameIsNull_returnValidateError() {
        // given
        val signInRequest = SignInRequest(username = null, password = null)

        // when
        val result = useCase.validateSignIn(signInRequest)

        // then
        assertEquals(ValidateSignIn.VALIDATE_ERROR, result)
    }

    @Test
    fun validateSignIn_incorrectUsernameIsBlank_returnUsernameEmpty() {
        // given
        val signInRequest = SignInRequest(username = "", password = null)

        // when
        val result = useCase.validateSignIn(signInRequest)

        // then
        assertEquals(ValidateSignIn.USERNAME_EMPTY, result)
    }

    @Test
    fun validateSignIn_incorrectUsernameIsLengthLessThanFour_returnUsernameIncorrect() {
        // given
        val signInRequest = SignInRequest(username = "ad", password = null)

        // when
        val result = useCase.validateSignIn(signInRequest)

        // then
        assertEquals(ValidateSignIn.USERNAME_INCORRECT, result)
    }

    @Test
    fun validateSignIn_incorrectPasswordIsNull_returnValidateError() {
        // given
        val signInRequest = SignInRequest(username = "admin", password = null)

        // when
        val result = useCase.validateSignIn(signInRequest)

        // then
        assertEquals(ValidateSignIn.VALIDATE_ERROR, result)
    }

    @Test
    fun validateSignIn_incorrectPasswordIsBlank_returnPasswordEmpty() {
        // given
        val signInRequest = SignInRequest(username = "admin", password = "")

        // when
        val result = useCase.validateSignIn(signInRequest)

        // then
        assertEquals(ValidateSignIn.PASSWORD_EMPTY, result)
    }

    @Test
    fun validateSignIn_incorrectPasswordIsLengthLessThanFour_returnPasswordIncorrect() {
        // given
        val signInRequest = SignInRequest(username = "admin", password = "12")

        // when
        val result = useCase.validateSignIn(signInRequest)

        // then
        assertEquals(ValidateSignIn.PASSWORD_INCORRECT, result)
    }

    @Test
    fun validateSignIn_correct_returnValidateSuccess() {
        // given
        val signInRequest = SignInRequest(username = "admin", password = "1234")

        // when
        val result = useCase.validateSignIn(signInRequest)

        // then
        assertEquals(ValidateSignIn.VALIDATE_SUCCESS, result)
    }

    @Test
    fun isValidateUsername_incorrectUsernameIsBlank_returnFalse() {
        // given
        val username = ""

        // when
        val result = useCase.isValidateUsername(username)

        // then
        assertFalse(result)
    }

    @Test
    fun isValidateUsername_incorrectUsernameLengthLessThanFour_returnFalse() {
        // given
        val username = "ad"

        // when
        val result = useCase.isValidateUsername(username)

        // then
        assertFalse(result)
    }

    @Test
    fun isValidateUsername_correctUsername_returnTrue() {
        // given
        val username = "admin"

        // when
        val result = useCase.isValidateUsername(username)

        // then
        assertTrue(result)
    }

    @Test
    fun isValidatePassword_incorrectPasswordIsBlank_returnFalse() {
        // given
        val password = ""

        // when
        val result = useCase.isValidatePassword(password)

        // then
        assertFalse(result)
    }

    @Test
    fun isValidatePassword_incorrectPasswordLengthLessThanFour_returnFalse() {
        // given
        val password = "12"

        // when
        val result = useCase.isValidatePassword(password)

        // then
        assertFalse(result)
    }

    @Test
    fun isValidatePassword_correctPassword_returnTrue() {
        // given
        val password = "1234"

        // when
        val result = useCase.isValidatePassword(password)

        // then
        assertTrue(result)
    }

}
