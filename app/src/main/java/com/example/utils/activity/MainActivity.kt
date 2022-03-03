package com.example.utils.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.utils.R
import com.example.utils.activity.adapter.MainAdapter
import com.example.utils.learn.xfermode.XfermodeActivity
import com.example.utils.utils.AppManager

class MainActivity : AppCompatActivity() {
    private val adapter : MainAdapter by lazy {
        MainAdapter(this, object : MainAdapter.IChildItemClick {
            override fun click(position : Int) {
                var mClass = adapter.datas.getOrNull(position)?.get("class")
                if (mClass != null) {
                    mClass as Class<out Activity?>?
                    AppManager.jump(this@MainActivity, mClass)
                }
            }
        })

    }

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
        adapter.datas = getLearns()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setOnClickListener { }
    }

    private fun getLearns() : MutableList<Map<String, Any>> {
        return mutableListOf(mapOf("name" to "Xfermode", "class" to XfermodeActivity::class.java))
    }
}