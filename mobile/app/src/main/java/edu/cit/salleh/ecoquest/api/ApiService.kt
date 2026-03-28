package edu.cit.salleh.ecoquest.api

import edu.cit.salleh.ecoquest.model.AuthResponse
import edu.cit.salleh.ecoquest.model.LoginRequest
import edu.cit.salleh.ecoquest.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
}