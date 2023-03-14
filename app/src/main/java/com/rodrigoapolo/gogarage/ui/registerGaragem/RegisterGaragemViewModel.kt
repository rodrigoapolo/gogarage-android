package com.rodrigoapolo.gogarage.ui.registerGaragem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterGaragemViewModel: ViewModel() {

    private val _timeStart = MutableLiveData<String>()
    private val _timeEnd = MutableLiveData<String>()


    fun timeStart(): LiveData<String> {
        return _timeStart
    }

    fun timeEnd(): LiveData<String> {
        return _timeEnd
    }


    fun formataTimeStart(hour: Int, minute: Int){
        val strMinute = "%02d".format(minute)
        val strHour = "%02d".format(hour)
        _timeStart.value = "$strHour:$strMinute"
    }

    fun formataTimeEnd(hour: Int, minute: Int){
        val strMinute = "%02d".format(minute)
        val strHour = "%02d".format(hour)
        _timeEnd.value = "$strHour:$strMinute"

    }


}