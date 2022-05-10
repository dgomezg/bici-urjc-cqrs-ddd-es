package dev.dgomezg.urjc.biciurjc.coreapi

data class RegisterUserCommand(val userId: String, val fullName: String)
data class RegisterBikeCommand(val bikeId: String, val location: String, val status: BikeStatus)
data class RentBikeCommand(val bikeId: String, val userId: String)
data class ReturnBikeCommand(val bikeId: String, val location: String)

enum class BikeStatus {AVAILABLE, RENTED, BROKEN}
