package com.example.jetpackcomposefabanimation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    var items = mutableStateListOf<ListItemModel>()
        private set

    init {
        repeat(20) {
            items.add(ListItemModel())
        }
    }
}