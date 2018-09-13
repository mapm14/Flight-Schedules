package com.manuelperera.flightsschedules.data.repository

import com.manuelperera.flightsschedules.data.repository.base.BaseRepository
import com.manuelperera.flightsschedules.data.repository.model.ResponseObjectImpl
import com.manuelperera.flightsschedules.extensions.assertGeneralsError
import com.manuelperera.flightsschedules.extensions.assertGeneralsSuccess
import com.manuelperera.flightsschedules.extensions.getObEitherError
import com.manuelperera.flightsschedules.extensions.getObEitherSuccess
import com.manuelperera.flightsschedules.extensions.getObResultError
import com.manuelperera.flightsschedules.extensions.getObResultSuccess
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BaseRepositoryTest {

    @Mock
    private lateinit var baseRepository: BaseRepository

    @Test
    fun `modify observable should return Either-Right`() {
        val modifyOb = getObEitherSuccess(Any())
        val successOb = getObResultSuccess(ResponseObjectImpl())
        whenever(baseRepository.modifyObservable(successOb)).doReturn(modifyOb)

        val testObserver = baseRepository.modifyObservable(successOb).test()

        testObserver.assertGeneralsSuccess()
    }

    @Test
    fun `modify observable should return Either-Left`() {
        val modifyOb = getObEitherError<Any>()
        val errorOb = getObResultError<ResponseObjectImpl>()
        whenever(baseRepository.modifyObservable(errorOb)).doReturn(modifyOb)

        val testObserver = baseRepository.modifyObservable(errorOb).test()

        testObserver.assertGeneralsError()
    }

}