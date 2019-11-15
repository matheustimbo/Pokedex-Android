package com.example.pokedex.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.adapter.PokeAdapter
import com.example.pokedex.modal.ListPokemon
import com.example.pokedex.services.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var pokeList: RecyclerView
    private lateinit var adapter: PokeAdapter
    private var loading = true
    private var incrementPokemon = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokeList = findViewById(R.id.main_recyclerview)
        pokeList.layoutManager = LinearLayoutManager(this)

        adapter = PokeAdapter(this)

        pokeList.adapter = adapter

        var pastVisiblesItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int

        pokeList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0)
                //check for scroll down
                {
                    visibleItemCount = pokeList.childCount
                    totalItemCount = pokeList.layoutManager!!.itemCount
                    pastVisiblesItems = (pokeList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false
                            Log.v("...", "Last Item Wow !")
                            carregarApi()
                        }
                    }
                }
            }
        })

        carregarApi()
    }

    fun carregarApi() {
        val call = RetrofitInitializer.pokeApi.pokeService().ListPokemon(0, incrementPokemon * 20)

        call.enqueue(object: Callback<ListPokemon> {
            override fun onResponse(call: Call<ListPokemon>, response: Response<ListPokemon>) {
                response.body()?.let {
                    adapter.updateList(it.results)
                    loading = true
                    incrementPokemon++
                }
            }

            override fun onFailure(call: Call<ListPokemon>, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }
}
