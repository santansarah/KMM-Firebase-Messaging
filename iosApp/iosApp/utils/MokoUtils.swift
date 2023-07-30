//
//  Image.swift
//  iosApp
//
//  Created by Sarah Brenner on 7/18/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import MultiPlatformLibrary
import SwiftUI

extension Image {
    init(resource: KeyPath<SharedRes.images, ImageResource>) {
        self.init(uiImage: SharedRes.images()[keyPath: resource].toUIImage()!)
    }
}

extension Text {
    init(resource: StringResource) {
        self.init(resource.desc().localized())
    }
}

extension SwiftUI.Color {
    
    init(resource: KeyPath<SharedRes.colors, ColorResource>) {
        self.init(SharedRes.colors()[keyPath: resource].getUIColor())
    }
}

