//
//  AppDelegate.swift
//  anunipa
//
//  Created by 石康暘 on 2020/4/27.
//  Copyright © 2020 wolves. All rights reserved.
//

import UIKit
import WebKit
import StoreKit
import FacebookCore

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    var shouldRotate = true

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        ApplicationDelegate.shared.application(application, didFinishLaunchingWithOptions: launchOptions)
        return true
    }
    
    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
       ApplicationDelegate.shared.application(app,
          open: url,
          sourceApplication: options[UIApplication.OpenURLOptionsKey.sourceApplication] as? String,
          annotation:
        options[UIApplication.OpenURLOptionsKey.annotation])
    }
    
    // 要重新設定 webview 的大小
    func application(_ application: UIApplication, supportedInterfaceOrientationsFor window: UIWindow?) -> UIInterfaceOrientationMask {
        //print ("[application]")
        // 做儲值相關的處理
        SKPaymentQueue.default().add (IAPManager.shared)
        // 處理旋轉的動作
        if (shouldRotate == true){
            return UIInterfaceOrientationMask.all
        }
        return UIInterfaceOrientationMask.landscapeRight
        //return UIInterfaceOrientationMask.all
        
    }
    // 所使用的 webview
    var webView: WKWebView?

    func applicationWillResignActive(_ application: UIApplication) {
        print ("[applicationWillResignActive]")
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        print ("[applicationDidEnterBackground]")
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        print ("[applicationWillEnterForeground]")
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        print ("[applicationDidBecomeActive]")
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(_ application: UIApplication) {
        print ("[applicationWillTerminate]")
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
        // 移除監控
        SKPaymentQueue.default().remove (IAPManager.shared)
    }


}

