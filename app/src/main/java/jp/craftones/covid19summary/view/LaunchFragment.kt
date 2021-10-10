package jp.craftones.covid19summary.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import jp.craftones.covid19summary.R
import jp.craftones.covid19summary.databinding.FragmentLaunchBinding

class LaunchFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        DataBindingUtil.inflate<FragmentLaunchBinding>(inflater, R.layout.fragment_launch, container, false).apply {
            buttonSelectPrefecture.setOnClickListener {
                findNavController().navigate(R.id.launchToEntry)
            }
        }.root
}