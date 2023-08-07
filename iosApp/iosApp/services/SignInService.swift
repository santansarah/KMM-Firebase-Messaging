import Foundation

import FirebaseAuthUI
import FirebaseGoogleAuthUI

class SignInService: NSObject, ObservableObject, FUIAuthDelegate {
    let authUI: FUIAuth? = FUIAuth.defaultAuthUI()

    override init() {
        super.init()

        authUI?.delegate = self
        
        let providers: [FUIAuthProvider] = [
            FUIGoogleAuth(authUI: authUI!)
        ]
        
        authUI?.providers = providers
    }

    
    func authUI(_ authUI: FUIAuth, didSignInWith user: User?, error: Error?) {
        print("user: \(user?.email ?? "none")")
        
        switch error {
            case .some(let error as NSError) where UInt(error.code) == FUIAuthErrorCode.userCancelledSignIn.rawValue:
              print("User cancelled sign-in")
            case .some(let error as NSError) where error.userInfo[NSUnderlyingErrorKey] != nil:
              print("Login error: \(error.userInfo[NSUnderlyingErrorKey]!)")
            case .some(let error):
              print("Login error: \(error.localizedDescription)")
            case .none:
              return
            }

    }
    

    func signOut() throws {
        try authUI?.signOut()
    }
}
