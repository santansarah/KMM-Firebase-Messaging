import SwiftUI
import MultiPlatformLibrary

struct ContentView: View {
	
    var body: some View {
        VStack {
            Image(resource: \.logo)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .padding(.all)
            
        
            Text(resource: \.new_sign_in_heading)
                .foregroundColor(Color.black)

            Spacer()
            
        }
    }
    
    
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
