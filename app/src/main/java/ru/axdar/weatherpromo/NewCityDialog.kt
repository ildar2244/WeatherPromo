package ru.axdar.weatherpromo

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_new_city.view.*

/** Created by qq_3000 on 24.09.2019. */
class NewCityDialog : DialogFragment() {
    private lateinit var mListener: Listener

    interface Listener {
        fun onInputCityConfirm(cityName: String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mListener = context as Listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_new_city, null)
        return AlertDialog.Builder(context!!)
            .setView(view)
            .setTitle(getString(R.string.dialog_input_city))
            .setPositiveButton(android.R.string.ok) { _, _ ->
                mListener.onInputCityConfirm(view.city_input.text.toString())
            }
            .setNegativeButton(android.R.string.cancel) {_,_ ->

            }
            .create()
    }
}