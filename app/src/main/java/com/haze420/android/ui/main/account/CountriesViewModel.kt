package com.haze420.android.ui.main.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.haze420.android.R
import com.haze420.android.model.repositories.CountriesRepository

class CountriesViewModel : ViewModel() {

    private val _selected = MutableLiveData<String>()
    private  val _repository = CountriesRepository()


    init {
        _repository.trigerFetchList()
    }

    //Getter
    fun getCountryList() : MutableLiveData<List<String>>{
        return _repository.getCountryList()
    }
    fun getSelected(): MutableLiveData<String>{
        return _selected
    }

    // Commands from View
    fun refresh(area: Int){
        _repository.trigerFetchList()
    }

    // Data binding
    fun onItemClick(position: Int){
        _selected.value = getCountryAt(position)
    }

    //Getter
    fun getCountryAt(position: Int) : String{
        getCountryList().value?.let { return it.get(position) }
        return ""
    }

    fun getBackgroundColorAt(position: Int): Int{
        if (position % 2 == 0){
            return R.color.countrytext_bg
        }else{
            return android.R.color.transparent
        }
    }

    fun isVailable(position: Int): Boolean{
        return position % 2 == 0
    }



}
