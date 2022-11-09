package com.example.periodic_table_app.util

import android.content.Context
import com.example.periodic_table_app.model.Cell
import com.example.periodic_table_app.model.ResourceData
import com.google.gson.Gson
import java.io.IOException

class Database(var context: Context) {
    var gson = Gson()

    //load data json dari file assets
    private fun jsonFileLoader(): ResourceData? {
        var jsonstring: ResourceData
        try {
            val data =
                context.assets.open("periodic_data.json").bufferedReader().use { it.readText() }
            jsonstring = gson.fromJson(data, ResourceData::class.java)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonstring
    }

    //mengubah data  json ke dalam object List(Cell)
    fun getAllCell(): List<Cell> {
        val rows = jsonFileLoader()?.Table?.Row!!
        var data = ArrayList<Cell>()

        for (cell in rows) {
            data.add(
                Cell(
                    cell.Cell.get(0),
                    cell.Cell.get(1),
                    cell.Cell.get(2),
                    cell.Cell.get(3),
                    cell.Cell.get(4),
                    cell.Cell.get(5),
                    cell.Cell.get(6),
                    cell.Cell.get(7),
                    cell.Cell.get(8),
                    cell.Cell.get(9),
                    cell.Cell.get(10),
                    cell.Cell.get(11),
                    cell.Cell.get(12),
                    cell.Cell.get(13),
                    cell.Cell.get(14),
                    cell.Cell.get(15),
                    cell.Cell.get(16)
                )
            )
        }
        return data
    }
}