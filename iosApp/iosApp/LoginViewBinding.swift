//
//  LoginViewBinding.swift
//  iosApp
//
//  Created by Sarah Brenner on 7/21/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import MultiPlatformLibrary
import mokoMvvmFlowSwiftUI
import Combine

struct LoginView: View {
    @ObservedObject var viewModel: LoginViewModel = GetViewModels().getLoginViewModel()
    let onLoginSuccess: () -> Void
    @State var alertShowed: Bool = false
    @State var alertMessage: String = ""
    
    var body: some View {
        LoginViewBody(
            login: viewModel.binding(\.login),
            password: viewModel.binding(\.password),
            isButtonEnabled: viewModel.state(\.isLoginButtonEnabled),
            isLoading: viewModel.state(\.isLoading),
            onLoginPressed: { viewModel.onLoginPressed() }
        ).alert(
            isPresented: $alertShowed
        ) {
            Alert(
                title: Text("Error"),
                message: Text(alertMessage),
                dismissButton: .default(Text("Close"))
            )
        }
    }
}


