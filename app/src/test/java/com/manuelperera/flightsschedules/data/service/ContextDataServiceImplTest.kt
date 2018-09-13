package com.manuelperera.flightsschedules.data.service

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ContextDataServiceImplTest {

    private lateinit var contextDataServiceImpl: ContextDataServiceImpl

    @Before
    fun setUp() {
        contextDataServiceImpl = ContextDataServiceImpl()
    }

    @Test
    fun `getAccessToken should return access token`() {
        val accessToken = "d3ukap6h6ym9pchzf3ax6nga"
        contextDataServiceImpl.saveAccessToken(accessToken).subscribe()

        val result = contextDataServiceImpl.getAccessToken()

        assert(result == accessToken)
    }

}