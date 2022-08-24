package com.d3f.musclecarscatalog.api

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonToken
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.lang.RuntimeException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class RetrofitUtils private constructor() {

    companion object {
        val instance: RetrofitUtils by lazy {
            RetrofitUtils()
        }
    }

    val retrofitDefault: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://software877.github.io/api/")
            .addConverterFactory(GsonConverterFactory.create(buildGson()))
            //.client(buildHttpClient())
            .client(getUnsafeOkHttpClient().build())
            .build()
    }

    public fun getUnsafeOkHttpClient(): OkHttpClient.Builder =
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<X509Certificate?>?,
                                                    authType: String?) = Unit

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<X509Certificate?>?,
                                                    authType: String?) = Unit

                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                }
            )
            // Install the all-trusting trust manager
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory,
                trustAllCerts[0] as X509TrustManager
            )
            builder.hostnameVerifier { _, _ -> true }
            builder.connectTimeout(30, TimeUnit.SECONDS)
            builder.writeTimeout(30, TimeUnit.SECONDS)
            builder.readTimeout(60, TimeUnit.SECONDS)
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }


    private fun buildHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(60, TimeUnit.SECONDS)

        return builder.build()
    }


    private fun buildGson() = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .registerTypeAdapter(Boolean::class.java, booleanAsIntAdapter)
        // .registerTypeAdapter(Float::class.java, FloatTypeAdapter())
        //.registerTypeAdapter(Double::class.java, FloatTypeAdapter())
        .registerTypeAdapter(Integer::class.java, IntTypeAdapter())
        .registerTypeAdapter(Int::class.java, IntTypeAdapter())
        .create()

    private val booleanAsIntAdapter: TypeAdapter<Boolean> = object : TypeAdapter<Boolean>() {

        override fun write(out: com.google.gson.stream.JsonWriter?, value: Boolean?) {
            if (value == null) {
                out?.nullValue()
            } else {
                out?.value(value)
            }
        }

        override fun read(inJson: com.google.gson.stream.JsonReader?): Boolean? {
            inJson ?: return null
            val peek = inJson.peek() ?: return null
            return when (peek) {
                JsonToken.BOOLEAN -> inJson.nextBoolean()
                JsonToken.NULL -> {
                    inJson.nextNull()
                    null
                }
                JsonToken.NUMBER -> inJson.nextInt() != 0
                JsonToken.STRING -> java.lang.Boolean.parseBoolean(inJson?.nextString())
                else -> throw IllegalStateException("Expected BOOLEAN or NUMBER but was $peek")
            }
        }
    }

    class IntTypeAdapter : TypeAdapter<Int?>() {
        override fun write(out: com.google.gson.stream.JsonWriter?, value: Int?) {
            out?.value(value)
        }

        override fun read(inJson: com.google.gson.stream.JsonReader?): Int? {
            inJson ?: return null
            if (inJson.peek() === JsonToken.NULL) {
                inJson.nextNull()
                return null
            }
            return try {
                val result: String = inJson.nextString()
                if (result.isEmpty()) {
                    null
                } else result.toIntOrNull()
            } catch (e: NumberFormatException) {
                throw JsonSyntaxException(e)
            }
        }
    }
}
