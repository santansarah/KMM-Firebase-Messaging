//
//  CustomFirebaseUIViewController.swift
//  iosApp
//
//  Created by Sarah Brenner on 8/9/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import UIKit
import SwiftUI
import FirebaseAuthUI
import MultiPlatformLibrary

class FUICustomAuthPickerViewController: FUIAuthPickerViewController {
    
    @IBOutlet weak var accountIcon: UIImageView!
    
    override func viewWillAppear(_ animated: Bool) {
        
        let backgroundColor = SharedRes.colors().background.getUIColor()

        self.view.backgroundColor = backgroundColor
        self.navigationController?.navigationBar.backgroundColor = backgroundColor
        accountIcon.image = SharedRes.images().account.toUIImage()
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
}
