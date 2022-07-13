package com.example.marvelapp.model

data class ResponseEvent(val code:Int, val status:String, var data: EventList)
data class EventList(val offset:Int, val limit:Int, val total:Int, val count:Int,
                     var results:List<Event>)
data class Event(val id:String, val title:String, val description:String, val modified:String, val thumbnail: Thumbnail)
data class Thumbnail(val path:String, val extension:String)
