// DESC : 這邊是存放和 Android 相關的 API 功能
// Author : nick.shih
package com.wolves.wolveslib

import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import android.widget.Toast
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object AndroidAPI {
    // 顯示字串
    fun showMsg(msg: String) {
        println ("[showMsg] $msg")
        //Toast.makeText(ShareData.mContext, msg, Toast.LENGTH_LONG).show();
    }

}
