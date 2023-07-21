import SwiftUI
import MultiPlatformLibrary

struct ContentView: View {
    @ObservedObject var homeViewModel = GetViewModels().getHomeViewModel().asObservableObject()
	
    var body: some View {
        VStack {
            Image(resource: \.logo)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .padding(.all)
            
        
            Text(resource: \.new_sign_in_heading)
                .foregroundColor(Color.black)

            Spacer()
            
            List {
                ForEach(homeViewModel.viewModel.products) { product in
                    Text(product.title)
                }
            }
        }
    }
    
    
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
