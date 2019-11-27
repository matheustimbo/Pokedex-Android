package com.example.pokedex.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Xml
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.example.pokedex.R
import com.example.pokedex.modal.Pokemon
import com.example.pokedex.modal.evolution.Evolution
import com.example.pokedex.modal.specie.Specie
import com.example.pokedex.services.RetrofitInitializer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_fragment_evolution.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentEvolution : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        arguments?.let {
            pokemonSpecies(it.getInt("id"))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_fragment_evolution, container, false)

        return root
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(pokemon: Int) =
            FragmentEvolution().apply {
                arguments = Bundle().apply {
                    putInt("id", pokemon)
                }
            }
    }

    fun pokemonSpecies(pokemonNumber: Int) {
        val call = RetrofitInitializer.pokeApi.pokeService().pokemonSpecie(pokemonNumber.toString())

        call.enqueue(object: Callback<Specie> {
            override fun onResponse(call: Call<Specie>, response: Response<Specie>) {
                response.body()?.let {
                    val chain = it.evolution_chain.url.substring(42)

                    evolutionChain(chain)
                }
            }

            override fun onFailure(call: Call<Specie>, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }

    fun evolutionChain(endPoint: String) {
        val call = RetrofitInitializer.pokeApi.pokeService().evolution(endPoint)

        call.enqueue(object: Callback<Evolution> {
            override fun onResponse(call: Call<Evolution>, response: Response<Evolution>) {
                response.body()?.let { evolution ->
                    var chain = evolution.chain.evolves_to
                    var count = 1
                    val pokeName = mutableListOf<String>()
                    pokeName.add(evolution.chain.species.name)

                    while (chain.size != 0) {
                        count++
                        pokeName.add(chain[0].species.name)
                        chain = chain[0].evolves_to
                    }

                    when (count){
                        1 -> {
                            poke_layout1.visibility = View.VISIBLE
                            poke_nome1.text = pokeName[0]
                            carregarPokemon(pokeName[0], poke_imagem1)
                        }

                        2 -> {
                            poke_layout1.visibility = View.VISIBLE
                            carregarPokemon(pokeName[0], poke_imagem1)
                            poke_nome1.text = pokeName[0]

                            poke_layout2.visibility = View.VISIBLE
                            carregarPokemon(pokeName[1], poke_imagem2)
                            poke_nome2.text = pokeName[1]
                        }

                        3 -> {
                            poke_layout1.visibility = View.VISIBLE
                            carregarPokemon(pokeName[0], poke_imagem1)
                            poke_nome1.text = pokeName[0]

                            poke_layout2.visibility = View.VISIBLE
                            carregarPokemon(pokeName[1], poke_imagem2)
                            poke_nome2.text = pokeName[1]

                            poke_layout3.visibility = View.VISIBLE
                            carregarPokemon(pokeName[2], poke_imagem3)
                            poke_nome3.text = pokeName[2]
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Evolution>, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }

    fun carregarPokemon(pokemonName: String, target: ImageView) {
        val call = RetrofitInitializer.pokeApi.pokeService().pokemon(pokemonName)

        call.enqueue(object: Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                response.body()?.let {
                    Picasso.get().load(it.sprites.front_default).into(target)

                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }

}
