package com.haze420.android.ui

import android.text.BoringLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.haze420.android.model.enums.ActionBarItemType

class SharedViewModel : ViewModel() {
    private val _selectedActionBarItem = MutableLiveData<ActionBarItemType?>()

    private val _selectedCountry = MutableLiveData<String?>()

    init {

    }

    // TODO: Implement the ViewModel
    fun getSelectedActionbarItem(): MutableLiveData<ActionBarItemType?>{
        return _selectedActionBarItem
    }

    fun setSelectedActionbarItem(selectedItem: ActionBarItemType?){
        _selectedActionBarItem.value = selectedItem
    }

    fun getSelectedCountry(): MutableLiveData<String?>{
        return _selectedCountry
    }

    fun setSelectedCountry(selected: String?){
        _selectedCountry.value = selected
    }
}
