// DESC : 做出彈跳視窗
// DATE : 2021/4/20

package com.wolves.anunapidemo

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow

class cLoginUIPopupWindow (context : Context, builder: ConfirmPopupWindowBuilder? = null) : PopupWindow() {

    // 做初使化的動作
    init {
        // 動態產生彈出視窗
        val inflater = LayoutInflater.from(context)
        this.contentView = inflater.inflate(R.layout.api_login, null) //布局xml
        this.width = LinearLayout.LayoutParams.MATCH_PARENT //父布局减去padding
        this.height = LinearLayout.LayoutParams.MATCH_PARENT
        this.isOutsideTouchable = true //是否可以
        this.isClippingEnabled = false //背景透明化可以铺满全屏
        // 设置最终的背景,也可以通过context.resources.getColor(resId)设置自己的颜色
        // ARGB
        val colorDrawable = ColorDrawable(Color.parseColor("#80808080"))
        this.setBackgroundDrawable(colorDrawable) //设置背景
    }

    //创建ConfirmPopupWindow的一个内部类
    //返回Builder就可以流水线定义
    class ConfirmPopupWindowBuilder(val context: Context) {
        companion object {
            fun init(context: Context): ConfirmPopupWindowBuilder {
                return ConfirmPopupWindowBuilder(context)
            }
        }

        var mCancelListener: () -> Unit = {} //一个不需要参数的无返回值的函数
        var mConfirmListener: () -> Unit = {}

        private val window: cLoginUIPopupWindow = cLoginUIPopupWindow(context, this)

        fun build(): cLoginUIPopupWindow {
            return window
        }
    }

    // 做顯示的動作
    fun show() {
        showAtLocation(contentView, Gravity.CENTER, 0, 0)
    }
}