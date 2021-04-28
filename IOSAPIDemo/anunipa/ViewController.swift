//-----------------------------------------------------
//  ViewController.swift
//  anunipa
//
//  Created by 石康暘 on 2020/4/27.
//  Copyright © 2020 wolves. All rights reserved.
//-----------------------------------------------------

// History
// 1.1.4 試著上架的新版本
// 1.0.25 修正翻轉回大廳會上面有空條的動作
// 1.0.24 修正流海的轉向問題
// 1.0.22 新增 QWE IPA
// 1.3.0 介接APPLE 儲值功能

import UIKit
import WebKit
// 做音樂播放的LIB
import AVFoundation
import SwiftUI
import Foundation
import FacebookLogin

var globalWebView : WKWebView?

class ViewController: UIViewController, WKNavigationDelegate, WKScriptMessageHandler{
        
    lazy var webView: WKWebView = {
        let preferences = WKPreferences()
        preferences.javaScriptEnabled = true
        
        let configuration = WKWebViewConfiguration()
        configuration.preferences = preferences
        configuration.userContentController = WKUserContentController()
        // 给webview与swift交互起一个名字：AppModel，webview给swift发消息的时候会用到
        configuration.userContentController.add(self, name: "AppModel")
        var webView = WKWebView(
            frame: self.view.frame,
            configuration: configuration
        )
        webView.navigationDelegate = self
        // Add observer
        webView.addObserver(self, forKeyPath: "URL", options: .new, context: nil)
        // 做重新設定 webview 的動作
        //self.resizeWebview(web: webView)
        globalWebView = webView
        // 回傳設好的結果
        return webView
    }()
    
    // Observe value
    override func observeValue(forKeyPath keyPath: String?, of object: Any?, change: [NSKeyValueChangeKey : Any]?, context: UnsafeMutableRawPointer?) {
        if let key = change?[NSKeyValueChangeKey.newKey] {
            print("observeValue \(key)") // url value
            self.resizeWebview(web: self.webView)
        }
    }
    
    // 重置解析度
    func resizeWebview (web: WKWebView) {
        let inserts = UIApplication.shared.delegate?.window??.safeAreaInsets ?? UIEdgeInsets.zero
        //if self.getDeviceName() == 0 {
        //    inserts = UIEdgeInsets.zero
        //}
        print ("~~~~~~~~~~~~")
        print (web.safeAreaInsets)
        print ("[UIDevice.current.orientation]")
        print (UIDevice.current.orientation.isLandscape)
        print (UIDevice.current.orientation.isPortrait)
        print (UIDevice.current.orientation.rawValue)
        print ("[resizeWebview] bounds")
        print (self.view.bounds.width)
        print (self.view.bounds.height)
        print ("[resizeWebview] inserts")
        print (inserts.top)
        print (inserts.bottom)
        print (inserts.left)
        print (inserts.right)
        print ("[resizeWebview] center")
        print (self.view.center)
        // 計算寬/高
        var big : CGFloat = 0.0
        var small : CGFloat = 0.0
        if self.view.bounds.height > self.view.bounds.width {
            big = self.view.bounds.height
            small = self.view.bounds.width
        }
        else
        {
            small = self.view.bounds.height
            big = self.view.bounds.width
        }
        //web.frame = self.view.bounds
        //web.safeAreaInsets = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 0)
        // 流海在左邊
        if (UIDevice.current.orientation.rawValue == 0 || UIDevice.current.orientation.rawValue == 3)
        {
            self.showToast(message: "first")
            web.frame = CGRect(
                x:inserts.left,
                y:inserts.top,
                width:big-inserts.left,
                height:small
            )
            print (web.frame)
        }
        // 流海在右邊
        else if (UIDevice.current.orientation.rawValue == 4)
        {
            self.showToast(message: "landscape top in right")
            web.frame = CGRect(
                x:0,
                y:0,
                width:big-inserts.top,
                height:small
            )
            print (web.frame)
        }
        //else if (UIDevice.current.orientation.isPortrait)
        // 流海在上面
        else if (UIDevice.current.orientation.rawValue == 1)
        {
            self.showToast(message: "portrait top is top")
            web.frame = CGRect(
                x:0,
                y:inserts.left,
                width:small,
                height:big-inserts.left
            )
            print (web.frame)
        }
        // 開局時會不知道道方向，就當是 landscape
        else
        {
            self.showToast(message: "undirection")
            web.frame = CGRect(
                x:0,
                y:0,
                width:big,
                height:small
            )
        }
        // 让webview翻动有回弹效果
        web.scrollView.bounces = false
        web.contentMode = UIView.ContentMode.scaleAspectFit
        // 只允许webview上下滚动
        webView.scrollView.alwaysBounceVertical = false
        print (web.safeAreaInsets)
        return
    }
    
    // 控制使用的浮動按鈕
    var mActionButton: ActionButton!
    // MP3播放
    var mSound: AVAudioPlayer?
    
    // 連接的網址
    // BigA UAT
    //var mLobbyURL = "https://entrance-anun.yongxu.com.tw/?v="
    // BigA DEV
    var mLobbyURL = "https://entrance-anun.wolves.com.tw/?v="
    // GPK UAT
    //var mLobbyURL = "https://entrance-gpk.95gpk.com/?v="
    // GPK DEV
    //var mLobbyURL = "http://entrance-anun-gpk.wolves.com.tw/?v="
    
    func randomString(length: Int) -> String {
      let letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
      return String((0..<length).map{ _ in letters.randomElement()! })
    }
    
    override func viewDidLoad() {
        print ("[viewDidLoad]")
        self.mLobbyURL = self.mLobbyURL + self.randomString(length: 8)
        self.navigationController?.navigationBar.isTranslucent = false
        super.viewDidLoad()
        // 設定背景COLOER
        view.backgroundColor = .black
        // 設定畫面不要關閉
        UIApplication.shared.isIdleTimerDisabled = true
        // 掛在網頁上
        webView.contentMode = .scaleAspectFill//.scaleToFill
        view.addSubview(webView)
        // 處理URL的載入動作
        // 做清除CACHE的動作
        //URLCache.shared.removeAllCachedResponses()
        // 設定空間使用＝0
        //URLCache.shared.diskCapacity = 0
        //URLCache.shared.memoryCapacity = 0
        self.loadURL(urlString:self.mLobbyURL)
        // 開始浮動視窗
        //self.addActionButton()
        //mActionButton.showVisible (active: false)
        // 產生新的浮動按鈕
        self.addJDJellyButton()
        mJDButton.showVisible (active: false)
        // 初使化音樂
        if let url = Bundle.main.url(forResource: "bg_music_00", withExtension: "mp4") {
            print ("[load sound]")
            self.mSound = try? AVAudioPlayer(contentsOf: url)
            self.mSound?.numberOfLoops = -1
        }
        // 做轉向的動作
        setAutoRotation(autoRotation: false)
        // 設定 webview 的大小
        self.resizeWebview(web: self.webView)
        print (self.getDeviceName ())
        // 做商品的初使化動作
        self.iapManager.getProducts()
        // FB 資料
        if let accessToken = AccessToken.current {
            print("\(accessToken.userID) login")
        } else {
            print("not login")
        }
    }
    @ObservedObject var iapManager = IAPManager.shared
    
    // 讓底下的BAR會自動消失
    override var prefersHomeIndicatorAutoHidden: Bool {
        //return true
        return false
    }
    
    override func viewDidAppear(_ animated: Bool)
    {
        super.viewDidAppear(animated)
        self.setNeedsUpdateOfHomeIndicatorAutoHidden()
    }
    
    func setAutoRotation (autoRotation:Bool)
    {
        print ("[setAutoRotation] " + String(autoRotation))
        let appdelegate = UIApplication.shared.delegate as! AppDelegate
        appdelegate.shouldRotate = autoRotation
    }
    
    
    // 做載入 URL 的動作
    func loadURL (urlString:String)
    {
        print ("[loadURL] "+urlString)
        let url = URL(string:urlString)
        // 設定網址做打開的動作
        if let url = url {
            let request = URLRequest (url:url)
            // 做清除CACHE的動作
            URLCache.shared.removeAllCachedResponses()
            // 做載入的動作
            webView.load (request)
        }
    }
    
    // 處理浮動按鈕的動作
    func addActionButton() -> Void {	
        // 取得圖片
        let rimage = UIImage(named: "floatingbutton")!
        // 產生按鈕
        mActionButton = ActionButton(attachedToView: self.view, items: [])
        // 實作按下的行為
        mActionButton.action = { button in self.loadURL (urlString:self.mLobbyURL) }
        // 設定圖片
        mActionButton.setImage (rimage, forState: [])
        // 設定背景顏色
        mActionButton.backgroundColor = UIColor(red: 238.0/255.0, green: 130.0/255.0, blue: 34.0/255.0, alpha:0.0)
    }
    
    // 新版本的浮動按鈕
    var mJDButton : JDJellyButton!
    func addJDJellyButton () -> Void {
        mJDButton = JDJellyButton()
        mJDButton.attachtoView(rootView: self.view,mainbutton: UIImage(named:"floatingbutton")!)
        mJDButton.delegate = self
        mJDButton.datasource = self
        mJDButton.setJellyType(type: .Cross)
        // 實作按下的行為
        // 設定按鈕位置
        mJDButton.Container.frame.origin.x = -0.5 * mJDButton.Container.frame.width + 30
        mJDButton.Container.frame.origin.y = -0.5 * mJDButton.Container.frame.height + 30
    }

    // 用來接收 JS 傳送資料給 SWIFT
    func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        print ("[userContentController]")
        print(message.body)
        // 依照行為來做事
        let action : String = message.body as! String
        // 5. 離開APP
        if (action == "ExitApp")
        {
            // 做離開遊戲
            UIControl().sendAction(
                #selector(NSXPCConnection.suspend)
                , to: UIApplication.shared
                , for: nil
            )
        }
        // 1. 強制更新版本號
        else if (action == "appReloadURL")
        {
            // 做清除CACHE的動作
            URLCache.shared.removeAllCachedResponses()
            // 設定空間使用＝0
            //NSURLCache.sharedURLCache().diskCapacity = 0
            //NSURLCache.sharedURLCache().memoryCapacity = 0
            // 處理URL的載入動作
            self.loadURL(urlString:self.mLobbyURL)
        }
        // 3. 關閉浮動按鈕
        else if (action == "CloseFloatingButton")
        {
            //mActionButton.showVisible (active: false)
            mJDButton.showVisible (active: false)
            // 轉成橫的
            UIDevice.current.setValue(UIInterfaceOrientation.landscapeRight.rawValue, forKey: "orientation")
            webView.contentMode = .scaleAspectFill//.scaleToFill
            setAutoRotation(autoRotation: false)
            // 做轉向的動作
            self.resizeWebview(web: self.webView)
        }
        // 4. 打開浮動按鈕
        else if (action == "OpenFloatingButton")
        {
            setAutoRotation(autoRotation: true)
            //mActionButton.showVisible(active: true)
            mJDButton.showVisible (active: true)
        }
        // 6. 播放音樂
        else if (action.startsWith(str: "PlayAppSound") == true)
        {
            print ("[PlayAppSound]")
            let result = action.split(separator: " ")
            print (result)
            //let vol = String(result[2]).floatValue
            //if (self.mSound?.isPlaying == false)
            //{
            //    self.mSound?.play()
            //}
            //self.mSound?.setVolume(vol, fadeDuration: 0.1)
        }
        // 7. 關閉音樂
        else if (action == "StopAppSound")
        {
            //self.mSound?.stop ()
        }
        // 18. 做儲值的動作
        else if (action.startsWith(str: "DepositMoneyStr") == true)
        {
            print ("[DepositMoneyStr]")
            let result = action.split(separator: " ")
            print (result)
            //print (String(result[1]))
            self.iapManager.buyByString(productStr: String(result[1]))
        }
        else if (action == "LoginFB")
        {
            let manager = LoginManager()
            manager.logIn { (result) in
               if case LoginResult.success(granted: _, declined: _, token: _) = result {
                    print("login ok")
                    if let accessToken = AccessToken.current {
                        print("[login] \(accessToken.tokenString)")
                        // 實現了登出的動作
                        let manager = LoginManager()
                        manager.logOut()
                        // 回傳了結果
                        let command = "onFBCallback('\(accessToken.tokenString)')"
                        print ( command )
                        globalWebView?.evaluateJavaScript(command){ (result, err) in
                            print(err, result)
                        }
                    } else {
                        print("not login")
                    }
               } else {
                    print("login fail")
                    let manager = LoginManager()
                    manager.logOut()
               }
            }
        }
    }
    
    var mFBID : String = ""
    
    
    override func didReceiveMemoryWarning() {
        print ("[didReceiveMemoryWarning]")
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // 做旋轉的動作
    override func viewWillTransition(to size: CGSize, with coordinator: UIViewControllerTransitionCoordinator) {
        print ("[viewWillTransition] ")
        resizeWebview(web: self.webView)
        if UIDevice.current.orientation.isLandscape {
            print ("landscape")
            //UIDevice.current.setValue(UIInterfaceOrientation.landscapeRight.rawValue, forKey: "orientation")
        } else {
            print ("portrait")
        }
    }
}

