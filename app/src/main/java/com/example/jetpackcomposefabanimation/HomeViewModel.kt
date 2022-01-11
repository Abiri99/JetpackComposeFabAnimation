package com.example.jetpackcomposefabanimation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

//    init {
//        repeat(100) {
//            items.add(ListItemModel())
//        }
//    }

    var items = mutableStateListOf<ListItemModel>(ListItemModel(), ListItemModel(), ListItemModel(),)
        private set
}