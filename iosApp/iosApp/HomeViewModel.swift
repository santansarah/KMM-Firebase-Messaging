//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Sarah Brenner on 7/20/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import MultiPlatformLibrary

class iOSHomeViewModel: ObservableObject {
    let homeViewModel = GetViewModels().getHomeViewModel()
    
    @Published var products = [Product]()
    
    func observeState() {
        homeViewModel.products.subscribe(onCollect: { list in
                self.products = list as! [Product]
        })
    }
}
