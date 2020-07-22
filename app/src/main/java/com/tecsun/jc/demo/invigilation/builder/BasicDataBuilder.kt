package com.tecsun.jc.demo.invigilation.builder

import android.graphics.BitmapFactory
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.bean.db.invigilation.bean.ProctorDetailsBean
import com.tecsun.jc.base.bean.db.invigilation.bean.StudentDetailsBean
import com.tecsun.jc.base.bean.db.invigilation.bean.TestingDetailsBean
import com.tecsun.jc.base.bean.filter.IFilter
import com.tecsun.jc.base.builder.StudentOwnImageBuilder
import com.tecsun.jc.base.builder.TestingRoomBuilder
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.demo.invigilation.R
import com.tecsun.jc.register.util.constant.Const
import com.tecsun.jc.register.util.constant.Const.ROOM_1
import com.tecsun.jc.register.util.constant.Const.ROOM_2
import org.litepal.LitePal
import java.util.*

/**
 * 基础数据
 *
 * @author liudongwen
 * @date 2019/10/10
 */
object BasicDataBuilder {
    /**考场*/
    private val examinationRoomInfoList: LinkedList<IFilter> = LinkedList()
    /**监考官*/
    private val examinerInfoList: LinkedList<IFilter> = LinkedList()
    private val studentInfoList1: LinkedList<StudentDetailsBean> = LinkedList()
    private val studentInfoList2: LinkedList<StudentDetailsBean> = LinkedList()
    private val normalSFZH = "510321198605202874"
    private val normalName = "胡云熙"
    /***拿到考点信息*/
    fun getExaminationRoom(): LinkedList<IFilter> {
        examinationRoomInfoList.clear()

        var bean1 = TestingDetailsBean()
        bean1.schoolName = Const.ROOM_1
        bean1.address = "南二环东路20号河北师大新校区"
        bean1.buildingName = "文科楼A座外国语学院二层205房间"

        var bean2 = TestingDetailsBean()
        bean2.schoolName = Const.ROOM_2
        bean2.address = "石家庄市珠江大道288号"
        bean2.buildingName = "A座四层601房间"
////        var bean3 = TestingDetailsBean()
////        bean3.schoolName = "沧州医学高等专科学校"
////        bean3.address = "石家庄红旗大街南端学院路6号"
////        bean3.buildingName = "科研楼二层201室"
//
//        examinationRoomInfoList.add(bean1)
//        examinationRoomInfoList.add(bean2)
//        examinationRoomInfoList.add(bean3)
        var list = TestingRoomBuilder.getAllRooms()
        if (!list.isNullOrEmpty()) {
            examinationRoomInfoList.addAll(list)
        }
        var nameHash = TestingRoomBuilder.getAllRoomsName()
        if (!nameHash.contains(ROOM_1)) {
            examinationRoomInfoList.add(bean1)
        }
        if (!nameHash.contains(ROOM_2)) {
            examinationRoomInfoList.add(bean2)
        }
        return examinationRoomInfoList
    }

    fun getExaminerInfo(): LinkedList<IFilter> {
//        var bean1 = ProctorDetailsBean()
//        bean1.name = "范玑平"
//        bean1.sfzh = "513436200010081121"
//
//        var bean2 = ProctorDetailsBean()
//        bean2.name = "汪勇蕾"
//        bean2.sfzh = "513436200010082204"
//
//        var bean3 = ProctorDetailsBean()
//        bean3.name = "汪壬"
//        bean3.sfzh = "513436200010081586"
//
//        examinerInfoList.add(bean1)
//        examinerInfoList.add(bean2)
//        examinerInfoList.add(bean3)

        examinerInfoList.clear()
        var list = LitePal.findAll(ProctorDetailsBean::class.java)
        if (list != null && list.size != 0) {
            examinerInfoList.addAll(list)
        }
        var count = LitePal.where("sfzh = ? ", normalSFZH).count(ProctorDetailsBean::class.java)
        if (count == 0) {
            var bean1 = ProctorDetailsBean()
            bean1.name = normalName
            bean1.sfzh = normalSFZH
            examinerInfoList.add(bean1)
        }
        return examinerInfoList
    }

//     private fun getStudentInfo() {
//        if (true) {
//            var r1List =
//                LitePal.where("schoolName = ? ", ROOM_1).find(StudentDetailsBean::class.java)
//            r1List?.let {
//                studentInfoList1.addAll(r1List)
//            }
//
//            //考点1
//            for (index in 1..10) {
//                var bean = StudentDetailsBean()
//                bean.schoolName = ROOM_1
//                bean.sfzh = "513436200010089625$index"
//                bean.name = "康承$index"
//                studentInfoList1.add(bean)
//            }
//        }
//        if (true) {
//            var r2List =
//                LitePal.where("schoolName = ? ", ROOM_2).find(StudentDetailsBean::class.java)
//            r2List?.let {
//                studentInfoList2.addAll(r2List)
//                for (item in r2List) {
//                    LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>> r2List :$item")
//                }
//            }
//            for (index in 1..10) {
//                var bean = StudentDetailsBean()
//                bean.schoolName = ROOM_2
//                bean.name = "丁慧锋$index"
//                bean.sfzh = "652801198101012772$index"
//                studentInfoList2.add(bean)
//            }
//        }
//    }

    fun getStudentInfoList1(): LinkedList<StudentDetailsBean> {
        studentInfoList1.clear()
        if (true) {
            var r1List =
                LitePal.where("schoolName = ? ", ROOM_1).find(StudentDetailsBean::class.java)
            r1List?.let {
                studentInfoList1.addAll(r1List)
            }
//            if(studentInfoList1.size < 10){
            //考点1
//                for (index in 1..10) {
//                    var bean = StudentDetailsBean()
//                    bean.schoolName = ROOM_1
//                    bean.sfzh = "513436200010089625$index"
//                    bean.name = "康承$index"
//                    studentInfoList1.add(bean)
//                }
//            }
        }
        return studentInfoList1
    }

    fun getStudentInfoList2(): LinkedList<StudentDetailsBean> {
        studentInfoList2.clear()
        if (true) {
            var r2List =
                LitePal.where("schoolName = ? ", ROOM_2).find(StudentDetailsBean::class.java)
            r2List?.let {
                studentInfoList2.addAll(r2List)
                for (item in r2List) {
                    LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>> r2List :$item")
                }
            }

//            if(studentInfoList2.size < 10){
//                for (index in 1..10) {
//                    var bean = StudentDetailsBean()
//                    bean.schoolName = ROOM_2
//                    bean.name = "丁慧锋$index"
//                    bean.sfzh = "652801198101012772$index"
//                    studentInfoList2.add(bean)
//                }
//            }
        }
        return studentInfoList2
    }

    fun getStudentInfoListOther(schoolName: String):List<StudentDetailsBean>? {
        var r1List = LitePal.where("schoolName = ? ", schoolName).find(StudentDetailsBean::class.java)
        return r1List as List<StudentDetailsBean>
    }


    fun addStudentNormalData() {
        var bean = LitePal.where("sfzh = ? ", normalSFZH).findFirst(StudentDetailsBean::class.java)
        if (bean == null) {
            var bean = StudentDetailsBean()
//            bean.schoolName = ROOM_2
            bean.name = normalName
            bean.sfzh = normalSFZH
            bean.schoolName = ROOM_1
            bean.save()
            var bitmap = BitmapFactory.decodeResource(
                JinLinApp.context!!.getResources(),
                R.raw.hu_yun_xi
            )
            StudentOwnImageBuilder.savePic(bitmap, normalSFZH)
            var list = LinkedList<StudentDetailsBean>()
//            for (index in 1..10) {
//                var bean = StudentDetailsBean()
//                bean.schoolName = ROOM_1
//                bean.sfzh = "513436200010089625$index"
//                bean.name = "康承$index"
//                list.add(bean)
//            }
//
//            for (index in 1..10) {
//                var bean = StudentDetailsBean()
//                bean.schoolName = ROOM_2
//                bean.name = "丁慧锋$index"
//                bean.sfzh = "652801198101012772$index"
//                list.add(bean)
//            }
            LitePal.saveAll(list)
        }
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>. 胡云熙:$bean")
    }
}





