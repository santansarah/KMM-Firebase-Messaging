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
    let analytics: IAnalyticsService
    let currentScreen: Int32
    var onScreenChanged: (Int32) -> Void
    
    var body: some View {
        if currentScreen == 0 {
            ProgressView()
        } else
        {
            let thisOnboardingScreen: OnboardingScreen =
            OnboardingScreenRepo().screens[Int(currentScreen)-1]
            
            VStack {
                Image(uiImage: thisOnboardingScreen.headingIcon.toUIImage()!)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .padding([.top, .leading, .trailing])
                    .padding([.bottom], 40)
                
                Text(resource: thisOnboardingScreen.headingText)
                    .padding([.leading, .trailing])
                    .padding([.bottom], 40)
                
                
                Button {
                    analytics.completeTutorial()
                    onScreenChanged(currentScreen)

                    } label: {
                        Text("Next")
                            .multilineTextAlignment(.center)
                            .lineLimit(0)
                            .controlSize(.large)
                            .padding()
                            .background(Color(resource: \.primary))
                            .foregroundColor(Color(resource: \.lightText))
                            .cornerRadius(16)
                    }
                
            }
        }
    }
}

struct OnboardingScreen_Previews: PreviewProvider {
    static var previews: some View {
        OnboardingScreenView(
            analytics: AppAnalyticsService(),
            currentScreen: 1,
                         onScreenChanged: {_ in }
        )
    }
}
