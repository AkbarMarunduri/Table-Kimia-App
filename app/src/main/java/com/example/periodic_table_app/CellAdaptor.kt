package com.example.periodic_table_app

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.periodic_table_app.databinding.CardCellItemBinding
import com.example.periodic_table_app.model.Cell

class CellAdaptor(private val cells: List<Cell>, var context: Context) :
    RecyclerView.Adapter<CellAdaptor.CellHolder>() {

    private lateinit var onClickCellCallBack: OnClickCellCallback

    fun setOnClickCellListener(onClickCellCallBack: OnClickCellCallback) {
        this.onClickCellCallBack = onClickCellCallBack
    }

    inner class CellHolder(var binding: CardCellItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellHolder {
        val view = CardCellItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CellHolder(view)
    }

    override fun onBindViewHolder(holder: CellHolder, position: Int) {
        val image = cells[holder.adapterPosition].Name.lowercase()
        val imagelink = context.resources.getIdentifier(image, "drawable", context.packageName)
        Glide.with(context)
            .load(imagelink)
            .fitCenter()
            .into(holder.binding.imageCell)

        holder.binding.imageCell.setOnClickListener {
            onClickCellCallBack.onClick(cells[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return cells.size
    }

    interface OnClickCellCallback {
        fun onClick(cell: Cell)
    }
}