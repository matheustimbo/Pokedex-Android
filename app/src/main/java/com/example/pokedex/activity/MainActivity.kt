package com.example.pokedex.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.adapter.PokeAdapter
import com.example.pokedex.adapter.PokemonClickListener
import com.example.pokedex.modal.ListPokemon
import com.example.pokedex.modal.Name_Url
import com.example.pokedex.modal.Pokemon
import com.example.pokedex.services.RetrofitInitializer
import com.example.pokedex.util.Type
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity: AppCompatActivity(), PokemonClickListener {

    private lateinit var pokeList: RecyclerView
    private lateinit var adapter: PokeAdapter
    private val pokemons = mutableListOf<Name_Url>()
    private val pokeLive = MutableLiveData<List<Pokemon>>()
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

        pokeLive.observe(this, Observer {
            adapter.updateList(it)
            progress_bar_main.visibility = View.GONE
        })
    }

    fun carregarApi() {
        val call = RetrofitInitializer.pokeApi.pokeService().ListPokemon(incrementPokemon * 20)

        call.enqueue(object: Callback<ListPokemon> {
            override fun onResponse(call: Call<ListPokemon>, response: Response<ListPokemon>) {
                response.body()?.let {
                    pokemons.addAll(it.results)
//                    adapter.updateList(pokemons)

                    urlToPokemon(it.results)
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

    fun urlToPokemon(lista: List<Name_Url>) {
        progress_bar_main.visibility = View.VISIBLE

        val poke = mutableListOf<Pokemon>()
        var count = 0

        lista.forEach {
            val path = it.url.substring(34)
            val call = RetrofitInitializer.pokeApi.pokeService().pokemon(path)

            call.enqueue(object: Callback<Pokemon> {
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    response.body()?.let { currentPokemon ->
                        poke.add(currentPokemon)
                        count++

                        if(count === 20) {
                            pokeLive.postValue(poke)
                        }
                    }
                }

                override fun onFailure(call: Call<Pokemon>, t: Throwable?) {
                    count++
                    Log.e("onFailure error", t?.message)
                }
            })
        }
    }
}
