package com.haze420.android.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.haze420.android.model.enums.ActionBarItemType

class SharedViewModel : ViewModel() {
    private val _selectedActionBarItem = MutableLiveData<ActionBarItemType?>()

    // TODO: Implement the ViewModel
    fun getSelectedActionbarItem(): MutableLiveData<ActionBarItemType?>{
        return _selectedActionBarItem
    }

    fun setSelectedActionbarItem(selectedItem: ActionBarItemType?){
        _selectedActionBarItem.value = selectedItem
    }
}
