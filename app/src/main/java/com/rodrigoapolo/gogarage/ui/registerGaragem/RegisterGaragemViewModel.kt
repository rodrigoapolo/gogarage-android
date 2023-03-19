package com.rodrigoapolo.gogarage.ui.registerGaragem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoapolo.gogarage.util.validate.ValidateCompose

class RegisterGaragemViewModel : ViewModel() {

    private val _timeStart = MutableLiveData<String>()
    private val _timeEnd = MutableLiveData<String>()
    private val _timeValue = MutableLiveData<String>()
    private val _timeRate = MutableLiveData<String>()
    private val _height = MutableLiveData<String>()
    private val _width = MutableLiveData<String>()
    private val _register = MutableLiveData<Boolean>()

    fun doContinue() {
        _register.value =
            (_timeValue.value == null || _timeRate.value == null || _height.value == null || _width.value == null)
    }

        fun register(): LiveData<Boolean> {
            return _register
        }

        fun timeStart(): LiveData<String> {
            return _timeStart
        }

        fun timeEnd(): LiveData<String> {
            return _timeEnd
        }

        fun timeValue(): LiveData<String> {
            return _timeValue
        }

        fun timeRate(): LiveData<String> {
            return _timeRate
        }

        fun height(): LiveData<String> {
            return _height
        }

        fun width(): LiveData<String> {
            return _width
        }

        fun validateWidth(value: String, msg: String) {
            _width.value = ValidateCompose.camposeNullOrEmpty(value, msg)
        }

        fun validateHeight(value: String, msg: String) {
            _height.value = ValidateCompose.camposeNullOrEmpty(value, msg)
        }

        fun validateTimeRate(value: String, msg: String) {
            _timeRate.value = ValidateCompose.camposeNullOrEmpty(value, msg)
        }

        fun validateTimeValue(value: String, msg: String) {
            _timeValue.value = ValidateCompose.camposeNullOrEmpty(value, msg)
        }

        fun formataTimeStart(hour: Int, minute: Int) {
            _timeStart.value = "%02d".format(hour) + ":" + "%02d".format(minute)
        }

        fun formataTimeEnd(hour: Int, minute: Int) {
            _timeEnd.value = "%02d".format(hour) + ":" + "%02d".format(minute)
        }



}