package jp.craftones.covid19summary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val selectedPrefecture = MutableLiveData<String>()
}