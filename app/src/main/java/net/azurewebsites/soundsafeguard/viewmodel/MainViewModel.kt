package net.azurewebsites.soundsafeguard.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){
    var isActivated = mutableStateOf(false)
}