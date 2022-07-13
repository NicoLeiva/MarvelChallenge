package com.example.marvelapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marvelapp.model.CharacterData
import com.example.marvelapp.databinding.FragmentDetailsCharacterBinding
import com.squareup.picasso.Picasso


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DetailsCharacterFragment : Fragment() {

    private lateinit var binding:FragmentDetailsCharacterBinding

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsCharacterBinding.inflate(layoutInflater,container,false)
        binding.description.text = param1
        val url = "${param2}.${"jpg"}".replace("http", "https")
        Picasso.get().load(url).into(binding.imageView)

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(characterData: CharacterData) =
            DetailsCharacterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, characterData.name)
                    putString(ARG_PARAM2, characterData.thumbnail.path)
                }
            }
    }
}