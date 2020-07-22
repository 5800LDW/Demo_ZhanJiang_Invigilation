package com.tecsun.jc.base.bean

/**
 * Created by psl on 2017/5/19.
 */

class ComparePhotoBean :BaseResultEntity() {
    private val verifyId: String? = null
    private val verifyTime: String? = null

    var accountId: String? = null
    var accountName: String? = null
    var accountPwd: String? = null
    var phone: String? = null
    var sfzh: String? = null
    var sex: String? = null
    var address: String? = null
    var company: String? = null
    var status: String? = null
    var roleCode: String? = null
    var description: String? = null
    var openid: String? = null
    var score: String? = null
    override fun toString(): String {
        return "ComparePhotoBean(verifyId=$verifyId, verifyTime=$verifyTime, accountId=$accountId, accountName=$accountName, accountPwd=$accountPwd, phone=$phone, sfzh=$sfzh, sex=$sex, address=$address, company=$company, status=$status, roleCode=$roleCode, description=$description, openid=$openid, score=$score)"
    }


}
