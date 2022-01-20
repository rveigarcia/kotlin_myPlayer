package com.mon.myplayer2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mon.myplayer2.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    companion object {      //ejemplo de companio objec, se emplea en tes fun11
        val EXTRA_ID = "extra:id"
    }

    private val adapter = MediaAdapter {
        startActivity<DetailActivity>(DetailActivity.EXTRA_ID to it.id)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
   //     job = SupervisorJob()   //Se emplea el SupervisorJob() en vez de Job() porque si se produce una excepción solo solo se cancela el job que genere la excepción y no todos los jobs
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter  //hasta esta línea no se ejecuta el código  MediaAdapter(getItems()) { toast(it.title) }
        updateItems()

    }

     private fun updateItems(filter: Filter = Filter.None){
        lifecycleScope.launch {
            binding.progress.visibility = View.VISIBLE
            adapter.items = withContext(Dispatchers.IO){getFilteredItems(filter) }
            binding.progress.visibility = View.GONE
        }
    }

    private fun getFilteredItems(filter: Filter): List<MediaItem>{
        return MediaProvider.getItems().let { media ->
            when(filter){
                Filter.None -> media  //para los objetc no se pone is
                is Filter.ByType -> media.filter { it.type == filter.type }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val filter = when (item.itemId){
            R.id.filter_photos -> Filter.ByType(MediaItem.Type.PHOTO)
            R.id.filter_videos -> Filter.ByType(MediaItem.Type.VIDEO)
            else -> Filter.None
        }

        updateItems(filter)
        return super.onOptionsItemSelected(item)
    }
}