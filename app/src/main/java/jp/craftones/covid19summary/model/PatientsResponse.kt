package jp.craftones.covid19summary.model

import com.squareup.moshi.Json

data class PatientsResponse(
    @Json(name = "itemList")
    val summaryList: List<Summary>,

    @Json(name = "errorInfo")
    val errorInfo: ErrorInfo,
) {
    data class Summary(
        @Json(name = "date")
        val date: String,

        @Json(name = "name_jp")
        val nameJp: String,

        @Json(name = "npatients")
        val number: String,
    )

    data class ErrorInfo(
        @Json(name = "errorFlag")
        val flag: String,

        @Json(name = "errorCode")
        val code: String?,

        @Json(name = "errorMessage")
        val message: String?,
    )
}
