import SwiftUI
import MultiPlatformLibrary
import mokoMvvmFlowSwiftUI


struct HomeView: View {
    @StateObject var homeViewModel: HomeViewModel = KoinHelper().getHomeViewModel()
    
    var body: some View {
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
