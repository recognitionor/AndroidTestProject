package com.example.architecturetest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerInfoViewModel : ViewModel() {
    private var liveData = MutableLiveData<PlayerInfo>()

    fun getPlayerInfo(): MutableLiveData<PlayerInfo> {
        return liveData
    }

}