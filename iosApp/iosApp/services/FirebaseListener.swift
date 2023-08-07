//
//  FirebaseListener.swift
//  iosApp
//
//  Created by Sarah Brenner on 8/6/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import FirebaseAuth

class FirebaseAuthStateManager: ObservableObject {
    @Published var signInState: Bool = false
    private var handle: AuthStateDidChangeListenerHandle!
    
    init() {
        handle = Auth.auth().addStateDidChangeListener { (auth, user) in
            if let _ = user {
                print("Sign-in")
                self.signInState = true
            } else {
                print("Sign-out")
                self.signInState = false
            }
        }
    }
    
    deinit {
        Auth.auth().removeStateDidChangeListener(handle)
    }
    
    func signOut() {
        do {
            try Auth.auth().signOut()
        } catch {
            print("Error")
        }
    }
}
