package com.tecsun.jc.base.bean

/**
 * Created by psl on 2017/5/10.
 */

class PictureBean  :BaseResultEntity() {
    //{"statusCode":"200","message":"上传成功","data":{"picId":5015,"picPath":"c52349e7a46748b0bff3f58dd8b9c2d1.jpg"}}
    var data: Bean? = null
    class Bean {
        var picId: String? = null
        var picPath: String? = null
    }

    override fun toString(): String {
        return "PictureBean(data=$data)"
    }
}




