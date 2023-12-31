// This file automatically generated by MOKO KSwift (https://github.com/icerockdev/moko-kswift)
//
import MultiPlatformLibrary

/**
 * selector: ClassContext/KMM_Firebase_Messaging:shared/com/santansarah/kmmfirebasemessaging/utils/ServiceResult */
public enum ServiceResultKs<T: AnyObject> {

  case empty
  case error(ServiceResultError)
  case loading
  case success(ServiceResultSuccess<T>)

  public var sealed: ServiceResult {
    switch self {
    case .empty:
      return MultiPlatformLibrary.ServiceResultEmpty() as MultiPlatformLibrary.ServiceResult
    case .error(let obj):
      return obj as! MultiPlatformLibrary.ServiceResult
    case .loading:
      return MultiPlatformLibrary.ServiceResultLoading() as MultiPlatformLibrary.ServiceResult
    case .success(let obj):
      return obj as! MultiPlatformLibrary.ServiceResult
    }
  }

  public init(_ obj: ServiceResult) {
    if obj is MultiPlatformLibrary.ServiceResultEmpty {
      self = .empty
    } else if let obj = obj as? MultiPlatformLibrary.ServiceResultError {
      self = .error(obj)
    } else if obj is MultiPlatformLibrary.ServiceResultLoading {
      self = .loading
    } else if let obj = obj as? MultiPlatformLibrary.ServiceResultSuccess<T> {
      self = .success(obj)
    } else {
      fatalError("ServiceResultKs not synchronized with ServiceResult class")
    }
  }

}
