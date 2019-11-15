package com.example.pokedex.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.activity.MainActivity
import com.example.pokedex.modal.ListPokemon
import com.example.pokedex.modal.Name_Url
import com.example.pokedex.modal.Pokemon
import com.example.pokedex.services.RetrofitInitializer
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokeAdapter(private val context: Context, val listener: MainActivity): RecyclerView.Adapter<PokeAdapter.PokeViewHolder>() {

    private val pokemons = mutableListOf<Name_Url>()

    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val view = layoutInflater.inflate(R.layout.poke_cardview, parent, false)

        return PokeViewHolder(view, context, listener)
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        val pokemon = pokemons[position]

        val path = pokemon.url.substring(34)
        val call = RetrofitInitializer.pokeApi.pokeService().pokemon(path)



        call.enqueue(object: Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                response.body()?.let {
                    Picasso.get().load(it.sprites.front_default).into(holder.imagem)
                    holder.nome.text = pokemonNumber(it.id) + it.name
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }

    class PokeViewHolder(view: View, context: Context, listener: PokemonClickListener): RecyclerView.ViewHolder(view) {
        val imagem: ImageView = view.findViewById(R.id.pokeImagem)
        val nome: TextView = view.findViewById(R.id.pokeTexto)

        init {
            view.setOnClickListener{
                listener.onClick(it, adapterPosition)
            }
        }
    }

    fun updateList(pokemons: List<Name_Url>) {
        this.pokemons.clear()
        this.pokemons.addAll(pokemons)
        notifyDataSetChanged()
    }

    fun pokemonNumber(number: Int): String {
        if (number < 10) {
            return "#00$number - "
        } else if (number < 100) {
            return "#0$number - "
        } else {
            return "#$number - "
        }
    }
}