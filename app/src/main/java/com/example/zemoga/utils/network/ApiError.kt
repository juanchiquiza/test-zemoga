package com.example.zemoga.utils.network

import com.example.zemoga.R
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.example.zemoga.data.models.ApiErrorModel
import com.example.zemoga.utils.RUtil.Companion.rString
import retrofit2.HttpException

class ApiError constructor(error: Throwable) {

    var message = "An error occurred"
    var code = 0
    var apiErrorModel: ApiErrorModel

    init {
        when (error) {
            is HttpException -> {
                val errorJsonString = error.response()?.errorBody()?.string()
                apiErrorModel = ApiErrorModel().apply {
                    code = error.code()
                    mesage = getMessageError(errorJsonString).capitalize()
                }
            }
            else ->
                apiErrorModel = ApiErrorModel().apply {
                    code = 0
                    mesage = error.message ?: message
                }
        }
    }

    private fun getMessageError(errorJsonString: String?): String {
        return if (errorJsonString.isNullOrEmpty()) {
            rString(R.string.lbl_error)
        } else {
            try {
                JsonParser()
                    .parse(errorJsonString)
                    .asJsonObject["message"]
                    .asString
            } catch (e: JsonSyntaxException) {
                e.message.toString()
            }
        }
    }
}