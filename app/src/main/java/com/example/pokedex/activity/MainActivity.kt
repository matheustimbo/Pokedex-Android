package com.example.pokedex.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.pokedex.R
import com.example.pokedex.modal.Pokemon
import com.example.pokedex.services.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.teste).text = "aaa"

        val call = RetrofitInitializer.pokeApi.pokeService().pokemon()

        call.enqueue(object: Callback<Pokemon?> {
            override fun onResponse(call: Call<Pokemon?>?, response: Response<Pokemon?>?) {
                response?.body()?.let {
                    val pokemon: Pokemon? = it
                }
            }

            override fun onFailure(call: Call<Pokemon?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }
}
