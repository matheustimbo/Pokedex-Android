package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.TextView
import com.android.volley.Response
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.teste).text = "aaa"

//        val call = RetrofitInitializer().pokeService().pokemonList()
//        call.enqueue(object: Callback<List<ContactsContract.CommonDataKinds.Note>?> {
//            override fun onResponse(call: Call<List<ContactsContract.CommonDataKinds.Note>?>?,
//                                    response: Response<List<ContactsContract.CommonDataKinds.Note>?>?) {
//                response?.body()?.let {
//                    val notes: List<ContactsContract.CommonDataKinds.Note> = it
//                }
//            }
//
//            override fun onFailure(call: Call<List<ContactsContract.CommonDataKinds.Note>?>?,
//                                   t: Throwable?) {
//            }
//        })



    }
}
