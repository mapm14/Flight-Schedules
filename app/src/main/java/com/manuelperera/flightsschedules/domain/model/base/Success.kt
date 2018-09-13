package com.manuelperera.flightsschedules.domain.model.base

sealed class Success(open val info: CallInfo) {

    class None(i: CallInfo) : Success(i)

}