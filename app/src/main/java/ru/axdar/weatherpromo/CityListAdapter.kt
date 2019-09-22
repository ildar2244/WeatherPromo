package ru.axdar.weatherpromo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.city_item.view.*
import ru.axdar.weatherpromo.local.CityEntity

/** Created by qq_3000 on 19.09.2019. */
class CityListAdapter(context: Context) : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {

    private var cityList = emptyList<CityEntity>()

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.city_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = cityList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cityList[position]
        with(holder) {
            view.city_text.text = city.name
            view.temperature_text.text =
                view.context.getString(R.string.temperature, city.temperature.toString())
        }
    }

    internal fun setCityList(cityList: List<CityEntity>) {
        this.cityList = cityList
        notifyDataSetChanged()
    }

}