package com.rodrigoapolo.gogarage.ui.home

import androidx.lifecycle.ViewModel
import com.rodrigoapolo.gogarage.repository.Repository

@Suppress("UNCHECKED_CAST")
class ViewModelProvider(private var repository: Repository) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("ViewModel class not found.")
    }
}