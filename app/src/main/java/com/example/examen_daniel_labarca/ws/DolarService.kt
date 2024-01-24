package com.example.examen_daniel_labarca.ws

import retrofit2.http.GET

interface DolarService {

    //https://mindicador.cl/api/dolar/fecha

    @GET("dolar")

    suspend fun getDolar() : Dolar
}