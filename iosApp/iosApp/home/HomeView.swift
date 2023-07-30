import SwiftUI
import MultiPlatformLibrary
import mokoMvvmFlowSwiftUI


struct HomeView: View {
    @StateObject var homeViewModel: HomeViewModel = GetViewModels().getHomeViewModel()
    @EnvironmentObject var router: Router
    
    init() {
        // temp, just to reset it each time
        homeViewModel.clearDatastore()
    }
    
    var body: some View {
        NavigationStack(path: $router.path) {
            let homeUiState = homeViewModel.stateKs
            
            VStack {
                if !homeUiState.isOnboardingComplete {
                    OnboardingScreenView(
                        analytics: AppAnalyticsService(),
                        currentScreen: homeUiState.currentOnboardingScreen,
                        onScreenChanged: homeViewModel.onOnboardingScreenUpdated
                    )}
                else{
                    ProductList(productStateK: homeUiState.products.resultKs)
                }
            }
            .navigationDestination(for: LinkScreen.self) { deepLink in
                chooseDestination(for: deepLink)
            }
            .navigationTitle("Welcome")
        }
    }
}

@ViewBuilder
func chooseDestination(for goToPath: LinkScreen) -> some View {
    
        switch goToPath {
        case .signin: SignInScreen()
        default: EmptyView()
        }
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
