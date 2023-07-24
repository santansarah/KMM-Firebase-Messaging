import SwiftUI
import MultiPlatformLibrary
import mokoMvvmFlowSwiftUI


struct HomeView: View {
    @StateObject var homeViewModel: HomeViewModel = GetViewModels().getHomeViewModel()
    
    init() {
        homeViewModel.clearDatastore()
        
    }
    
    var body: some View {
        
        let homeUiState = homeViewModel.stateKs
        
        if !homeUiState.isOnboardingComplete {
                OnboardingScreenView(
                    currentScreen: homeUiState.currentOnboardingScreen,
                    onScreenChanged: homeViewModel.onOnboardingScreenUpdated
                )}
        else{
            ProductList(productStateK: homeUiState.products.resultKs)
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
