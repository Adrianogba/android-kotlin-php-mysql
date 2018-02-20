package io.github.adrianogba.crud_kotlin.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import java.util.ArrayList

import io.github.adrianogba.crud_kotlin.R

import io.github.adrianogba.crud_kotlin.model.Veiculo

/**
 * Created by Adrianogba on 12/26/2017.
 */

class VeiculoListAdapter(private var activity: Activity, private var items: ArrayList<Veiculo>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txtTitle: TextView? = null

        init {
            this.txtTitle = row?.findViewById(R.id.textModelo)
        }
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_veiculo, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val veiculo = items[position]
        viewHolder.txtTitle?.text = veiculo.modelo


        return view as View
    }

    override fun getItem(i: Int): Veiculo {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }


}