package com.example.pokedex.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.adapter.PokeAdapter
import com.example.pokedex.adapter.PokemonClickListener
import com.example.pokedex.modal.ListPokemon
import com.example.pokedex.modal.Name_Url
import com.example.pokedex.services.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity: AppCompatActivity(), PokemonClickListener {

    private lateinit var pokeList: RecyclerView
    private lateinit var adapter: PokeAdapter
    private val pokemons = mutableListOf<Name_Url>()
    private var lastClickTime = 0L
    private var loading = true
    private var incrementPokemon = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokeList = findViewById(R.id.main_recyclerview)
        pokeList.layoutManager = LinearLayoutManager(this)

        adapter = PokeAdapter(this, this)

        pokeList.adapter = adapter

        var pastVisiblesItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int

        pokeList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {

                    visibleItemCount = pokeList.childCount
                    totalItemCount = pokeList.layoutManager!!.itemCount
                    pastVisiblesItems = (pokeList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false
                            carregarApi()
                        }
                    }
                }
            }
        })

        carregarApi()
    }

    fun carregarApi() {
        val call = RetrofitInitializer.pokeApi.pokeService().ListPokemon(incrementPokemon * 20)

        call.enqueue(object: Callback<ListPokemon> {
            override fun onResponse(call: Call<ListPokemon>, response: Response<ListPokemon>) {
                response.body()?.let {
                    pokemons.addAll(it.results)
                    adapter.updateList(pokemons)
                    incrementPokemon++
                    loading = true
                }
            }

            override fun onFailure(call: Call<ListPokemon>, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }

    override fun onClick(view: View, position: Int) {
        if (SystemClock.elapsedRealtimeNanos() - lastClickTime < 800) {
            return
        }
        lastClickTime = SystemClock.elapsedRealtime()

        val it = Intent(this, PokemonDetailsActivity::class.java)
//        val img = view.findViewById<View>(R.id.imageViewCelebrityDetails)
//        val option = ActivityOptionsCompat.makeSceneTransitionAnimation(this, img, ViewCompat.getTransitionName(img)!!)

        it.putExtra("pokemon_position", position)
        startActivity(it)
    }
}
