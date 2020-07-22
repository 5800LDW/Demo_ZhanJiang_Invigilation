package com.tecsun.jc.base.bean

class SignResultEntity {

    var statusCode: String? = null
    var message: String? = null
    var data: String? = null

    fun isSuccess(): Boolean {
        return statusCode?.equals("200") ?: false
    }
}