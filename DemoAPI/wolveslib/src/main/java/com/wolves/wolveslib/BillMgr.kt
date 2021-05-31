package com.wolves.wolveslib

import androidx.appcompat.app.AppCompatActivity
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import com.tjbaobao.gitee.billing.GoogleBillingUtil
import com.tjbaobao.gitee.billing.OnGoogleBillingListener
import java.util.*

object BillMgr {
    lateinit var googleBillingUtil: GoogleBillingUtil
    lateinit var mContext: AppCompatActivity

    fun setSkus (context: AppCompatActivity, items : Array<String>){
        mContext = context
        //----------------------------------------------
        // 設定儲值相關的API
        //----------------------------------------------
        // 設定購買相關物品
        GoogleBillingUtil.isDebug(true)
        // 設定商品列表
        GoogleBillingUtil.setSkus(items, arrayOf())
        // 設定要處理的 class
        var bl = OnMyGoogleBillingListener()
        googleBillingUtil = GoogleBillingUtil.getInstance()
            .addOnGoogleBillingListener(context, bl)
            .build(context)
    }

    /**
     * 檢查是否有有效訂閱
     */
    fun checkSubs(){
        when(val size = googleBillingUtil.getPurchasesSizeSubs(this.mContext)){
            0->{
                // 不具備有效訂閱
                println("有效訂閱數:0(無有效訂閱)")
            }
            -1->{
                // 查詢失敗
                println("有效訂閱數:-1(查詢失敗)")
            }
            else->{
                // 具有有效訂閱
                println("有效訂閱數:$size(具備有效訂閱)")
            }
        }
    }

    // 用來處理的 class
    private class OnMyGoogleBillingListener : OnGoogleBillingListener()
    {
        // 內購服務初始化成功
        override fun onSetupSuccess(isSelf: Boolean) {
            println ("內購服務初始化完成")
            println (isSelf)
            checkSubs()
        }

        override fun onQuerySuccess(skuType: String, list: MutableList<SkuDetails>, isSelf: Boolean) {
            if(!isSelf) return
            val tempBuffer = StringBuffer()
            tempBuffer.append("查詢商品資訊成功($skuType):\n")
            for((i, skuDetails) in list.withIndex()){
                val details = String.format(
                    Locale.getDefault(),"%s , %s",
                    skuDetails.sku,skuDetails.price
                )
                tempBuffer.append(details)
                if(i!=list.size-1){
                    tempBuffer.append("\n")
                }
            }
            println(tempBuffer.toString())
        }

        //  購買成功的 callback
        private var signature : String = ""
        private var originalJson : String = ""
        override fun onPurchaseSuccess(purchase: Purchase, isSelf: Boolean)
        {
            val tempBuffer = StringBuffer()
            val details = String.format(Locale.getDefault(),"購買商品成功:%s", purchase.sku)
            this.signature = purchase.signature
            this.originalJson = purchase.originalJson
            tempBuffer.append(details)
            println(tempBuffer.toString())
        }

        // 物品要消耗才能繼續購買。
        override fun onConsumeSuccess(purchaseToken: String, isSelf: Boolean) {
            //log("[0] 消耗商品成功:$purchaseToken")
            //tmpFun (purchaseToken)
            //AndroidAPI.mDepositeFinish(purchaseToken)
        }

        // 交易失敗的 callback
        override fun onFail(tag: GoogleBillingUtil.GoogleBillingListenerTag, responseCode: Int, isSelf: Boolean) {
            println ("操作失敗:tag=${tag.name},responseCode=$responseCode")
        }

        // 發生錯誤的 callback
        override fun onError(tag: GoogleBillingUtil.GoogleBillingListenerTag, isSelf: Boolean) {
            println("發生錯誤:tag=${tag.name}")
        }
    }

}