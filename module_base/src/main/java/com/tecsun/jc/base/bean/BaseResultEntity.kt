package com.tecsun.jc.base.bean

abstract class BaseResultEntity {

    var statusCode: String? = null

    var message: String? = null

    fun isSuccess(): Boolean {
        return statusCode?.equals("200") ?: false
    }
}