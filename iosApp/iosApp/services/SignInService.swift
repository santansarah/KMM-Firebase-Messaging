import Foundation
import FirebaseAuthUI
import FirebaseGoogleAuthUI
import FirebaseOAuthUI
import MultiPlatformLibrary
import mokoMvvmFlowSwiftUI
import Combine

class SignInService: NSObject, ObservableObject, FUIAuthDelegate {
    let authUI: FUIAuth? = FUIAuth.defaultAuthUI()
    let userRepo = KoinHelper().getUserRepo()
        
    @Published var isSignedIn: KotlinBoolean = false
    
    private var isSignedInCancellable: AnyCancellable?
    
    override init() {
        super.init()
        
        authUI?.delegate = self
        authUI?.shouldHideCancelButton = true
        
        let providers: [FUIAuthProvider] = [
            FUIGoogleAuth(authUI: authUI!),
            FUIOAuth.appleAuthProvider()
        ]
        
        authUI?.providers = providers
        
        // decided to not use this, but it's good code to have for future
        // reference.
        isSignedInCancellable = createPublisher(userRepo.isUserSignedIn)
            .receive(on: DispatchQueue.main)
            .sink(receiveCompletion: { completion in
                debugPrint(completion)
            }, receiveValue: { [weak self] value in
                self?.isSignedIn = value
                print("signinservice: " + value.boolValue.description)
            })
    }
    
    func getPublisher() -> AnyPublisher<KotlinBoolean, Never> {
        return createPublisher(userRepo.isUserSignedIn)
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
            guard let uid = user?.uid else {return}
            let token = ""
            
            Task {
                do {
                    try await userRepo.upsertUser(uid: uid, token: token)
                }
                catch {
                    print("exception")
                }

            }
            
            Router.shared.goBack()
            
        }
    }
    
    func signOut() {
        Task {
            do {
                try await userRepo.signOut()
                
            } catch {
                print(error)
            }
        }
    }
    
    func authPickerViewController(forAuthUI authUI: FUIAuth) -> FUIAuthPickerViewController {
      return FUICustomAuthPickerViewController(nibName: "CustomFirebaseUI",
                                               bundle: Bundle.main,
                                               authUI: authUI)
    }

}
