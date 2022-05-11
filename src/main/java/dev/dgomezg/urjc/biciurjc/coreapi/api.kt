package dev.dgomezg.urjc.biciurjc.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class RegisterUserCommand(@TargetAggregateIdentifier val userId: String, val fullName: String)
data class RegisterBikeCommand(@TargetAggregateIdentifier val bikeId: String, val location: String)
data class RentBikeCommand(@TargetAggregateIdentifier val bikeId: String, val userId: String)
data class ReturnBikeCommand(@TargetAggregateIdentifier val bikeId: String, val location: String)

enum class BikeStatus {AVAILABLE, RENTED, BROKEN}

class AllBikesQuery
class AllUsersQuery
data class BikeQuery(val bikeId: String)
data class UserQuery(val userId: String)

class Top10UsersQuery
data class UserRankingQuery(val userId: String)

data class BikeCreatedEvent(val bikeId: String, val location: String)
data class UserCreatedEvent(val userId: String, val fullName: String)
data class BikeRentedEvent(val bikeId: String, val userId: String)
data class BikeReturnedEvent(val bikeId: String, val location:String)
