package com.example.utils.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.utils.R
import com.example.utils.activity.adapter.MainAdapter
import com.example.utils.learn.xfermode.XfermodeActivity
import com.example.utils.utils.AppManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_main_)
        initRecyclerView(findViewById(R.id.recyclerview))
    }

    //    fun click(v : View) {
//        var view = findViewById<View>(R.id.ll)
//        FileUtils.saveBitmap(this, ImageUtils.getViewBitmap(view, watermarkText = "水印", watermarkColor = ContextCompat.getColor(this, R.color.color_transparency)))
//    }
    private fun initRecyclerView(recyclerView : RecyclerView) {
        var adapter = MainAdapter(this, object : MainAdapter.IChildItemClick {
            override fun click(position : Int) {
                when (position) {
                    0 -> {
                        AppManager.jump(this@MainActivity, XfermodeActivity::class.java)
                    }
                }
            }
        })
        adapter.datas = getLearns()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setOnClickListener { }
    }

    private fun getLearns() : MutableList<String> {
        return mutableListOf("Xfermode")
    }
}