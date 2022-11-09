package com.example.periodic_table_app

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.periodic_table_app.databinding.ActivityDetailBinding
import com.example.periodic_table_app.model.Cell
import kotlinx.android.synthetic.main.toolbar_main.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val cell = intent.extras?.get("data") as Cell
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Element Detail ${cell.Name}"
        }
        loadData(cell)
    }

    fun loadData(cell: Cell) {
        val image = cell.Name.lowercase()
        val imagelink = resources.getIdentifier(image, "drawable", this.packageName)
        Glide.with(this)
            .load(imagelink)
            .fitCenter()
            .into(binding.cellImageView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    fun onShareAction(item: MenuItem) {
        shareImage(getScreenShoot())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    //mengambil screenshot pada aplikasi yang sedang berjalan
    fun getScreenShoot(): Bitmap {
        val view = binding.cardDetail
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun shareImage(bitmap: Bitmap) {
        val pathofbmp = MediaStore.Images.Media.insertImage(
            applicationContext.contentResolver,
            bitmap,
            "title",
            null
        )
        val uri = Uri.parse(pathofbmp)
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("image/*")
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Periodic App")
        shareIntent.putExtra(Intent.EXTRA_TEXT, "")
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(shareIntent, "Hello"))
    }
}