package com.tecsun.jc.base.common

object CommonApi {

    /**
     * 测试环境URL
     */
//    const val URL_TEST_ENVIRONMENT = "http://125.32.42.243:441"
    const val URL_TEST_ENVIRONMENT = "http://222.162.179.35:80"
    /**
     * 生产环境URL
     */
    const val URL_LIVE_ENVIRONMENT = "http://222.162.179.46:80"


    const val BASE_URL_ADDRESS = URL_LIVE_ENVIRONMENT

    const val ROAD_APP_SIGN = "/sisp/iface/user/checkLogin"

    fun getIpPrefix15(): String {
        return BASE_URL_ADDRESS.substring(0, 15)
    }

}