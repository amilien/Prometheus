package com.amilien.prometheus.view.feed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amilien.prometheus.R
import com.amilien.prometheus.model.WeatherData

class FeedAdapter(private val context: Context) : RecyclerView.Adapter<FeedViewHolder>() {

    private val items = mutableListOf<WeatherData>()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_holder_feed_item, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        with(items[position]) {
            holder.placeName.text = place
            holder.temperature.text = temperature
            holder.location.text = context.getString(R.string.coordinates, latitude, longitude)
            holder.weather.text = weather
            holder.windSpeed.text = windSpeed
            holder.humidity.text = humidity
        }
    }

    fun setItems(items: List<WeatherData>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}

class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val placeName: TextView = itemView.findViewById(R.id.item_location_timezone)
    val temperature: TextView = itemView.findViewById(R.id.item_location_temperature)
    val location: TextView = itemView.findViewById(R.id.item_location_text)
    val weather: TextView = itemView.findViewById(R.id.item_location_summary)
    val windSpeed: TextView = itemView.findViewById(R.id.item_location_wind_speed)
    val humidity: TextView = itemView.findViewById(R.id.item_location_humidity)
}
