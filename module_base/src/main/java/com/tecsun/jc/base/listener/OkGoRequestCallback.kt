package com.tecsun.jc.base.listener

interface OkGoRequestCallback<T> {

    fun onSuccess(t: T)

    fun onError(throwable: Throwable?)
}