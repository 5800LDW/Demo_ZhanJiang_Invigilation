package com.tecsun.jc.base.bean

import com.tecsun.jc.base.bean.BaseResultEntity


class MyNewsEnitity : BaseResultEntity() {
    var data: List<Bean>? = null
        class Bean {
            var messageId: String? = null
            var messageType: String? = null
            var messageContent: String? = null
            var messageStatus: String? = null
            var pushTime: String? = null
            var sfzh: String? = null
            var messageTitle: String? = null
        }
}