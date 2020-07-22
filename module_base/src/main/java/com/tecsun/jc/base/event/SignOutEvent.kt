package com.tecsun.jc.base.event

data class SignOutEvent(var isSignOut: Boolean)

data class GetMessage(var size: Int)

data class NetWorkChangeEvent(var netWorkType: Int)

data class HotInfoEvent(var id: String)


data class UpdatePicAndNOEvent(var id: String)


data class UpdateGovernAndMessageInfo(var id: String)