package com.example.marvelapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.marvelapp.databinding.FragmentDetailsCharacterBinding
import com.example.marvelapp.model.CharacterData
import com.example.marvelapp.utils.ImageUtils.createUrlImage
import com.squareup.picasso.Picasso

private const val CHARACTER_DATA = "characterData"

class DetailsCharacterFragment : Fragment() {

    private lateinit var binding: FragmentDetailsCharacterBinding
    private var data: CharacterData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getSerializable(CHARACTER_DATA) as CharacterData?
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = FragmentDetailsCharacterBinding.inflate(layoutInflater, container, false)
        setCharacterDetails()
        return binding.root
    }

    private fun setCharacterDetails() {
        binding.name.text = data?.name
        val url = data?.thumbnail?.extension?.let {
            data?.thumbnail?.path?.let { it1 ->
                createUrlImage(
                    it1, it
                )
            }
        }
        Picasso.get().load(url).into(binding.imageView)
        binding.subtitle.text = data?.description
        binding.close.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(characterData: CharacterData) =
            DetailsCharacterFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(CHARACTER_DATA, characterData)
                }
            }
    }

}