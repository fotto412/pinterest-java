package com.chrisdempewolf.pinterest.methods.network

import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.fluent.Form
import org.apache.http.client.fluent.Request
import org.apache.http.util.EntityUtils

import java.net.URI

object NetworkHelper {

    @JvmStatic
    fun submitDeleteRequest(url: URI): ResponseMessageAndStatusCode {
        val response = Request.Delete(url)
                .viaProxy("95.111.230.175:8118")
                .execute()
                .returnResponse()

        return convertResponse(response)
    }

    @JvmStatic
    fun submitPostRequest(url: URI, formData: Map<String, String>): ResponseMessageAndStatusCode
            = submitRequestWithBody(url, formData, Request::Post)

    @JvmStatic
    fun submitPatchRequest(url: URI, formData: Map<String, String?>): ResponseMessageAndStatusCode
            = submitRequestWithBody(url, formData, Request::Patch)

    @JvmStatic
    private fun submitRequestWithBody(url: URI, formData: Map<String, String?>, request: (uri: URI) -> Request): ResponseMessageAndStatusCode {
        val response = request(url)
                .bodyForm(convertMapToForm(formData))
                .viaProxy("95.111.230.175:8118")
//                .viaProxy(HttpHost())
                .execute()
                .returnResponse()

        return convertResponse(response)
    }

    @JvmStatic
    private fun convertResponse(response: HttpResponse): ResponseMessageAndStatusCode
            = ResponseMessageAndStatusCode(response.statusLine.statusCode, EntityUtils.toString(response.entity),response.allHeaders)

    @JvmStatic
    private fun convertMapToForm(formData: Map<String, String?>): List<NameValuePair> {
        val form = Form.form()
        formData.filter  { it.value != null }
                .forEach { form.add(it.key, it.value) }
        return form.build()
    }
}
