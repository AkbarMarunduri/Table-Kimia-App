package com.example.periodic_table_app

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.periodic_table_app.databinding.ActivityMainBinding
import com.example.periodic_table_app.model.Cell
import com.example.periodic_table_app.util.Database


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val db = Database(this)
    lateinit var adaptor: CellAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setToolBar()
        loadListCell()

        adaptor.setOnClickCellListener(object : CellAdaptor.OnClickCellCallback {
            override fun onClick(cell: Cell) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable("data", cell)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })
    }
    
    fun setToolBar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            title = "Tabel Periodik"
            setDisplayShowHomeEnabled(true)
            setLogo(R.drawable.ic_baseline_device_thermostat_24)
            setDisplayUseLogoEnabled(true)
        }
    }

    fun loadListCell() {
        adaptor = CellAdaptor(db.getAllCell(), this)
        binding.rvCell.adapter = adaptor
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    fun onAboutAction(item: MenuItem) {
        startActivity(Intent(this@MainActivity, AboutActivity::class.java))
    }

}