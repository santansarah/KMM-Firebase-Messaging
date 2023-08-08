//
//  ProductList.swift
//  iosApp
//
//  Created by Sarah Brenner on 7/24/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import MultiPlatformLibrary
import mokoMvvmFlowSwiftUI

struct ProductList: View {
    var productStateK: ServiceResultKs<AnyObject>
    @EnvironmentObject var signInService: SignInService
    @StateObject var router = Router.shared
    
    var body: some View {
        NavigationStack(path: $router.path) {
            VStack {
                Image(resource: \.logo)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .padding(.all)
                
                if !signInService.isSignedIn.boolValue {
                    Button {
                        router.path.append(AppDeepLink.signin)
                    } label: {
                        Text(resource: SharedRes.strings().new_sign_in_heading)
                            .padding()
                            .foregroundColor(Color(resource: \.lightText))
                            .frame(maxWidth: .infinity)
                            .background(Color(resource: \.primary))
                    }
                    .buttonStyle(.borderless)
                } else {
                    Button("Sign Out") {
                        signInService.signOut()
                    }
                }
                
                Spacer()
                
                switch(productStateK) {
                case .loading:
                    ProgressView()
                case .empty:
                    Text("No products")
                case .error(let resultError):
                    VStack {
                        Text(resultError.message)
                    }
                case .success(let resultSuccess):
                    List(
                        resultSuccess.data as! [Product],
                        id: \.self.id
                    ) { product in
                        Text(product.title)
                            .listRowBackground(Color(resource: \.cardSurface))      .padding()
                            .foregroundColor(Color(resource: \.darkText))
                    }
                    .background(Color(resource: \.background))
                    .scrollContentBackground(.hidden)
                }
                
            }
            .background(Color(resource: \.background))
            .navigationDestination(for: AppDeepLink.self) { deepLink in
                switch deepLink {
                case .signin: SignInScreen(authUI: signInService.authUI!)
                default: EmptyView()
                }
            }
        }
//        .onReceive(createPublisher(userRepo.isUserSignedIn) { result in
//            print("signinservice: " + result.boolValue.description)
//        })
    }
}

struct ProductList_Previews: PreviewProvider {
    static var previews: some View {
        
        let mockProducts: [Product] = ProductsKt.products
        let products = ServiceResultSuccess(data: mockProducts as NSArray)
        
        ProductList(productStateK: products.resultKs)
            .environmentObject(SignInService())
            .environmentObject(Router())
    }
}
