package quizcounter.geeks.compete.androidpractical_iba.main

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import quizcounter.geeks.compete.androidpractical_iba.R
import quizcounter.geeks.compete.androidpractical_iba.models.ImageData
import quizcounter.geeks.compete.androidpractical_iba.volley.NetworkUtility
import quizcounter.geeks.compete.androidpractical_iba.volley.VolleyCallBack

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SimpleViewModel
    val itemadapter: DataItemsAdapter =
        DataItemsAdapter(this)
    var spnaCount=2;
    lateinit var mLayoutManager:GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val binding: quizcounter.geeks.compete.androidpractical_iba.databinding.ActivityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        viewModel = ViewModelProviders.of(this)[SimpleViewModel::class.java]

        mLayoutManager= GridLayoutManager(this,spnaCount)

        recyclerView.layoutManager = mLayoutManager
        recyclerView.hasFixedSize()
        recyclerView.adapter =itemadapter
        recyclerView.addItemDecoration(
            GridSpacingItemDecoration(
                2,
                14,
                true
            )
        )

        binding.setLifecycleOwner(this);
        binding.post = viewModel.imagedata

        val API = "http://php.dev.drcsystems.ooo:8083/php-projects/basic_auth/"
        NetworkUtility.makeSimplerequest(API,object : VolleyCallBack {
            override fun onSuccess(result: String) {
                itemadapter.update(Gson().fromJson(result , ImageData::class.java).urls)
            }
        })

        binding.executePendingBindings()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemmenu: MenuItem =item

        when (item.itemId) {

            R.id.action_toggle_layout -> {
                if(spnaCount>1){
                    itemmenu.setIcon(R.drawable.ic_grid)
                    spnaCount=1
                    recyclerView.layoutManager=GridLayoutManager(this@MainActivity,1)
                }else{
                    spnaCount=2
                    itemmenu.setIcon(R.drawable.ic_list)
                    recyclerView.layoutManager=GridLayoutManager(this@MainActivity,2)
                }
            }
        }
        return super.onOptionsItemSelected(item)
}

}
