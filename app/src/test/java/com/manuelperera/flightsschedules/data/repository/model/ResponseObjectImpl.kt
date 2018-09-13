package com.manuelperera.flightsschedules.data.repository.model

import com.manuelperera.flightsschedules.domain.model.base.ResponseObject

class ResponseObjectImpl : ResponseObject<Any> {

    override fun toAppDomain() = Any()

}