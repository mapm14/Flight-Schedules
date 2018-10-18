package com.manuelperera.flightsschedules.extensions

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import com.manuelperera.flightsschedules.data.entity.error.ErrorResponse.Companion.DEFAULT_CODE_ERROR
import com.manuelperera.flightsschedules.domain.model.base.Failure
import com.manuelperera.flightsschedules.domain.model.getFailureError
import com.manuelperera.flightsschedules.domain.model.getNoMoreData
import io.reactivex.Observable
import io.reactivex.Observable.just
import io.reactivex.observers.TestObserver
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.adapter.rxjava2.Result

@Suppress("UNCHECKED_CAST")
fun <T : Either<Failure, R>, R : Any> TestObserver<T>.assertGeneralsSuccess(asserts: (R) -> Boolean = { true }) {
    assertComplete()
    assertValueCount(1)
    assertNoErrors()
    assertValue {
        if (it is Either.Right<*>) {
            asserts(it.b as R)
        } else false
    }
}

@Suppress("UNCHECKED_CAST")
fun <T : Either<Failure, R>, R : Any> TestObserver<T>.assertGeneralsError(asserts: (Failure) -> Boolean = { true }) {
    assertComplete()
    assertValueCount(1)
    assertNoErrors()
    assertValue {
        if (it is Either.Left<*>) {
            (it.a as Failure).callInfo.code == DEFAULT_CODE_ERROR && asserts(it.a as Failure)
        } else false
    }
}

fun <T> getObResultSuccess(value: T): Observable<Result<T>> = just(Result.response(Response.success(value)))

fun <T> getObResultError(): Observable<Result<T>> = just(Result.response(Response.error(DEFAULT_CODE_ERROR, ResponseBody.create(null, byteArrayOf()))))

fun <T> getObEitherSuccess(value: T): Observable<Either<Failure, T>> = just(Right(value))

fun <T> getObEitherError(): Observable<Either<Failure, T>> = just(Left(getFailureError()))

fun <T> getObEitherNoMoreData(): Observable<Either<Failure, T>> = just(Left(getNoMoreData()))