package com.zaed.ordertracker.domain.utils

open class InvalidCredentialsException(
    message: String,
) : Exception(message)

class InvalidPasswordException : InvalidCredentialsException("Password is invalid")

class InvalidUsernameException : InvalidCredentialsException("Username is invalid")

class UserNotFoundException : Exception("User not found")

class FlightHasUnprocessedShipmentsException : Exception("Flight has unprocessed shipments")
