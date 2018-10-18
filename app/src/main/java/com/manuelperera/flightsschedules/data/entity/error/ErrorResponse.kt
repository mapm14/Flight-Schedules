package com.manuelperera.flightsschedules.data.entity.error

import com.google.gson.annotations.SerializedName
import com.manuelperera.flightsschedules.domain.model.base.CallInfo
import com.manuelperera.flightsschedules.domain.model.base.ResponseObject

class ErrorResponse(
        @SerializedName("ProcessingErrors") val processingErrors: ProcessingErrors = ProcessingErrors()
) : ResponseObject<CallInfo> {

    constructor(code: Int, description: String) : this() {
        ErrorResponse(ProcessingErrors(ProcessingErrors.ProcessingError(code = code, description = description)))
    }

    companion object {
        const val DEFAULT_CODE_ERROR = 400
    }

    override fun toAppDomain() = CallInfo(
            processingErrors.processingError.code,
            processingErrors.processingError.description
    )

    class ProcessingErrors(
            @SerializedName("ProcessingError") val processingError: ProcessingError = ProcessingError()
    ) {

        class ProcessingError(
                @SerializedName("@RetryIndicator") val retryIndicator: String = "",
                @SerializedName("Type") val type: String = "",
                @SerializedName("Code") val code: Int = DEFAULT_CODE_ERROR,
                @SerializedName("Description") val description: String = "Unknown Error",
                @SerializedName("InfoURL") val infoURL: String = ""
        )
    }
}