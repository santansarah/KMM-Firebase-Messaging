//
//  SignInScreen.swift
//  iosApp
//
//  Created by Sarah Brenner on 7/27/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import FirebaseAuthUI
import FirebaseGoogleAuthUI

struct SignInScreen: UIViewControllerRepresentable {
    var authUI: FUIAuth
    
    func makeUIViewController(context: Context) -> UINavigationController {
        let authViewController = authUI.authViewController()
        return authViewController
    }
    
    func updateUIViewController(_ uiViewController: UINavigationController, context: Context) {
        
    }
}
