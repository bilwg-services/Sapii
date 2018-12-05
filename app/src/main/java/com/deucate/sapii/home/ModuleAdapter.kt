package com.deucate.sapii.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deucate.sapii.R
import kotlinx.android.synthetic.main.card_module.view.*

class ModuleAdapter(private val viewModel: HomeViewModel) : RecyclerView.Adapter<ModuleViewHolder>() {

    lateinit var listner: OnCallBack

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        return ModuleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_module,parent, false))
    }

    override fun getItemCount(): Int {
        return viewModel.modules.value!!.size
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        val module = viewModel.modules.value!![position]

        holder.imageView.setImageResource(module.resourceID)
        holder.titleTV.text = module.title
        holder.descriptionTv.text = module.detail

        holder.cardView.setOnClickListener {
            listner.onClickCard(module)
        }
    }

    interface OnCallBack {
        fun onClickCard(module: Module)
    }

}

class ModuleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val cardView = view.cardView!!
    val imageView = view.image!!
    val titleTV = view.title!!
    val descriptionTv = view.description!!
}