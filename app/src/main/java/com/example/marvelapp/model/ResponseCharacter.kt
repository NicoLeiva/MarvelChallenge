package com.example.marvelapp.model

import java.io.Serializable

data class ResponseList(val code:Int, val status:String,val data: Data)
data class Data(val code:Int, val total: String, val count:String,val offset:Int, var results: List<CharacterData>)
data class CharacterData (val id:String, val name:String, val description:String, var thumbnail: Thumbnail):Serializable


