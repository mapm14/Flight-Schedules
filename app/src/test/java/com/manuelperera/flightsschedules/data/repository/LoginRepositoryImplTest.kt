package com.manuelperera.flightsschedules.data.repository

import com.manuelperera.flightsschedules.data.net.LoginApiClient
import com.manuelperera.flightsschedules.data.repository.datasources.api.login.LoginApi
import com.manuelperera.flightsschedules.data.entity.login.LoginResponse
import com.manuelperera.flightsschedules.data.repository.implementation.api.LoginRepositoryImpl
import com.manuelperera.flightsschedules.extensions.assertGeneralsError
import com.manuelperera.flightsschedules.extensions.assertGeneralsSuccess
import com.manuelperera.flightsschedules.extensions.getObResultError
import com.manuelperera.flightsschedules.extensions.getObResultSuccess
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginRepositoryImplTest {

    private lateinit var loginRepositoryImpl: LoginRepositoryImpl

    @Mock
    private lateinit var loginApiClient: LoginApiClient
    @Mock
    private lateinit var loginApi: LoginApi

    @Before
    fun setUp() {
        whenever(loginApiClient.api).then { loginApi }

        loginRepositoryImpl = LoginRepositoryImpl(loginApiClient)
    }

    @Test
    fun `login should return access token`() {
        val accessToken = "d3ukap6h6ym9pchzf3ax6nga"
        whenever(loginApi.login(any(), any(), any())).doReturn(getObResultSuccess(LoginResponse(accessToken)))

        val testObserver = loginRepositoryImpl.login("id", "secret").test()

        testObserver.assertGeneralsSuccess { it == accessToken }
    }

    @Test
    fun `login should return failure error`() {
        whenever(loginApi.login(any(), any(), any())).doReturn(getObResultError())

        val testObserver = loginRepositoryImpl.login("id", "secret").test()

        testObserver.assertGeneralsError()
    }

}