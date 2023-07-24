//
//  OnboardingScreen.swift
//  iosApp
//
//  Created by Sarah Brenner on 7/23/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import MultiPlatformLibrary

struct OnboardingScreenView: View {
    var currentScreen: Int32
    var onScreenChanged: (Int32) -> Void
    
    var body: some View {
        let currentScreen: OnboardingScreen =
        OnboardingScreenRepo().screens[Int(currentScreen)-1]
                    
        VStack {
            Image(uiImage: currentScreen.headingIcon.toUIImage()!)
                .padding([.top, .leading, .trailing])
                .padding([.bottom], 20)
            
            Text(resource: currentScreen.headingText)
            
            
        }
        
    }
}

struct OnboardingScreen_Previews: PreviewProvider {
    static var previews: some View {
        OnboardingScreenView(
            currentScreen: 1,
                         onScreenChanged: {_ in }
        )
    }
}
