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
    init(resource: KeyPath<SharedRes.images, ResourcesImageResource>) {
        self.init(uiImage: SharedRes.images()[keyPath: resource].toUIImage()!)
    }
}

extension Text {
    init(resource: ResourcesStringResource) {
        //var test = ResourcesStringDesc(SharedRes.strings()[keyPath: resource])
        self.init(StringsKt.mrString(resource: resource))
    }
}
