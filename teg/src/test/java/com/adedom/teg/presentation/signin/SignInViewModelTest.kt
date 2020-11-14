package com.adedom.teg.presentation.signin

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.model.ValidateSignIn
import com.adedom.teg.models.response.SignInResponse
import com.adedom.teg.models.response.Token
import com.adedom.teg.presentation.usercase.SignInUseCase
import com.adedom.teg.util.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.KoinContextHandler
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class SignInViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val useCase = mockk<SignInUseCase>(relaxed = true)
    private lateinit var viewModel: SignInViewModel

    @Before
    fun setup() {
        viewModel = SignInViewModel(useCase)
    }

    @After
    fun cleanup() {
        KoinContextHandler.stop()
    }

    @Test
    fun setStateUsername_incorrectUsernameIsBlank() {
        // given
        val username = ""
        val isValidateUsername = false
        every { useCase.isValidateUsername(username) } returns isValidateUsername

        // when
        viewModel.setStateUsername(username)
        val state = viewModel.state.getOrAwaitValue()

        // then
        assertEquals(username, state.username)
        assertEquals(isValidateUsername, state.isValidUsername)
    }

    @Test
    fun setStateUsername_incorrectUsernameLengthLessThenFour() {
        // given
        val username = "ad"
        val isValidateUsername = false
        every { useCase.isValidateUsername(username) } returns isValidateUsername

        // when
        viewModel.setStateUsername(username)
        val state = viewModel.state.getOrAwaitValue()

        // then
        assertEquals(username, state.username)
        assertEquals(isValidateUsername, state.isValidUsername)
    }

    @Test
    fun setStateUsername_correct() {
        // given
        val username = "admin"
        val isValidateUsername = true
        every { useCase.isValidateUsername(username) } returns isValidateUsername

        // when
        viewModel.setStateUsername(username)
        val state = viewModel.state.getOrAwaitValue()

        // then
        assertEquals(username, state.username)
        assertEquals(isValidateUsername, state.isValidUsername)
    }

    @Test
    fun setStatePassword_incorrectPasswordIsBlank() {
        // given
        val password = ""
        val isValidatePassword = false
        every { useCase.isValidatePassword(password) } returns isValidatePassword

        // when
        viewModel.setStatePassword(password)
        val state = viewModel.state.getOrAwaitValue()

        // then
        assertEquals(password, state.password)
        assertEquals(isValidatePassword, state.isValidPassword)
    }

    @Test
    fun setStatePassword_incorrectPasswordLengthLessThenFour() {
        // given
        val password = "123"
        val isValidatePassword = false
        every { useCase.isValidatePassword(password) } returns isValidatePassword

        // when
        viewModel.setStatePassword(password)
        val state = viewModel.state.getOrAwaitValue()

        // then
        assertEquals(password, state.password)
        assertEquals(isValidatePassword, state.isValidPassword)
    }

    @Test
    fun setStatePassword_correct() {
        // given
        val password = "1234"
        val isValidatePassword = true
        every { useCase.isValidatePassword(password) } returns isValidatePassword

        // when
        viewModel.setStatePassword(password)
        val state = viewModel.state.getOrAwaitValue()

        // then
        assertEquals(password, state.password)
        assertEquals(isValidatePassword, state.isValidPassword)
    }

    @Test
    fun onSignIn_incorrectIsNull_returnValidateError() {
        // given
        val validateSignIn = ValidateSignIn.VALIDATE_ERROR
        every { useCase.validateSignIn(any()) } returns validateSignIn

        // when
        viewModel.onSignIn()
        val result = viewModel.signInEvent.getOrAwaitValue()

        // then
        assertEquals(validateSignIn, result)
    }

    @Test
    fun onSignIn_incorrectUsernameIsBlank_returnUsernameEmpty() {
        // given
        val validateSignIn = ValidateSignIn.USERNAME_EMPTY
        every { useCase.validateSignIn(any()) } returns validateSignIn

        // when
        viewModel.onSignIn()
        val result = viewModel.signInEvent.getOrAwaitValue()

        // then
        assertEquals(validateSignIn, result)
    }

    @Test
    fun onSignIn_incorrectUsernameIsLengthLessThanFour_returnUsernameIncorrect() {
        // given
        val validateSignIn = ValidateSignIn.USERNAME_INCORRECT
        every { useCase.validateSignIn(any()) } returns validateSignIn

        // when
        viewModel.onSignIn()
        val result = viewModel.signInEvent.getOrAwaitValue()

        // then
        assertEquals(validateSignIn, result)
    }

    @Test
    fun onSignIn_incorrectPasswordIsBlank_returnPasswordEmpty() {
        // given
        val validateSignIn = ValidateSignIn.PASSWORD_EMPTY
        every { useCase.validateSignIn(any()) } returns validateSignIn

        // when
        viewModel.onSignIn()
        val result = viewModel.signInEvent.getOrAwaitValue()

        // then
        assertEquals(validateSignIn, result)
    }

    @Test
    fun onSignIn_incorrectPasswordIsLengthLessThanFour_returnPasswordIncorrect() {
        // given
        val validateSignIn = ValidateSignIn.PASSWORD_INCORRECT
        every { useCase.validateSignIn(any()) } returns validateSignIn

        // when
        viewModel.onSignIn()
        val result = viewModel.signInEvent.getOrAwaitValue()

        // then
        assertEquals(validateSignIn, result)
    }

    @Test
    fun onSignIn_correct_returnValidateSuccess() {
        // given
        val validateSignIn = ValidateSignIn.VALIDATE_SUCCESS
        every { useCase.validateSignIn(any()) } returns validateSignIn

        // when
        viewModel.onSignIn()
        val result = viewModel.signInEvent.getOrAwaitValue()

        // then
        assertEquals(validateSignIn, result)
    }

    @Test
    fun callSignIn_success() {
        // given
        val response = SignInResponse(
            success = true,
            message = "OK",
            token = Token(
                accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9",
                refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
            )
        )
        val success = Resource.Success(response)
        coEvery { useCase.callSignIn(any()) } returns success

        // when
        viewModel.callSignIn()
        val result = viewModel.signIn.getOrAwaitValue()

        // then
        assertEquals(success.data, result)
    }

    @Test
    fun callSignIn_error() {
        // given
        val throwable = Throwable("Api error")
        val error = Resource.Error(throwable)
        coEvery { useCase.callSignIn(any()) } returns error

        // when
        viewModel.callSignIn()
        val result = viewModel.error.getOrAwaitValue()

        // then
        assertEquals(error, result)
    }

    @Test
    fun getConfigUsername() {
        // given
        val username = "AdeDom"
        every { useCase.getConfigUsername() } returns username

        // when
        val result = viewModel.getConfigUsername()

        // then
        assertEquals(username, result)
    }

}
