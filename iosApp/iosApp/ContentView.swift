import SwiftUI
import MultiPlatformLibrary

struct ContentView: View {
    @ObservedObject var homeViewModel = GetViewModels().getHomeViewModel()
    @State var products: [Product] = homeViewModel.state(\.products)
    
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
                ForEach(products, id: \.id) { product in
                    Text(product.title)
                }
            }
        }
    }
    
    func observeState() {
    
        homeViewModel.products.subscribe(onCollect: { list in
            products = list as! [Product]
        })
        
    }
    
}

//struct ContentView_Previews: PreviewProvider {
//	static var previews: some View {
//		ContentView(
//            products: [Product(id: 0, title: "Test", price: 0.00, category: "Test", description: "Test", image: "url")]
//        )
//	}
//}
