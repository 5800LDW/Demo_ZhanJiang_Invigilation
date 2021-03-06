package com.tecsun.jc.demo.invigilation.zhanjiang.builder

import com.lzy.okgo.OkGo
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.listener.IEvents
import com.tecsun.jc.base.listener.IEvents2
import com.tecsun.jc.base.listener.OkGoRequestCallback
import com.tecsun.jc.base.utils.OkGoManager
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.demo.invigilation.zhanjiang.bean.DictionariesInfoEntity
import com.tecsun.jc.demo.invigilation.zhanjiang.bean.ListBean
import com.tecsun.jc.demo.invigilation.zhanjiang.constant.Constants
import com.tecsun.jc.demo.invigilation.zhanjiang.constant.Constants.URL_DICTIONARIES
import java.util.HashMap

object DictionariesInfoBuilder {

    private val TAG = DictionariesInfoBuilder::class.java.simpleName

    //http://61.28.113.182:6726/sisp/apply/getDicByPid/CERT_TYPE

    /**证件类型，数据字典编码：CERT_TYPE*/
    val data_CERT_TYPE = HashMap<String, String>()

    /**文化程度，数据字典编码：EDU*/
    val data_EDU = HashMap<String, String>()

    /**申报职业级别，数据字典编码：APPLY_LEVEL*/
    val data_APPLY_LEVEL = HashMap<String, String>()

    /**考核科目，数据字典编码：APPLY_SUBJECT*/
    val data_APPLY_SUBJECT = HashMap<String, String>()

    /** 现有技术等级、职业资格证书,数据字典编码：TELV */
    val data_TELV = HashMap<String, String>()

    /** 目前状态,数据字典编码：TRAIN_JOB_STATUS */
    val data_TRAIN_JOB_STATUS = HashMap<String, String>()

    /** 报名参加培训级别,数据字典编码：TELV_TYPE */
    val data_TELV_TYPE = HashMap<String, String>()


    fun getAll() {
        if (data_CERT_TYPE.size == 0) {
            getCERT_TYPE(null)
        }
        if (data_EDU.size == 0) {
            getEDU(null)
        }
        if (data_APPLY_LEVEL.size == 0) {
            getAPPLY_LEVEL(null)
        }
        if (data_APPLY_SUBJECT.size == 0) {
            getAPPLY_SUBJECT(null)
        }
        if (data_TELV.size == 0) {
            getTELV(null)
        }
        if (data_TRAIN_JOB_STATUS.size == 0) {
            getTRAIN_JOB_STATUS(null)
        }
        if (data_TELV_TYPE.size == 0) {
            getTELV_TYPE(null)
        }
    }


    fun getCERT_TYPE(biz2: IEvents2?) {
        OkGoManager.instance.okGoRequestManageForGet(
            URL_DICTIONARIES + "CERT_TYPE",
            DictionariesInfoEntity::class.java,
            object : OkGoRequestCallback<DictionariesInfoEntity> {
                override fun onSuccess(t: DictionariesInfoEntity) {
                    LogUtil.e(TAG, ">>>>>>>>>>> CERT_TYPE $t")
                    if (t != null && t.isSuccess) {
                        var l = t.data;
                        if (l != null && l.size > 0) {
                            for (item in l) {
                                data_CERT_TYPE[item.codedesc] = item.code
                            }
                            biz2?.biz()
                        }
                    } else {
                        biz2?.failBiz(t.message ?: "")
                    }

                }

                override fun onError(throwable: Throwable?) {
                    LogUtil.e(TAG, ">>>>>>>>>>> CERT_TYPE $throwable")
                    biz2?.failBiz(throwable.toString() ?: "")
                }
            })
    }

    fun getEDU(biz2: IEvents2?) {
        OkGoManager.instance.okGoRequestManageForGet(
            URL_DICTIONARIES + "EDU",
            DictionariesInfoEntity::class.java,
            object : OkGoRequestCallback<DictionariesInfoEntity> {
                override fun onSuccess(t: DictionariesInfoEntity) {
                    LogUtil.e(TAG, ">>>>>>>>>>> EDU $t")
                    if (t != null && t.isSuccess) {
                        var l = t.data;
                        if (l != null && l.size > 0) {
                            for (item in l) {
                                data_EDU[item.codedesc] = item.code
                            }
                            biz2?.biz()
                        }
                    } else {
                        biz2?.failBiz(t.message ?: "")
                    }
                }

                override fun onError(throwable: Throwable?) {
                    LogUtil.e(TAG, ">>>>>>>>>>> EDU $throwable")
                    biz2?.failBiz(throwable.toString() ?: "")
                }
            })
    }

    fun getAPPLY_LEVEL(biz2: IEvents2?) {
        OkGoManager.instance.okGoRequestManageForGet(
            URL_DICTIONARIES + "APPLY_LEVEL",
            DictionariesInfoEntity::class.java,
            object : OkGoRequestCallback<DictionariesInfoEntity> {
                override fun onSuccess(t: DictionariesInfoEntity) {
                    LogUtil.e(TAG, ">>>>>>>>>>> APPLY_LEVEL $t")
                    if (t != null && t.isSuccess) {
                        var l = t.data;
                        if (l != null && l.size > 0) {
                            for (item in l) {
                                data_APPLY_LEVEL[item.codedesc] = item.code
                            }
                            biz2?.biz()
                        }
                    } else {
                        biz2?.failBiz(t.message ?: "")
                    }
                }

                override fun onError(throwable: Throwable?) {
                    LogUtil.e(TAG, ">>>>>>>>>>> APPLY_LEVEL $throwable")
                    biz2?.failBiz(throwable.toString() ?: "")
                }
            })
    }


    fun getAPPLY_SUBJECT(biz2: IEvents2?) {
        OkGoManager.instance.okGoRequestManageForGet(
            URL_DICTIONARIES + "APPLY_SUBJECT",
            DictionariesInfoEntity::class.java,
            object : OkGoRequestCallback<DictionariesInfoEntity> {
                override fun onSuccess(t: DictionariesInfoEntity) {
                    LogUtil.e(TAG, ">>>>>>>>>>> APPLY_SUBJECT $t")
                    if (t != null && t.isSuccess) {
                        var l = t.data;
                        if (l != null && l.size > 0) {
                            for (item in l) {
                                data_APPLY_SUBJECT[item.codedesc] = item.code
                            }
                            biz2?.biz()
                        }
                    } else {
                        biz2?.failBiz(t.message ?: "")
                    }
                }

                override fun onError(throwable: Throwable?) {
                    LogUtil.e(TAG, ">>>>>>>>>>> APPLY_SUBJECT $throwable")
                    biz2?.failBiz(throwable.toString() ?: "")
                }
            })
    }

    fun getTELV(biz2: IEvents2?) {
        OkGoManager.instance.okGoRequestManageForGet(
            URL_DICTIONARIES + "TELV",
            DictionariesInfoEntity::class.java,
            object : OkGoRequestCallback<DictionariesInfoEntity> {
                override fun onSuccess(t: DictionariesInfoEntity) {
                    LogUtil.e(TAG, ">>>>>>>>>>> TELV $t")
                    if (t != null && t.isSuccess) {
                        var l = t.data;
                        if (l != null && l.size > 0) {
                            for (item in l) {
                                data_TELV[item.codedesc] = item.code
                            }
                            biz2?.biz()
                        }
                    } else {
                        biz2?.failBiz(t.message ?: "")
                    }
                }

                override fun onError(throwable: Throwable?) {
                    LogUtil.e(TAG, ">>>>>>>>>>> TELV $throwable")
                    biz2?.failBiz(throwable.toString() ?: "")
                }
            })
    }

    fun getTRAIN_JOB_STATUS(biz2: IEvents2?) {
        OkGoManager.instance.okGoRequestManageForGet(
            URL_DICTIONARIES + "TRAIN_JOB_STATUS",
            DictionariesInfoEntity::class.java,
            object : OkGoRequestCallback<DictionariesInfoEntity> {
                override fun onSuccess(t: DictionariesInfoEntity) {
                    LogUtil.e(TAG, ">>>>>>>>>>> TRAIN_JOB_STATUS $t")
                    if (t != null && t.isSuccess) {
                        var l = t.data;
                        if (l != null && l.size > 0) {
                            for (item in l) {
                                data_TRAIN_JOB_STATUS[item.codedesc] = item.code
                            }
                            biz2?.biz()
                        }
                    } else {
                        biz2?.failBiz(t.message ?: "")
                    }
                }

                override fun onError(throwable: Throwable?) {
                    LogUtil.e(TAG, ">>>>>>>>>>> TRAIN_JOB_STATUS $throwable")
                    biz2?.failBiz(throwable.toString() ?: "")
                }
            })
    }

    fun getTELV_TYPE(biz2: IEvents2?) {
        OkGoManager.instance.okGoRequestManageForGet(
            URL_DICTIONARIES + "TELV_TYPE",
            DictionariesInfoEntity::class.java,
            object : OkGoRequestCallback<DictionariesInfoEntity> {
                override fun onSuccess(t: DictionariesInfoEntity) {
                    LogUtil.e(TAG, ">>>>>>>>>>> TELV_TYPE $t")
                    if (t != null && t.isSuccess) {
                        var l = t.data;
                        if (l != null && l.size > 0) {
                            for (item in l) {
                                data_TELV_TYPE[item.codedesc] = item.code
                            }
                            biz2?.biz()
                        }
                    } else {
                        biz2?.failBiz(t.message ?: "")
                    }
                }

                override fun onError(throwable: Throwable?) {
                    LogUtil.e(TAG, ">>>>>>>>>>> TELV_TYPE $throwable")
                    biz2?.failBiz(throwable.toString() ?: "")
                }
            })
    }
}




































