package com.example.pokedex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.modal.Pokemon

class AdapterEvolution(private val context: Context, val pokemons: List<Pokemon>): RecyclerView.Adapter<AdapterEvolution.PokeViewHolder>() {

    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val view = layoutInflater.inflate(R.layout.card_evolution, parent, false)

        return PokeViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        val pokemon = pokemons[position]


    }

    class PokeViewHolder(view: View, context: Context): RecyclerView.ViewHolder(view) {
        val imagem1: ImageView = view.findViewById(R.id.poke_1)
        val imagem2: ImageView = view.findViewById(R.id.poke_2)
    }
}