package com.hj.papagotest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class MainActivity : AppCompatActivity() {

    private val NAVER_PAPAGO_CLIENT_ID = "rD0F_hl_Kx0VjeUUS0CI"
    private val NAVER_PAPAGO_CLIENT_SECRET = "r9mBaEyIWc"

    private lateinit var retrofit: Retrofit
    private lateinit var papagoService: PapagoService

    private lateinit var sourceTextView: TextView
    private lateinit var translatedTextView: TextView
    private lateinit var translateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sourceTextView = findViewById(R.id.sourceText1)
        translatedTextView = findViewById(R.id.translatedText)
        translateButton = findViewById(R.id.translateButton)

        retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/papago/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(createOkHttpClient())
            .build()

        papagoService = retrofit.create(PapagoService::class.java)

        translateButton.setOnClickListener {
            val sourceText = sourceTextView.text.toString()
            translateText("ko", "en", sourceText)
        }
    }



    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("X-Naver-Client-Id", "rD0F_hl_Kx0VjeUUS0CI")
                    .header("X-Naver-Client-Secret", "r9mBaEyIWc")
                    .method(original.method, original.body)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }


    private fun translateText(source: String, target: String, text: String) {
        val requestObject = TranslationRequestObject(source, target, text)
        val requestBody = Gson().toJson(requestObject)
            .toRequestBody("application/json".toMediaType())


        val call = papagoService.translateText(requestBody)
        call.enqueue(object : Callback<TranslationResponseObject> {
            override fun onResponse(
                call: Call<TranslationResponseObject>,
                response: Response<TranslationResponseObject>
            ) {
                if (response.isSuccessful) {
                    val translation = response.body()?.translatedText
                    translatedTextView.text = translation
                    Log.d("Translation", translation ?: "Translation failed")
                } else {
                    val errorCode = response.code()
                    val errorMessage = response.errorBody()?.string()
                    Log.e("Translation", "Translation failed with error code: $errorCode")
                    Log.e("Translation", "Error message: $errorMessage")
                }
            }

            override fun onFailure(call: Call<TranslationResponseObject>, t: Throwable) {
                Log.e("Translation", "Translation failed with exception: ${t.message}")
            }
        })
    }
}

data class TranslationRequestObject(
    @SerializedName("source")
    val source: String,
    @SerializedName("target")
    val target: String,
    @SerializedName("text")
    val text: String
)

data class TranslationResponseObject(
    @SerializedName("message")
    val message: Message?
) {
    val translatedText: String?
        get() = message?.result?.translatedText
}

data class Message(
    @SerializedName("result")
    val result: Result?
)

data class Result(
    @SerializedName("translatedText")
    val translatedText: String?
)

interface PapagoService {
    @POST("n2mt")
    fun translateText(@Body requestBody: RequestBody): Call<TranslationResponseObject>
}

