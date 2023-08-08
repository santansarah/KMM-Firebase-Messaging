import SwiftUI
import FirebaseCore
import MultiPlatformLibrary


@main
struct iOSApp: App {
    
    let signInService: SignInService?
    
    init() {
        FirebaseApp.configure()
        KoinKt.doInitKoin()
        signInService = SignInService()
    }
    
	var body: some Scene {
		WindowGroup {
			HomeView()
                .environmentObject(signInService!)
                .onOpenURL { url in
                    
                    guard let scheme = url.scheme, scheme == "kmm" else { return }
                    guard url.host != nil else { return }
                    
                    print("got here...")
                    
                    let linkScreen = AppDeepLink.utils().typeFromPath(fullDeepLink: url.absoluteString)
                    print("screen: " + linkScreen.name)
                    Router.shared.path.append(linkScreen)
                    
                }
        }
	}
}



