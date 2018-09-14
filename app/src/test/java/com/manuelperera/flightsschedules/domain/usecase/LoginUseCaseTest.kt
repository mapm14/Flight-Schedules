package com.manuelperera.flightsschedules.domain.usecase

import com.manuelperera.flightsschedules.domain.repository.LoginRepository
import com.manuelperera.flightsschedules.domain.usecase.login.LoginUseCase
import com.manuelperera.flightsschedules.domain.usecase.login.LoginUseCase.Params
import com.manuelperera.flightsschedules.domain.usecase.login.SaveAccessTokenUseCase
import com.manuelperera.flightsschedules.extensions.ImmediateSchedulerRuleUnitTests
import com.manuelperera.flightsschedules.extensions.assertGeneralsError
import com.manuelperera.flightsschedules.extensions.assertGeneralsSuccess
import com.manuelperera.flightsschedules.extensions.getObEitherError
import com.manuelperera.flightsschedules.extensions.getObEitherSuccess
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginUseCaseTest {

    private lateinit var loginUseCase: LoginUseCase

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleUnitTests()
    @Mock
    private lateinit var loginRepository: LoginRepository
    @Mock
    private lateinit var saveAccessTokenUseCase: SaveAccessTokenUseCase

    @Before
    fun setUp() {
        loginUseCase = LoginUseCase(loginRepository, saveAccessTokenUseCase)
    }

    @Test
    fun `invoke should return access token`() {
        val accessToken = "d3ukap6h6ym9pchzf3ax6nga"
        whenever(loginRepository.login(any(), any())).doReturn(getObEitherSuccess(accessToken))
        whenever(saveAccessTokenUseCase(any())).doReturn(getObEitherSuccess(accessToken))

        val testObserver = loginUseCase(Params("", "")).test().await()

        testObserver.assertGeneralsSuccess { it == accessToken }
    }

    @Test
    fun `invoke should return failure`() {
        whenever(loginRepository.login(any(), any())).doReturn(getObEitherError())
        whenever(saveAccessTokenUseCase(any())).doReturn(getObEitherError())

        val testObserver = loginUseCase(Params("", "")).test().await()

        testObserver.assertGeneralsError()
    }

}