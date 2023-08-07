import SwiftUI
import MultiPlatformLibrary
import mokoMvvmFlowSwiftUI


struct HomeView: View {
    @StateObject var homeViewModel: HomeViewModel = GetViewModels().getHomeViewModel()
    @EnvironmentObject var router: Router
    @EnvironmentObject var signInService: SignInService
    
    var body: some View {
        NavigationStack(path: $router.path) {
            let homeUiState = homeViewModel.stateKs
            
            VStack {
                //Image(resource: \.flower)
                if !homeUiState.isOnboardingComplete {
                    OnboardingScreenView(
                        analytics: AppAnalyticsService(),
                        currentScreen: homeUiState.currentOnboardingScreen,
                        onScreenChanged: homeViewModel.onOnboardingScreenUpdated
                    )}
                else{
                    ProductList(productStateK: homeUiState.products.resultKs)                }
            }
            .navigationDestination(for: AppDeepLink.self) { deepLink in
                chooseDestination(for: deepLink)
            }
        }
    }
}

@ViewBuilder
func chooseDestination(for goToPath: AppDeepLink) -> some View {
    
    EmptyView()
    
//        switch goToPath {
//        case .signin: SignInScreen()
//        default: EmptyView()
//        }
}

extension HomeViewModel {
    var stateKs: HomeUIState {
        get {
            return self.state(
                \.homeUIState,
                equals: { $0 === $1 },
                mapper: { ($0 as HomeUIState?)! }
            )
        }
    }
    
}

extension ServiceResult {
    var resultKs: ServiceResultKs<AnyObject> {
        get {
            return ServiceResultKs(self)
        }
    }
}
