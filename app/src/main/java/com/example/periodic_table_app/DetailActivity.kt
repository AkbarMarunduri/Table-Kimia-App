package com.example.periodic_table_app

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.periodic_table_app.databinding.ActivityDetailBinding
import com.example.periodic_table_app.model.Cell
import kotlinx.android.synthetic.main.toolbar_main.*
import java.io.File
import java.io.OutputStream

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
        binding.apply {
            tvStandardState.text = cell.StandardState
            tvAtomicMass.text = cell.AtomicMass
            tvElectronConfiguration.text = cell.ElectronConfiguration
            tvOxidationStates.text = cell.OxidationStates
            tvElectronegativity.text = cell.Electronegativity
            tvAtomicRadiusls.text = cell.AtomicRadius
            tvIonizationEnergy.text = cell.IonizationEnergy
            tvElectronAffinity.text = cell.ElectronAffinity
            tvMeltingPointy.text = cell.MeltingPoint
            tvBoilingPoint.text = cell.BoilingPoint
            tvDensity.text = cell.Density
            tvYearDiscovered.text = cell.YearDiscovered
            tvGroupBlock.text = cell.GroupBlock
        }
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
            bitmap, "title", null
        )
        val uri=Uri.parse(pathofbmp)

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setType("image/*")
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Periodic App")
        shareIntent.putExtra(Intent.EXTRA_TEXT, "")
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(shareIntent, "Hello"))

    }

    fun getUriScreen(bitmap:Bitmap):Uri {
        val filenaame = "IMG_${System.currentTimeMillis()}.jpeg"
        var fos: OutputStream? = null
        var imageUri: Uri? = null
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filenaame)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        }

        val contentResolver = application.contentResolver

        contentResolver.also { resolve ->
            imageUri = resolve.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolve.openOutputStream(it) }
        }

        fos?.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 70, it) }
        contentValues.clear()
        contentValues.put(MediaStore.Video.Media.IS_PENDING, 1)
        contentResolver.update(imageUri!!, contentValues, null, null)

        return imageUri!!
    }

}