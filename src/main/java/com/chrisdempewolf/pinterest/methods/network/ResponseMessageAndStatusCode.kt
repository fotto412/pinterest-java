package com.chrisdempewolf.pinterest.methods.network

import org.apache.http.Header

data class ResponseMessageAndStatusCode(val statusCode: Int, val message: String, val header: Array<Header>)