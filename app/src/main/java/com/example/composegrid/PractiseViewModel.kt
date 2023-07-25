package com.example.composegrid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PractiseViewModel:ViewModel() {

    //MutableStateFlow to hold the list of ImageInfoData

    private val _imageInfoList = MutableStateFlow<List<ImageInfoData>>(emptyList())
    val imageInfoList: StateFlow<List<ImageInfoData>> get() = _imageInfoList

    init {
        viewModelScope.launch {
          //  _imageInfoList.value = generateImageList()
        }
    }
}
