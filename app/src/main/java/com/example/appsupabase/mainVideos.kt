package com.example.appsupabase

import android.content.Context
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.appsupabase.adapters.VideoAdapter
import com.example.appsupabase.models.Video
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.cert.X509Certificate

class mainVideos : AppCompatActivity() {

    private fun getUnsafeQueue(context: Context): RequestQueue {
        val trustAll = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf<TrustManager>(trustAll), java.security.SecureRandom())
        val hurlStack = object : HurlStack() {
            override fun createConnection(url: URL): HttpURLConnection {
                val connection = super.createConnection(url)
                if (connection is HttpsURLConnection) {
                    connection.sslSocketFactory = sslContext.socketFactory
                    connection.hostnameVerifier = HostnameVerifier { _: String, _: SSLSession -> true }
                }
                return connection
            }
        }
        return Volley.newRequestQueue(context, hurlStack)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_videos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val lvVideos = findViewById<ListView>(R.id.lvVideos)
        val queue = getUnsafeQueue(this)
        val url = "https://apiws.uteq.edu.ec/h6RPoSoRaah0Y4Bah28eew/functions/information/entity/3"

        val request = object : JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                val lstVideos = ArrayList<Video>()
                val limit = minOf(10, response.length())
                for (i in 0 until limit) {
                    val obj = response.getJSONObject(i)
                    lstVideos.add(
                        Video(
                            titulo = obj.getString("titulo"),
                            fechapub = obj.getString("fechapub"),
                            urlvideo1 = obj.getString("urlvideo1"),
                            portadaVideo = obj.getString("portadaVideo")
                        )
                    )
                }
                val adapter = VideoAdapter(this, lstVideos)
                lvVideos.adapter = adapter
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJfeDF1c2VyZGV2IiwiaWF0IjoxNzgxMjA2NDgwLCJleHAiOjE3ODEyOTI4ODB9.ut9t7jNdM2ubQhp0EZCCytNYR2IQQPmlyoO51V2laGE"
                return headers
            }
        }
        queue.add(request)
    }
}
