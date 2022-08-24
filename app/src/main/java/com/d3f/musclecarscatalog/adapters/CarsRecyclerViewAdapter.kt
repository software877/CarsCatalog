package com.d3f.musclecarscatalog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.d3f.musclecarscatalog.R
import com.d3f.musclecarscatalog.models.CarModel

class CarsRecyclerViewAdapter(val carClickListener: (CarModel) -> Unit): RecyclerView.Adapter<CarsRecyclerViewHolder>() {

    private val cars = ArrayList<CarModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cars_recycler_view, parent, false)
        return CarsRecyclerViewHolder(view, carClickListener)
    }

    override fun onBindViewHolder(holder: CarsRecyclerViewHolder, position: Int) {
        holder.bind(cars.get(position))
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    fun setCars(cars: List<CarModel>) {
        this.cars.clear()
        this.cars.addAll(cars)
        notifyDataSetChanged()
    }

}

class CarsRecyclerViewHolder(itemView: View, val carClickListener: (CarModel) -> Unit): RecyclerView.ViewHolder(itemView) {

    private val carImage: ImageView
    private val carTitle: TextView

    init {
        carImage = itemView.findViewById(R.id.carImage)
        carTitle = itemView.findViewById(R.id.carTitle)
    }

    fun bind(car: CarModel) {
        carImage.load(car.image_url)
        carTitle.text = car.title
        itemView.setOnClickListener { carClickListener(car) }
    }

}