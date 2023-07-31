//
//  Analytics.swift
//  iosApp
//
//  Created by Sarah Brenner on 7/29/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import FirebaseAnalytics
import MultiPlatformLibrary

// Firebase implementation of Analytics
class AppAnalyticsService: IAnalyticsService {

    func completeTutorial() {
        Analytics.logEvent(AnalyticsEventTutorialComplete, parameters: nil)
    }
    
}
