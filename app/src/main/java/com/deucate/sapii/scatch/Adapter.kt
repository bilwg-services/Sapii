package com.deucate.sapii.scatch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cooltechworks.views.ScratchTextView
import com.deucate.sapii.R
import kotlinx.android.synthetic.main.card_scratch.view.*

class Adapter(private val prices: ArrayList<Int>) : RecyclerView.Adapter<ViewHolder>() {

    lateinit var listener: OnCallBack


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_scratch, parent, false))
    }

    override fun getItemCount(): Int {
        return prices.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.scratchTextView.text = prices[position].toString()

        holder.scratchTextView.setRevealListener(object : ScratchTextView.IRevealListener {
            override fun onRevealed(p0: ScratchTextView?) {
                listener.onRevealed(p0)
            }

            override fun onRevealPercentChangedListener(p0: ScratchTextView?, p1: Float) {
                listener.onRevealPercentChangedListener(p0, p1)
            }
        })

    }

    interface OnCallBack {
        fun onRevealed(p0: ScratchTextView?)
        fun onRevealPercentChangedListener(p0: ScratchTextView?, p1: Float)
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val cardView = view.scratchCardView!!
    val scratchTextView = view.scratchImage!!
}