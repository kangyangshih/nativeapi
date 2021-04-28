//
//  IAPManager.swift
//  IAPDemo
//
//  Created by SHIH-YING PAN on 2020/4/28.
//  Copyright © 2020 SHIH-YING PAN. All rights reserved.
//

import StoreKit


class IAPManager: NSObject, ObservableObject {
    
    static let shared = IAPManager()
    @Published var products = [SKProduct]()
    fileprivate var productRequest: SKProductsRequest!
    
    // 取得商品列表
    func getProductIDs() -> [String] {
        [
            "happy_a_33",
            "happy_a_170",
            "happy_a_330",
            "happy_a_490",
            "happy_a_990",
            "happy_a_1690",
            "happy_a_3290",
            "first_happy_33",
            "first_happy_170",
        ]
    }
    
    func restore() {
        SKPaymentQueue.default().restoreCompletedTransactions()
    }
    
    func getProducts() {
        print ("~~[getProducts]~~")
        let productIds = getProductIDs()
        let productIdsSet = Set(productIds)
        productRequest = SKProductsRequest(productIdentifiers: productIdsSet)
        productRequest.delegate = self
        productRequest.start()
    }
 
    // 使用字串來做購買
    func buyByString (productStr:String){
        self.products.forEach {
            if ($0.productIdentifier == productStr) {
                self.buy (product: $0)
                return
            }
        }
    }
    
    // 使用產品資訊來做購買
    func buy(product: SKProduct) {
        print ("[buy]")
        if SKPaymentQueue.canMakePayments() {
            let payment = SKPayment(product: product)
            SKPaymentQueue.default().add(payment)
        } else {
            print ("buy error!")
            // show error
        }
        print ("buy finishonf ")
       
    }
}

extension IAPManager: SKProductsRequestDelegate {
    func productsRequest(_ request: SKProductsRequest, didReceive response: SKProductsResponse) {
        print ("[productsRequest]")
        // 把每一個品項都印出來看看
        response.products.forEach {
            print ("-------")
            // 名稱，價格，描述
            print($0.localizedTitle, $0.price, $0.localizedDescription)
            // 其他程式相關的
            print($0.productIdentifier)
        }
        DispatchQueue.main.async {
            // 記錄可以買的商品
            self.products = response.products
        }
    }
    
}
extension IAPManager: SKPaymentTransactionObserver {
    // 購買成功的回傳結果
    func paymentQueue(_ queue: SKPaymentQueue, updatedTransactions transactions: [SKPaymentTransaction]) {
        print ("[paymentQueue]")
        transactions.forEach {
            // 依照結果來做處理
            switch $0.transactionState {
            case .purchasing:
                print ("purchasing")
            case .purchased:
                print ("~~~~~")
                // happy_a_3290 1
                print($0.payment.productIdentifier, $0.transactionState.rawValue)
                print ($0.transactionIdentifier as Any)
                print ($0.payment.applicationUsername as Any )
                if let appStoreReceiptURL = Bundle.main.appStoreReceiptURL,
                    FileManager.default.fileExists(atPath: appStoreReceiptURL.path) {
                    do {
                        let receiptData = try Data(contentsOf: appStoreReceiptURL, options: .alwaysMapped)
                        print(receiptData)
                        let receiptString = receiptData.base64EncodedString(options: [])
                        print (receiptString)
                        let otherInfo = "''"
                        // Read receiptData
                        print (UIDevice.current.identifierForVendor!.uuidString)
                        // 呼叫 webView callback
                        let command = "DepositMoneyResultIOS(\(otherInfo), \(otherInfo), '\(receiptString)')"
                        //let command = "DepositMoneyResultIOS(\(otherInfo), \(otherInfo), '12345')"
                        print ( command )
                        globalWebView?.evaluateJavaScript(command){ (result, err) in
                            print(err, result)
                        }
                    }
                    catch { print("Couldn't read receipt data with error: " + error.localizedDescription) }
                }
                print ("~~~~~")
                print ("purchased")
                SKPaymentQueue.default().finishTransaction($0)
            case .failed:
                print($0.error ?? "")
                if ($0.error as? SKError)?.code != .paymentCancelled {
                    // show error
                }
                SKPaymentQueue.default().finishTransaction($0)
            case .restored:
                SKPaymentQueue.default().finishTransaction($0)
            case .deferred:
                break
            @unknown default:
                break
            }
            
        }
    }
    
}


