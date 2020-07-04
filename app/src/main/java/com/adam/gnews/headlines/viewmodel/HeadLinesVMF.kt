package com.adam.gnews.headlines.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adam.gnews.headlines.model.HeadLinesC

class HeadLinesVMF(
    private val repo: HeadLinesC.Repository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return HeadLinesViewModel(
            repo = repo
        ) as T
    }
}