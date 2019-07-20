package quizcounter.geeks.compete.androidpractical_iba

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import quizcounter.geeks.compete.androidpractical_iba.databinding.ActivityMainBinding
import quizcounter.geeks.compete.androidpractical_iba.models.ImageData
import quizcounter.geeks.compete.androidpractical_iba.volley.NetworkUtility
import quizcounter.geeks.compete.androidpractical_iba.volley.VolleyCallBack

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SimpleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val binding: quizcounter.geeks.compete.androidpractical_iba.databinding.ActivityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        viewModel = ViewModelProviders.of(this)[SimpleViewModel::class.java]

        binding.setLifecycleOwner(this);
        binding.post = viewModel.imagedata

        binding.executePendingBindings()

        val API = "http://php.dev.drcsystems.ooo:8083/php-projects/basic_auth/"
        NetworkUtility.makeSimplerequest(API,object : VolleyCallBack {
            override fun onSuccess(result: String) {
              Log.e("Response", "::"+result)
//                viewModel.imagedata.notifyChange()
            }
        })
    }
}
