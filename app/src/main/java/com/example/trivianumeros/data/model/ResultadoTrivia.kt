package com.example.trivianumeros.data.model

data class ResultadoTrivia(
    var found: Boolean,
    var number: Int,
    var text: String,
    var type: String
)