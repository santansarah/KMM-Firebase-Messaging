import SwiftUI
import FirebaseCore
import MultiPlatformLibrary


@available(iOS 16.0, *)
@main
struct iOSApp: App {
    
    @StateObject var router = Router()
    
    init() {
        KoinKt.doInitKoin()
        FirebaseApp.configure()
    }
   
    
	var body: some Scene {
		WindowGroup {
			HomeView()
                .environmentObject(router)
                .onOpenURL { url in
                    
                    guard let scheme = url.scheme, scheme == "kmm" else { return }
                    guard url.host != nil else { return }
                    
                    print("got here...")
                    
                    let linkScreen = AppDeepLinks().getByLink(fullDeepLink: url.absoluteString)
                    print("screen: " + linkScreen.name)
                    router.path.append(linkScreen)
                    
                }
        }
	}
}



