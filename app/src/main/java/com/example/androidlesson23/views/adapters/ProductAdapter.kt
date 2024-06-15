package com.example.androidlesson23.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlesson23.databinding.ItemProductBinding
import com.example.androidlesson23.models.responses.get.product.Product

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.UserViewHolder>() {

    private val List = arrayListOf<Product>()

    lateinit var onClickItem: (String) -> Unit



    inner class UserViewHolder(val itemBinding: ItemProductBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return List.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val item = List[position]

        holder.itemBinding.product = item

        holder.itemBinding.productMaterialCardView.setOnClickListener {
            onClickItem.invoke(item.id.toString())


        }

    }




    fun updateList(newList: List<Product>) {
        List.clear()
        List.addAll(newList)
        notifyDataSetChanged()
    }


}