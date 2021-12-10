package fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.basicnavigation.databinding.FragmentRightBinding
import org.json.JSONObject


class RightFragment : Fragment() {
    private lateinit var queue: RequestQueue
    private lateinit var binding: FragmentRightBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentRightBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val etPokemonName = binding.etPokemonToSearchFor
        queue = Volley.newRequestQueue(context)
        binding.btnSearch.setOnClickListener(){
            getPokemon(etPokemonName.text.toString())
            etPokemonName.text.clear()
        }
    }

    @SuppressLint("SetTextI18n")
    fun getPokemon(pokemonName: String){
        val url ="https://pokeapi.co/api/v2/pokemon/${pokemonName}"
        val jsonRequest = JsonObjectRequest(url, { response->
                val name = response.getString("name")
                val id = response.getString("id")
                // hp,ataque,defesa,velocidad, peso, tipo
                val tipo = response.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name")
                val  hp  = response.getJSONArray("stats").getJSONObject(0).getString("base_stat")
                val attack  = response.getJSONArray("stats").getJSONObject(1).getString("base_stat")
                val deffense  = response.getJSONArray("stats").getJSONObject(2).getString("base_stat")
                val speed  = response.getJSONArray("stats").getJSONObject(5).getString("base_stat")
                val weight = response.getString("weight")
                val infoString = "Nombre: ${name.replaceFirstChar { it.uppercase() }} #: $id \nTipo: $tipo \nPuntos de Salud: $hp \nAtaque: $attack \nDefensa: $deffense  \nVelocidad: $speed \nPeso: $weight"
                binding.tvPokemonInfo.setText(infoString)
            },
            { errorMessage->
                binding.tvPokemonInfo.setText("404 Pokemon Not found")
                Log.d("JSONResponse","Error: $errorMessage")
            }
        )
        queue.add(jsonRequest)
    }

    override fun onStop() {
        super.onStop()
        queue.cancelAll("stopped")
    }

}