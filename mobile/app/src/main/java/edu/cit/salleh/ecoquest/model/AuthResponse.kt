package edu.cit.salleh.ecoquest.model

data class AuthResponse(
    val token: String,
    val name: String,
    val email: String,
    val points: Int
)