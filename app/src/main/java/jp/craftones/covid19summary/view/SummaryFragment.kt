package jp.craftones.covid19summary.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import jp.craftones.covid19summary.R
import jp.craftones.covid19summary.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        DataBindingUtil.inflate<FragmentSummaryBinding>(inflater, R.layout.fragment_summary, container, false).root
}