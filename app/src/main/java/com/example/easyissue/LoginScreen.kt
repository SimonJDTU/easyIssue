package com.example.easyissue

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.easyissue.data.ProjectSearchApi
import com.example.easyissue.data.ProjectSearchModel
import com.example.easyissue.data.ProjectWebService
import com.example.easyissue.databinding.LoginScreenBinding
import retrofit2.Call
import retrofit2.Callback

class LoginScreen : Fragment() {

    private lateinit var viewModel: LoginScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<LoginScreenBinding>(
            inflater, R.layout.login_screen, container, false)

        viewModel = ViewModelProvider(this).get(LoginScreenViewModel::class.java)

        binding.viewModel = viewModel

        Log.d("yeet","yeet")


        /**
        val request = ProjectWebService.retrofitInstance
        val call = request.getMovies(getString(R.string.api_key))

        call.enqueue(object : Callback<PopularMovies>{
            override fun onResponse(call: Call<PopularMovies>, response: Response<PopularMovies>) {
                if (response.isSuccessful){
                    progress_bar.visibility = View.GONE
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = MoviesAdapter(response.body()!!.results)
                    }
                }
            }
            override fun onFailure(call: Call<PopularMovies>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

         ??????????????????????????????????
        **/
        return binding.root
    }
}