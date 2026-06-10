package com.example.appsupabase.models

import kotlinx.serialization.Serializable
@Serializable
data class Materia(
    val id: Long,
    val nombre: String? = null,
    val nivel: Int? = null
)
