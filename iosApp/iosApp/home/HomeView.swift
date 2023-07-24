import SwiftUI
import MultiPlatformLibrary
import mokoMvvmFlowSwiftUI


struct HomeView: View {
    @StateObject var homeViewModel: HomeViewModel = GetViewModels().getHomeViewModel()
    
    var body: some View {
        
        let homeUiState = homeViewModel.stateKs
        
        if !homeUiState.isOnboardingComplete {
            if homeUiState.currentOnboardingScreen == 0 {
                ProgressView()
            } else {
                OnboardingScreenView(
                    currentScreen: homeUiState.currentOnboardingScreen,
                    onScreenChanged: homeViewModel.onOnboardingScreenUpdated
                )}
        }
        else{
            HomeViewContainer()}
        
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
    
//    var actionsKs: AnyPublisher<BookListViewModelActionKs, Never> {
//        get {
//            return createPublisher(self.actions)
//                .map { BookListViewModelActionKs($0) }
//                .eraseToAnyPublisher()
//        }
//    }
}

struct HomeViewContainer: View {
    //@State products: [Product]
    
    var body: some View {
    
        VStack {
            Image(resource: \.logo)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .padding(.all)
            
        
            Text(resource: SharedRes.strings().new_sign_in_heading)
                .foregroundColor(Color.black)

            Spacer()
            
//            List {
//                ForEach(homeViewModel.state(\.products, equals: { $0 === $1 },
//                                             mapper: { ($0 as! [Product]?)! }), id: \.id) { product in
//                    Text(product.title)
//                }
//            }
        }
    }
}

//struct ContentView_Previews: PreviewProvider {
//	static var previews: some View {
//		ContentView(
//            products: [Product(id: 0, title: "Test", price: 0.00, category: "Test", description: "Test", image: "url")]
//        )
//	}
//}
