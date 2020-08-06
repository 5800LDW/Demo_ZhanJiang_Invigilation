package com.tecsun.jc.base.builder

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.Settings
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.R
import com.tecsun.jc.base.listener.IEvents
import com.tecsun.jc.base.utils.ScreenUtil

/**
 * 权限提示说明
 * @author liudongwen
 * @date 2019/10/24
 */
object PermissionTipsShowBuilder {

    private val mHashMap = HashMap<String, String>()

    init {
        mHashMap.put(Manifest.permission.READ_PHONE_STATE, "获取手机信息权限")
        mHashMap.put(Manifest.permission.CALL_PHONE, "获取手机信息权限")
        mHashMap.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "读写手机存储权限")
        mHashMap.put(Manifest.permission.READ_EXTERNAL_STORAGE, "读写手机存储权限")
        mHashMap.put(Manifest.permission.CAMERA, "相机权限")
        mHashMap.put(Manifest.permission.ACCESS_COARSE_LOCATION, "定位权限")
        mHashMap.put(Manifest.permission.ACCESS_FINE_LOCATION, "定位权限")
    }


    /***
     *
     * @param activity
     * @param permissions 被拒绝的权限列表
     * @param leftClick
     * @param rightClick
     */
    fun show(
        activity: Activity,
//        vararg permissions: String,
        permissions: Array<String>,
        leftClick: IEvents = IEvents {},
        rightClick: IEvents = IEvents {}
    ) {
        var contentSB = StringBuffer()
        if (permissions == null || permissions.isEmpty()) {
            return
        } else {
            contentSB.append("请在设置－应用－${JinLinApp.context?.getString(R.string.app_name)}－权限中开启")

            val needShowContent = HashSet<String>()
            for (item in permissions) {
                if (item != null) {
                    var text = mHashMap.get(item)
                    if(text!=null){
                        needShowContent.add(text)
                    }
                }
            }

            var index = 0
            for (text in needShowContent) {
                if (text != null) {
                    if (index != 0) {
                        contentSB.append("、")
                    }
                    contentSB.append(text)
                    index++
                }
            }
        }

        if (activity != null && !activity.isFinishing) {
            var dialog = AppCompatDialog(activity, R.style.Base_Permission_Dialog_Style)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            dialog.setContentView(R.layout.dialog_tips_permission)
            dialog.show()
            val window = dialog.window
//            //去除 Dialog 的默认背景
           window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            //设置 Dialog 弹出后底下的 Activity 不变暗 / 这个函数用来设置 Dialog 周围的颜色。系统默认的是半透明的灰色。值设为0则为完全透明。
////            window.setDimAmount(1f)
//
            window.setGravity(Gravity.CENTER)
            window.decorView.setPadding(0, 0, 0, 0)// 消除边距
            val lp = window.attributes
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT

//            lp.flags =
//                WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//            lp.dimAmount = 0.0f
//
            //设置dialog整个背景的透明度/不透明
            lp.alpha = 1f
            window.attributes = lp
            dialog.setCanceledOnTouchOutside(false)


            var vLeft = dialog.findViewById<View>(R.id.vLeft)
            var vRight = dialog.findViewById<View>(R.id.vRight)
            var tvContent = dialog.findViewById<TextView>(R.id.tvContent)
            tvContent?.text = contentSB.toString()
            vLeft?.setOnClickListener {
                dialog.dismiss()
                leftClick?.biz()
            }
            vRight?.setOnClickListener {
                dialog.dismiss()
                val packageURI = Uri.parse("package:${activity.packageName}")
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI)
                activity.startActivity(intent)
                rightClick?.biz()
            }
        }
    }
}

class DrawLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        val localPaint = Paint()
        localPaint.strokeWidth = ScreenUtil.dip2px(JinLinApp.context, 1f).toFloat()
        localPaint.style = Paint.Style.FILL
        localPaint.color = context.resources.getColor(R.color.c_e8e8e8)

        val localPaint2 = Paint()
        localPaint2.strokeWidth = ScreenUtil.dip2px(JinLinApp.context, 0.5f).toFloat()
        localPaint2.style = Paint.Style.FILL
        localPaint2.color = context.resources.getColor(R.color.c_e8e8e8)

        for (i in 0 until childCount) {
            val cellView = getChildAt(i)
            //每一行最后一个画出横线
            if (i == 0) {
                canvas?.drawLine(
                    cellView.left.toFloat(),
                    cellView.top.toFloat(),
                    cellView.right.toFloat(),
                    cellView.top.toFloat(),
                    localPaint
                )



                canvas?.drawLine(
                    cellView.right.toFloat(),
                    cellView.top.toFloat(),
                    cellView.right.toFloat(),
                    cellView.bottom.toFloat(),
                    localPaint2
                )
            } else if (i == 1) {

                canvas?.drawLine(
                    cellView.left.toFloat(),
                    cellView.top.toFloat(),
                    cellView.right.toFloat(),
                    cellView.top.toFloat(),
                    localPaint
                )
            }
        }
    }
}























