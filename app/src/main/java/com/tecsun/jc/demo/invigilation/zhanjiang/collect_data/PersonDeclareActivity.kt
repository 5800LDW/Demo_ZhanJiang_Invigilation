package com.tecsun.jc.demo.invigilation.zhanjiang.collect_data

import com.alibaba.android.arouter.facade.annotation.Route
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.demo.invigilation.R

/**
 * 个人申报
 */
@Route(path = RouterHub.ROUTER_APP_PERSON_DECLARE)
class PersonDeclareActivity: BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.app_activity_person_declare
    }
}