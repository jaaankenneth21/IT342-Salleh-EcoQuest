package edu.cit.salleh.ecoquest.model

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)