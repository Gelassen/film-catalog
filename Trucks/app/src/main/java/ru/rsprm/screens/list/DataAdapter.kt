package ru.rsprm.screens.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.rsprm.R
import ru.rsprm.model.Truck

class DataAdapter(val listener : Listener) : androidx.recyclerview.widget.RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    interface Listener {
        fun onRemove(id: Int)

        fun onItemClick(truck: Truck)
    }

    private val model = ArrayList<Truck>()

    fun updateModel(model: List<Truck>) {
        this.model.clear()
        this.model.addAll(model)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, pos: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_item_truck, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, pos: Int) {
        val truck = model.get(pos)

        viewHolder.name.setText(truck.name)
        viewHolder.price.setText(truck.price)
        viewHolder.comment.setText(truck.comment)
        viewHolder.removeWrapper.setOnClickListener {
            listener?.onRemove(Integer.parseInt(truck.id))
        }
        viewHolder.itemView.setOnClickListener {
            listener?.onItemClick(truck)
        }
    }

    override fun getItemCount(): Int {
        return model.size
    }

    fun onRemoveItem(id: Int) {
        val iterator = model.iterator()
        while (iterator.hasNext()) {
            val truck = iterator.next()
            if (truck.id!!.toInt() == id) {
                val position = model.indexOf(truck)
                iterator.remove()
                notifyItemRemoved(position)
            }
        }
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val name: TextView
        val price: TextView
        val comment: TextView
        val remove: ImageView
        val removeWrapper: View

        init {
            name = itemView.findViewById(R.id.name)
            price = itemView.findViewById(R.id.price)
            comment = itemView.findViewById(R.id.comment)
            remove = itemView.findViewById(R.id.remove)
            removeWrapper = itemView.findViewById(R.id.removeWrapper)
        }
    }

}
