package com.tecsun.demo.import_data.ui

import android.content.DialogInterface
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.module_importdata.R
import com.tecsun.demo.import_data.util.ExcelUtil
import com.tecsun.jc.base.base.BaseActivity
import com.tecsun.jc.base.bean.db.invigilation.bean.StudentDetailsBean
import com.tecsun.jc.base.builder.TestingRoomBuilder
import com.tecsun.jc.base.common.RouterHub
import com.tecsun.jc.base.utils.DBFunctionUtil
import com.tecsun.jc.base.utils.file.FileUtil
import com.tecsun.jc.base.utils.log.LogUtil
import com.tecsun.jc.base.widget.SingleClickListener
import com.tecsun.jc.base.widget.TitleBar
import kotlinx.android.synthetic.main.import_activity_import_data.*
import java.io.File
import java.io.IOException

/**
 * 导入数据的Activity
 * @author liuDongWen
 * @date 2019/11/26
 */
@Route(path = RouterHub.ROUTER_LIB_IMPORT_DATA)
class ImportDtaActivity : BaseActivity() {

    private var FOLDER_NAME = "学生信息"
    private var FOLDER_PATH =
        Environment.getExternalStorageDirectory().toString() + File.separator + FOLDER_NAME
    private var activity: ImportDtaActivity? = null

    override fun getLayoutId(): Int {
        return R.layout.import_activity_import_data
    }

    override fun setTitleBar(titleBar: TitleBar) {
        titleBar.setTitle(R.string.base_batch_addition_of_students)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this

        btImport.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View?) {
                var files = ArrayList<File>()
                var fileList1 = checkFileListForXLSX()
                var fileList2 = checkFileListForXLS()
                if (!fileList1.isNullOrEmpty()) {
                    files.addAll(fileList1)
                }
                if (!fileList2.isNullOrEmpty()) {
                    files.addAll(fileList2)
                }
                //显示单选Dialog
                val items = arrayOfNulls<String>(files.size)
                for (i in files.indices) {
                    items[i] = files.get(i).name
                    LogUtil.e(files.get(i).getName() + "  >>>>>>>>>>>>>>>>>>>>   " + files.get(i).getAbsoluteFile())
                    LogUtil.e(files.get(i).getName() + "  >>>>>>>>>>>>>>>>>>>>   " + files.get(i).getAbsolutePath())
                }

                if (items.isNullOrEmpty()) {
                    showToast("文件夹\"学生信息\"不存在或没有存放文件")
                    return
                }
                showSingleChoiceDialog(items)
                LogUtil.e("TAG", FOLDER_PATH)
            }
        })


    }

    private fun checkFileListForXLSX(): ArrayList<File> {
        var files = ArrayList<File>()
//        try {
//            FileUtil.getSuffixFile(files, FOLDER_PATH, ".xlsx")
//        } catch (e: Exception) {
//            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>checkFileListForXLSX $e")
//        }
        return files
    }

    private fun checkFileListForXLS(): ArrayList<File> {
        var files = ArrayList<File>()
        try {
            FileUtil.getSuffixFile(files, FOLDER_PATH, ".xls")
        } catch (e: Exception) {
            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>checkFileListForXLS $e")
        }
        return files
    }

    private fun showSingleChoiceDialog(items: Array<String?>) {

        var yourChoice = 0
        val singleChoiceDialog = AlertDialog.Builder(activity!!)
        singleChoiceDialog.setTitle("请选择要导入的文件!")
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(
            items, 0
        ) { _, which -> yourChoice = which }
        singleChoiceDialog.setPositiveButton("确定",
            DialogInterface.OnClickListener { _, _ ->
                if (yourChoice != -1) {
                    val file = File(FOLDER_PATH + File.separator + items?.get(yourChoice))

                    LogUtil.e("TAG", "yourChoice=$yourChoice")
                    LogUtil.e("TAG", "fileName= $file")

                    try {
                        import(file.toString())
                    } catch (e: IOException) {
                        LogUtil.e(">>>>>>>>>>>>>>>>>>>>> 2 ImportDtaActivity $e")
                        return@OnClickListener
                    }
                }
            })
            .setNegativeButton("取消") { _, _ ->
                finish()
            }
        singleChoiceDialog.show()
    }

//    private fun import(fileName:String){
//
//        try{
//            Thread{
////                val totalLine =  ExcelUtils2.getRowTotalNumber(fileName,0)
//                val totalLine =  5
//                LogUtil.e(">>>>>>>>>>>>>>>>>> totalLine $totalLine")
//                //行
//                var line = 0
//                //列
//                var row = 0
//
//                val studentList = ArrayList<StudentDetailsBean>()
//
//                while (line <= totalLine) {
//
//                    var bean = StudentDetailsBean()
//                    for (i in 1..7) {
//                        var cellContent = ExcelUtils2.getCellContent(fileName, 0, i, row)?:""
//                        LogUtil.e(">>>>>>>>>>>>>>>>>> cellContent $cellContent")
//                        when (i) {
//                            1 -> bean.name = cellContent
//                            2 -> bean.sfzh = cellContent
//                            3 -> bean.schoolName = cellContent
//                            4 -> bean.numberOfExams = cellContent
//                            5 -> bean.seatNumber = cellContent
//                            6 -> bean.examinationNO = cellContent
//                            7 -> bean.subject = cellContent
//                        }
//                    }
//                    studentList.add(bean)
//                    LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
//                    line++
//                }
//            }.start()
//        }catch (e:Exception){
//            LogUtil.e(">>>>>>>>>>>>>>>>>> $e")
//        }
//    }

    private fun import(fileName: String) {
        showLoadingDialog(tipContent = "正在处理...")
        LogUtil.e(">>>>>>>>>>>>>>>>> fileName :$fileName")
        try {
            Thread {
                val studentList = ArrayList<StudentDetailsBean>()
                val list = ExcelUtil.read(fileName)
                if (list.isNullOrEmpty()) {
                    closeDialog()
                    runOnUiThread {
                        showErrorMessageDialog2("文件读取失败!")
                    }
                    return@Thread
                }
                for ((index, item) in list.withIndex()) {
                    if (index != 0) {
                        var bean = StudentDetailsBean()
                        for ((index2, item2) in item.withIndex()) {
                            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>> $item2")
                            var content = item2?.toString() ?: ""
                            with(bean) {
                                when (index2) {
                                    0 -> name = content
                                    1 -> sfzh = content
                                    2 -> schoolName = content
                                    3 -> numberOfExams = content
                                    4 -> seatNumber = content
                                    5 -> examinationNO = content
                                    6 -> subject = content
                                }
                            }
                        }

//                        for ((index2, item2) in item.withIndex()) {
//                            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>> $item2")
//                            when (index2) {
//                                0 -> bean.name = item2?.toString() ?: ""
//                                1 -> bean.sfzh = item2?.toString() ?: ""
//                                2 -> bean.schoolName = item2?.toString() ?: ""
//                                3 -> bean.numberOfExams = item2?.toString() ?: ""
//                                4 -> bean.seatNumber = item2?.toString() ?: ""
//                                5 -> bean.examinationNO = item2?.toString() ?: ""
//                                6 -> bean.subject = item2?.toString() ?: ""
//                            }
//                        }
                        studentList.add(bean)
                        LogUtil.e(">>>>>>>>>>>>>>>>>> 下面打印下一行 >>>>>>>>>>>>>>>>>>>")
                    }
                }
                DBFunctionUtil.deleteAllStudent(studentList)
                //保存到本地:
                DBFunctionUtil.saveAllStudent(studentList)
                closeDialog()
                TestingRoomBuilder.saveAllRoom(studentList)
                runOnUiThread {
                    showSuccessMessageDialog("导入成功!")
                }
            }.start()
        } catch (e: Exception) {
            LogUtil.e(">>>>>>>>>>>>>>>>>> 报错了$e")
            closeDialog()
            runOnUiThread {
                showErrorMessageDialog2("导入失败!\n错误信息:${e?.message ?: ""}")
            }
        }
    }

    private fun closeDialog() {
        runOnUiThread {
            dismissLoadingDialog()
        }
    }
}

























































