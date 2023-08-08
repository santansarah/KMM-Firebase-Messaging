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
    @State var showSignInButton = true
    
    var body: some View {
        NavigationStack(path: $router.path) {
            VStack {
                Image(resource: \.logo)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .padding(.all)
                
                if showSignInButton {
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
                    Spacer()
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
                        HStack {
                            AsyncImage(url: URL(string: product.image)) { phase in
                                switch phase {
                                case .empty:
                                    ProgressView()
                                        .frame(maxWidth: 100, maxHeight: 100)
                                case .success(let thisImage):
                                    thisImage.resizable()
                                        .aspectRatio(contentMode: .fit)
                                        .frame(maxWidth: 100, maxHeight: 100)
                                case .failure:
                                    Image(systemName: "photo")
                                        .frame(maxWidth: 100, maxHeight: 100)
                                @unknown default:
                                    EmptyView()
                                }
                            }
                            .padding(.all, 4)
                            Text(product.title)
                                .listRowBackground(Color(resource: \.cardSurface))
                                .padding(.all, 4)
                                .foregroundColor(Color(resource: \.darkText))
                        }
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
        .onReceive(signInService.getPublisher()) { isUserSignedIn in
            withAnimation {
                showSignInButton = !isUserSignedIn.boolValue
            }
        }
        
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
