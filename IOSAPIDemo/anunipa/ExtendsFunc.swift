//
//  ExtendsFunc.swift
//  anunipa
//
//  Created by 石康暘 on 2021/2/5.
//  Copyright © 2021 wolves. All rights reserved.
//

import Foundation
import UIKit
import WebKit

extension ViewController:JellyButtonDelegate
{
    internal func JellyButtonHasBeenTap(touch:UITouch,image:UIImage,groupindex:Int,arrindex:Int)
    {
        print ("[JellyButtonHasBeenTap]")
    }
    
    func JDMainButtonHasBeenTap ()
    {
        print ("[JDMainButtonHasBeenTap]")
        // 處理URL的載入動作
        self.loadURL(urlString:self.mLobbyURL)
    }
}

extension ViewController:JDJellyButtonDataSource
{
    func groupcount()->Int
    {
        print ("[groupcount]")
        return 0
    }
    
    func imagesource(forgroup groupindex:Int) -> [UIImage]
    {
        print ("[imagesource]")
        return [UIImage]()
    }
    
}

// 實作字串的轉換
extension String {
    // 轉成小數點
    var floatValue: Float {
        return (self as NSString).floatValue
    }
    // 轉成整數
    var intValue : Int {
        return Int((self as NSString).intValue)
    }
    // 做開頭判定
    func startsWith (str : String) -> Bool {
        return self.hasPrefix(str)
    }
    // 做結尾判定
    func endsWith (str : String) -> Bool {
        return self.hasSuffix(str)
    }
}

extension UIViewController {
    
    // 顯示中央MSG區
    func showToast(message : String) {
        print ("[showToast] "+message)
        // 使用的字型
        let font : UIFont = .systemFont(ofSize: 12.0)
        // 文字位置
        let toastLabel = UILabel(frame: CGRect(x: 0, y: 0, width: 150, height: 35))
        toastLabel.backgroundColor = UIColor.black.withAlphaComponent(0.6)
        toastLabel.textColor = UIColor.white
        toastLabel.font = font
        toastLabel.textAlignment = .center;
        toastLabel.text = message
        toastLabel.alpha = 1.0
        toastLabel.layer.cornerRadius = 10;
        toastLabel.clipsToBounds  =  true
        // 加到畫面上
        self.view.addSubview(toastLabel)
        // 做淡出的動作
        UIView.animate(withDuration: 4.0, delay: 0.1, options: .curveEaseOut, animations: {
             toastLabel.alpha = 0.0
        }, completion: {(isCompleted) in
            toastLabel.removeFromSuperview()
        })
    }
    
    func getDeviceName () -> Int {
        if UIDevice().userInterfaceIdiom == .phone {
        switch UIScreen.main.nativeBounds.height {
            case 1136:
                print("iPhone 5 or 5S or 5C")
                return 0
            case 1334:
                print("iPhone 6/6S/7/8")
                return 0
            case 1920, 2208:
                print("iPhone 6+/6S+/7+/8+")
                return 0
            case 2436:
                print("iPhone X/XS/11 Pro")
                return 1
            case 2688:
                print("iPhone XS Max/11 Pro Max")
                return 1
            case 1792:
                print("iPhone XR/ 11 ")
                return 1
            default:
                print("Unknown")
                return 0
            }
        }
        return 0
    }
    
}

extension WKWebView {
    // 強制網址不使用 safe area
    override open var safeAreaInsets: UIEdgeInsets {
        return UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 0)
    }
}

