package com.example.marvelapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.marvelapp.R
import com.example.marvelapp.model.Event
import com.example.marvelapp.utils.DateUtils.parseDate
import com.example.marvelapp.utils.ImageUtils.createUrlImage
import com.squareup.picasso.Picasso

class ExpansibleListViewAdapter internal constructor(private val context:Context,
                                                     private val headerList:List<Event>, private val detailsList: HashMap<String, List<Event>>):
    BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return headerList.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return detailsList[this.headerList[p0].id]!!.size
    }

    override fun getGroup(p0: Int): Any {
        return headerList[p0]
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return this.detailsList[this.headerList[p0].id]!![p1]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p0.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        var convertView = p2
        val event = getGroup(p0) as Event
        if( convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.header_list,null)
        }
        val headerTV = convertView!!.findViewById<TextView>(R.id.character_title)
        val dateTV = convertView.findViewById<TextView>(R.id.character_subtitle)
        val imageView = convertView.findViewById<ImageView>(R.id.imageView)
        headerTV.text = event.title
        val url = createUrlImage(event.thumbnail.path,event.thumbnail.extension)
        Picasso.get().load(url).into(imageView)
        dateTV.text = parseDate(event.modified)
        return convertView
    }


    @SuppressLint("InflateParams")
    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        var convertView = p3
        val detailsText = getChild(p0, p1) as Event
        if( convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.details_list,null)
        }
        val detailsTV = convertView!!.findViewById<TextView>(R.id.details_textView)
        detailsTV.text = detailsText.description

        return convertView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

}