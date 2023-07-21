//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Sarah Brenner on 7/20/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import MultiPlatformLibrary

public class SampleObservableObject: ObservableObject {
    var viewModel: HomeViewModel
    
    
    init(wrapper: HomeViewModel) {
        viewModel = wrapper
    }
   
}
public extension  HomeViewModel {
    func asObservableObject() -> SampleObservableObject {
        return  SampleObservableObject(wrapper: self)
    }
}

