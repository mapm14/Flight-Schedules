package com.manuelperera.flightsschedules.domain.model.base

interface ResponseObject<out DomainObject : Any> {

    fun toAppDomain(): DomainObject

}