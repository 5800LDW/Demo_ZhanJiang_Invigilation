package com.tecsun.jc.base.listener

import android.os.Message

interface HandlerCallback {

    fun handleMessage(msg: Message?)

}