package com.manuelperera.flightsschedules.data.repository.base

import android.util.Log
import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import com.google.gson.Gson
import com.manuelperera.flightsschedules.domain.model.base.Failure
import com.manuelperera.flightsschedules.domain.model.base.Failure.*
import com.manuelperera.flightsschedules.domain.model.base.ResponseObject
import com.manuelperera.flightsschedules.domain.model.base.Success
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.adapter.rxjava2.Result
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit.SECONDS

open class BaseRepository {

    fun <R : ResponseObject<DomainObject>, DomainObject : Any> modifyObservable(observable: Observable<Result<R>>): Observable<Either<Failure, DomainObject>> =
            observable.flatMap { data ->
                Observable.create<Either<Failure, DomainObject>> { observer ->

                    if (data.response() == null) Log.e(this::class.java.simpleName, data.error().toString())

                    data.response()?.let { response ->
                        val body: R? = response.body()
                        val code = response.code()
                        val errorBody = response.errorBody()

                        val either = when {
                            code in 200..208 && body != null -> getDomainObject(body)
                            code in 200..208 && body == null -> getDomainObjectNoResponse(code)
                            code in 400..512 -> getFailureErrorWithErrorResponse(errorBody)
                            else -> getFailureUnknownError()
                        }
                        observer.onNext(either)

                    } ?: observer.onNext(getFailureError(data.error()))

                    observer.onComplete()
                }
            }.timeout(30, SECONDS, Observable.create<Either<Failure, DomainObject>> { subscriber ->
                subscriber.onNext(getFailureTimeout())
                subscriber.onComplete()
            })

    @Suppress("UNCHECKED_CAST")
    private fun <T : ResponseObject<DomainObject>, DomainObject : Any> getDomainObject(body: T) =
            Right((body as ResponseObject<Any>).toAppDomain()) as Either<Failure, DomainObject>

    @Suppress("UNCHECKED_CAST")
    private fun <DomainObject : Any> getDomainObjectNoResponse(code: Int) =
            Right(Success.None(ErrorResponse(code, "").toAppDomain())) as Either<Failure, DomainObject>

    protected open fun getFailureErrorWithErrorResponse(errorBody: ResponseBody?): Either<Failure, Nothing> {
        val errorInfo = parseErrorResponse(errorBody).toAppDomain()
        return when (errorInfo.code) {
            3603 -> Left(NoMoreData(errorInfo))
            else -> Left(Error(errorInfo))
        }
    }

    private fun getFailureError(throwable: Throwable?): Either<Failure.Error, Nothing> {
        val errorInfo = ErrorResponse().toAppDomain()
        errorInfo.message = when (throwable) {
            is UnknownHostException -> "No Internet connection"
            else -> throwable?.message ?: "Unknown error"
        }

        return Left(Error(errorInfo))
    }

    private fun getFailureUnknownError() = Left(Error(ErrorResponse().toAppDomain()))

    private fun getFailureTimeout() = Left(Timeout(ErrorResponse(0, "Ups! Slow connection, try again").toAppDomain()))

    private fun parseErrorResponse(responseBody: ResponseBody?): ErrorResponse =
            responseBody?.let { Gson().fromJson(it.string(), ErrorResponse::class.java) }
                    ?: ErrorResponse()

}