package com.manuelperera.flightsschedules.domain.usecase

import com.manuelperera.flightsschedules.domain.service.ContextDataService
import com.manuelperera.flightsschedules.domain.usecase.login.SaveAccessTokenUseCase
import com.manuelperera.flightsschedules.extensions.ImmediateSchedulerRule
import com.manuelperera.flightsschedules.extensions.assertGeneralsSuccess
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
class SaveAccessTokenUseCaseTest {

    private lateinit var saveAccessTokenUseCase: SaveAccessTokenUseCase

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRule()
    @Mock
    private lateinit var contextDataService: ContextDataService

    @Before
    fun setUp() {
        saveAccessTokenUseCase = SaveAccessTokenUseCase(contextDataService)
    }

    @Test
    fun `invoke should return access token`() {
        val accessToken = "d3ukap6h6ym9pchzf3ax6nga"
        whenever(contextDataService.saveAccessToken(any())).doReturn(getObEitherSuccess(accessToken))

        val testObserver = saveAccessTokenUseCase(accessToken).test().await()

        testObserver.assertGeneralsSuccess { it == accessToken }
    }

}