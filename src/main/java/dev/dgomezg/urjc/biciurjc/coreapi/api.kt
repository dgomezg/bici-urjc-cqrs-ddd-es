package dev.dgomezg.urjc.biciurjc.coreapi

data class RegisterUserCommand(val userId: String, val fullName: String)
data class RegisterBikeCommand(val bikeId: String, val location: String, val status: BikeStatus)
data class RentBikeCommand(val bikeId: String, val userId: String)
data class ReturnBikeCommand(val bikeId: String, val location: String)

enum class BikeStatus {AVAILABLE, RENTED, BROKEN}

class AllBikesQuery
class AllUsersQuery
data class BikeQuery(val bikeId: String)
data class UserQuery(val userId: String)

data class BikeCreatedEvent(val bikeId: String, val location: String, val status: BikeStatus)
data class UserCreatedEvent(val userId: String, val fullName: String)
data class BikeRentedEvent(val bikeId: String, val userId: String)
data class BikeReturnedEvent(val bikeId: String, val location:String)
