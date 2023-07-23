import SwiftUI
import MultiPlatformLibrary

struct ContentView: View {
    @ObservedObject var homeViewModel = GetViewModels().getHomeViewModel()
        
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
                ForEach(homeViewModel.state(\.products, equals: { $0 === $1 },
                                             mapper: { ($0 as! [Product]?)! }), id: \.id) { product in
                    Text(product.title)
                }
            }
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
