package jp.craftones.covid19summary.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.ListPopupWindow
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import jp.craftones.covid19summary.R
import jp.craftones.covid19summary.databinding.FragmentEntryBinding
import jp.craftones.covid19summary.viewmodel.MainViewModel

class EntryFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentEntryBinding

    private val prefectures: Array<String> by lazy {
        resources.getStringArray(R.array.prefectures)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        DataBindingUtil.inflate<FragmentEntryBinding>(inflater, R.layout.fragment_entry, container, false).also {
            it.buttonSelectPrefecture.setOnClickListener {
                showPrefectureList()
            }

            it.buttonShowSummary.setOnClickListener {
                findNavController().navigate(R.id.entryToSummary)
            }

            binding = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.selectedPrefecture.observe(viewLifecycleOwner) {
            if (!it.isNullOrBlank()) {
                binding.buttonSelectPrefecture.text = it
            }
        }
    }

    private fun showPrefectureList() {
        ListPopupWindow(requireContext(), null, R.attr.listPopupWindowStyle).apply {
            setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, prefectures))
            anchorView = binding.buttonSelectPrefecture
            setOnItemClickListener { _, _, position, _ ->
                viewModel.selectedPrefecture.value = prefectures.getOrNull(position)
                dismiss()
            }
        }.show()
    }
}