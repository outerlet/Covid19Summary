package jp.craftones.covid19summary.net

import jp.craftones.covid19summary.model.PatientsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CoronaDashboardService {
    @GET("api/Covid19JapanAll")
    fun prefectureSummary(@Query("dataName", encoded = true) dataName: String): Call<PatientsResponse>
}