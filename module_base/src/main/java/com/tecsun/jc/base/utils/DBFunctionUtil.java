package com.tecsun.jc.base.utils;

import android.text.TextUtils;

import com.tecsun.jc.base.bean.db.GovernmentNoticeInfo;
import com.tecsun.jc.base.bean.db.InfoNoticeInfo;
import com.tecsun.jc.base.bean.db.invigilation.bean.ProctorDetailsBean;
import com.tecsun.jc.base.bean.db.invigilation.bean.ReadCardInfoBean;
import com.tecsun.jc.base.bean.db.invigilation.bean.StudentDetailsBean;
import com.tecsun.jc.base.bean.db.invigilation.bean.TestingDetailsBean;
import com.tecsun.jc.base.utils.log.LogUtil;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

/**
 * 数据库处理类
 *
 * @author liudongwen
 * @date 07/08/2019.
 */
public class DBFunctionUtil {

    private static String TAG = "DBFunctionUtil";


    public static int count(Class<?> modelClass) {
        return LitePal.count(modelClass);
    }

    public static boolean save(Object obj) {
        if (obj != null && obj instanceof LitePalSupport) {
            return ((LitePalSupport) obj).save();
        } else {
            return false;
        }
    }

    public static boolean saveGovernmentNoticeInfoID(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        } else {

            GovernmentNoticeInfo obj = new GovernmentNoticeInfo();
            obj.setNoticeID(str);

            if (obj != null && obj instanceof LitePalSupport) {
                return obj.save();
            } else {
                return false;
            }
        }
    }

    public static int findGovernmentNoticeInfoIDCount(String id) {
        return LitePal.where("noticeID" + " = ?", id).count(GovernmentNoticeInfo.class);
    }


    public static boolean saveInfoNoticeInfoID(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        } else {
            InfoNoticeInfo obj = new InfoNoticeInfo();
            obj.setNoticeID(str);

            if (obj != null && obj instanceof LitePalSupport) {
                return obj.save();
            } else {
                return false;
            }
        }
    }

    public static int findInfoNoticeInfoIDCount(String id) {
        return LitePal.where("noticeID" + " = ?", id).count(InfoNoticeInfo.class);
    }

    public static void deleteAll() {
        int i = LitePal.deleteAll(GovernmentNoticeInfo.class);
        LogUtil.e(">>>>>>>>>>>>>>>>>> DBFunctionUtil GovernmentNoticeInfo");
        LogUtil.e(i);
        int ii = LitePal.deleteAll(InfoNoticeInfo.class);
        LogUtil.e(">>>>>>>>>>>>>>>>>> DBFunctionUtil InfoNoticeInfo");
        LogUtil.e(ii);

        LitePal.deleteAll(ProctorDetailsBean.class);
        LitePal.deleteAll(StudentDetailsBean.class);
        LitePal.deleteAll(TestingDetailsBean.class);
        LitePal.deleteAll(ReadCardInfoBean.class);
    }


    public static TestingDetailsBean findFirstTestingRoom() {
        return LitePal.findFirst(TestingDetailsBean.class);
    }

    public static int deleteTestingDetails(String schoolName) {
        return LitePal.deleteAll(TestingDetailsBean.class, "schoolName = ? ", schoolName);
    }

    public static List<TestingDetailsBean> findAllTestingRoom() {
        return LitePal.findAll(TestingDetailsBean.class);
    }

    public static int countTestingRoom(String schoolName) {
        return LitePal.where("schoolName" + " = ?", schoolName).count(TestingDetailsBean.class);
    }


    public static List<ReadCardInfoBean> findAllReadCardInfoBean() {
        return LitePal.findAll(ReadCardInfoBean.class);
    }

    public static int findReadCardInfoBeanCountBySFZH(String sfzh) {
        return LitePal.where("number" + " = ?", sfzh).count(ReadCardInfoBean.class);
    }


    public static int findExaminerCount(String sfzh) {
        int count = LitePal.where("sfzh = ? ", sfzh).count(ProctorDetailsBean.class);
        return count;
    }


    /***student***/
    public static int deleteAllStudent(List<StudentDetailsBean> list) {
//        int deleteTotalNum = 0;
//        if (list != null && list.size() > 0) {
//            int sizeInt = list.size();
//            for (int i = 0; i < sizeInt; i++) {
//                StudentDetailsBean  bean = list.get(i);
//                if(bean!=null && bean.isSaved()){
//                    LogUtil.e(">>>>>>>>>>>>>>>>>> StudentDetailsBean 存在并被删除了");
//                    bean.delete();
//                    deleteTotalNum++;
//                }
//            }
//        }
//        return deleteTotalNum;

        int deleteTotalNum = 0;
        if (list != null && list.size() > 0) {
            int sizeInt = list.size();
            for (int i = 0; i < sizeInt; i++) {
                StudentDetailsBean bean = list.get(i);
                if (bean != null && bean.getSfzh() != null) {
                    int num = deleteStudent(bean.getSfzh());
                    if (num != 0) {
                        LogUtil.e(">>>>>>>>>>>>>>>>>> StudentDetailsBean 存在并被删除了 : " + bean.getSfzh());
                        deleteTotalNum++;
                    }
                }
            }
        }
        return deleteTotalNum;
    }

    /***student***/
    public static void saveAllStudent(List<StudentDetailsBean> list) {
        LitePal.saveAll(list);
    }

    private static int deleteStudent(String sfzh) {
        return LitePal.deleteAll(StudentDetailsBean.class, "sfzh = ? ", sfzh);
    }

    public static int countStudentBySchoolName(String schoolName) {
        return LitePal.where("schoolName" + " = ?", schoolName).count(StudentDetailsBean.class);
    }

}




