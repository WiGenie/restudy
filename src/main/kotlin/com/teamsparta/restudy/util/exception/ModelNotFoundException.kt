package com.teamsparta.restudy.util.exception

data class ModelNotFoundException(val model: String):
    RuntimeException("$model is not found")