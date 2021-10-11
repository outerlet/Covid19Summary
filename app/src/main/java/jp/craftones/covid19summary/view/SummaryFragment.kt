package jp.craftones.covid19summary.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import jp.craftones.covid19summary.R
import jp.craftones.covid19summary.databinding.FragmentSummaryBinding
import jp.craftones.covid19summary.databinding.ViewSummaryListItemBinding
import jp.craftones.covid19summary.model.PatientsResponse
import jp.craftones.covid19summary.net.CoronaDashboardService
import jp.craftones.covid19summary.viewmodel.MainViewModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SummaryFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentSummaryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        DataBindingUtil.inflate<FragmentSummaryBinding>(inflater, R.layout.fragment_summary, container, false).also {
            it.listSummary.addItemDecoration(
                DividerItemDecoration(requireContext(), LinearLayoutManager(requireContext()).orientation)
            )

            binding = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Retrofit.Builder()
            .baseUrl("https://opendata.corona.go.jp/")
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            ))
            .build()
            .create(CoronaDashboardService::class.java)
            .prefectureSummary(checkNotNull(viewModel.selectedPrefecture.value))
            .enqueue(
                object : Callback<PatientsResponse> {
                    override fun onResponse(call: Call<PatientsResponse>, response: Response<PatientsResponse>) {
                        if (response.isSuccessful) {
                            response.body()?.let { res ->
                                binding.listSummary.adapter = SummaryAdapter(res.summaryList)
                            } ?: Log.d("DEBUG-PRINT", "Response body is null.")
                        } else {
                            response.errorBody()?.let { err ->
                                Log.d("DEBUG-PRINT", "ERROR: ${err.string()}")
                            } ?: Log.d("DEBUG-PRINT", "Response error body is null.")
                        }
                    }

                    override fun onFailure(call: Call<PatientsResponse>, t: Throwable) {
                        t.printStackTrace()
                    }
                }
            )
    }
}

private class SummaryViewHolder(val binding: ViewSummaryListItemBinding) : RecyclerView.ViewHolder(binding.root)

private class SummaryAdapter(val summaryList: List<PatientsResponse.Summary>) : RecyclerView.Adapter<SummaryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder =
        SummaryViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.view_summary_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        holder.binding.summary = summaryList[position]
    }

    override fun getItemCount(): Int = summaryList.count()

}