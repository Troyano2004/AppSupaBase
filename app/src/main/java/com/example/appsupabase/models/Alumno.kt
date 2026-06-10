package com.example.appsupabase.models

import kotlinx.serialization.Serializable
@Serializable

data class Alumno (
    val id:Int,
    val nombres: String? = null,
    val correo: String? = null,
    val telefono: String? = null,
    val paralelo: String? = null,
    val foto: String? = null,

    )