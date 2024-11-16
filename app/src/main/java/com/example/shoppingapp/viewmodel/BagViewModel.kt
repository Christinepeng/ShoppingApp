package com.example.shoppingapp.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BagViewModel @Inject constructor() : ViewModel() {
    val state = "Home State"
}
