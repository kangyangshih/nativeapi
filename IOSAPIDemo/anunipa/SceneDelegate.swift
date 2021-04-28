//
//  SceneDelegate.swift
//  anunipa
//
//  Created by 石康暘 on 2021/4/28.
//  Copyright © 2021 wolves. All rights reserved.
//

import Foundation
import FacebookCore

func scene(_ scene: UIScene,
    openURLContexts URLContexts: Set<UIOpenURLContext>
) {
    guard let context = URLContexts.first else {
        return
    }
    ApplicationDelegate.shared.application(
        UIApplication.shared,
        open: context.url,
        sourceApplication:
        context.options.sourceApplication,
        annotation: context.options.annotation
    )
}
