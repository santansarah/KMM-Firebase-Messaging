//
//  ProductList.swift
//  iosApp
//
//  Created by Sarah Brenner on 7/24/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import MultiPlatformLibrary

struct ProductList: View {
    var productStateK: ServiceResultKs<AnyObject>
    @EnvironmentObject var signInService: SignInService
    
    @State var requestSignIn = false
    
    var body: some View {
        
        if requestSignIn == false {
            
            VStack {
                Image(resource: \.logo)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .padding(.all)
                
                
                Button {
                    requestSignIn = !requestSignIn
                } label: {
                    Text(resource: SharedRes.strings().new_sign_in_heading)
                        .foregroundColor(Color(resource: \.darkText))
                    
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
        } else {
            SignInScreen(authUI: signInService.authUI!)
        }
    }
}


struct ProductList_Previews: PreviewProvider {
    static var previews: some View {
        
        let mockProducts: [Product] = ProductsKt.products
        let products = ServiceResultSuccess(data: mockProducts as NSArray)
        
        ProductList(productStateK: products.resultKs)
            .environmentObject(SignInService())
    }
}
