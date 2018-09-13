package com.manuelperera.flightsschedules.domain.model.base

sealed class Failure(open val callInfo: CallInfo) {

    class Error(info: CallInfo) : Failure(info)
    class Timeout(info: CallInfo) : Failure(info)
    class NoMoreData(info: CallInfo) : Failure(info)

    abstract class FeatureFailure(override val callInfo: CallInfo) : Failure(callInfo)

}