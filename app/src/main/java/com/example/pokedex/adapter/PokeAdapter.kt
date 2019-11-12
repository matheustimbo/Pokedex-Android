package com.example.pokedex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.modal.Pokemon
import com.squareup.picasso.Picasso

class PokeAdapter(private val context: Context, private val pokemons: List<Pokemon>): RecyclerView.Adapter<PokeAdapter.PokeViewHolder>() {

    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val view = layoutInflater.inflate(R.layout.poke_cardview, parent, false)

        return PokeViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        val pokemon = pokemons[position]
        Picasso.get().load(pokemon.sprites.front_default).into(holder.imagem)
        holder.nome.text = pokemon.name
    }

    class PokeViewHolder(view: View, context: Context): RecyclerView.ViewHolder(view) {
        val imagem: ImageView = view.findViewById(R.id.pokeImagem)
        val nome: TextView = view.findViewById(R.id.pokeTexto)
    }
}