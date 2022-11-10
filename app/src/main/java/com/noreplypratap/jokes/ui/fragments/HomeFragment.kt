package com.noreplypratap.jokes.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.noreplypratap.jokes.R
import com.noreplypratap.jokes.constants.Constants
import com.noreplypratap.jokes.databinding.FragmentHomeBinding
import com.noreplypratap.jokes.model.JokesData
import com.noreplypratap.jokes.utils.isOnline
import com.noreplypratap.jokes.viewmodel.APIViewModel
import com.noreplypratap.jokes.viewmodel.DbViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val apiViewModel : APIViewModel by viewModels()
    private val dbViewModel : DbViewModel by viewModels()
    private var saveJoke : JokesData? = null

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.lifecycleOwner = this
        binding.apijoke = apiViewModel

        setupObservers()

        reFreshJoke()

        binding.btnReload.setOnClickListener {
            if (context?.isOnline() == true){
                reFreshJoke()
            }else{
                offlineView()
            }
        }

        binding.btnSave.setOnClickListener {
            saveToDB()
        }
    }

    private fun saveToDB() {
        saveJoke?.let { it1 -> dbViewModel.saveJokesIntoDB(it1)
            Log.d(Constants.TAG,"Saved To DB...... $it1")
            Toast.makeText(context, "Saved In DB", Toast.LENGTH_SHORT).show()
        }
    }

    private fun offlineView(){
        binding.tvOnlineData.text = "No Internet"
        binding.tvOnlineData.visibility = View.VISIBLE
        binding.tvOnlinePunchLine.text = "Please check your internet and restart ..."
        binding.tvOnlinePunchLine.visibility = View.VISIBLE
        binding.btnSave.visibility = View.GONE
        binding.tvJokeView.visibility = View.GONE
        binding.tvPunchLine.visibility = View.GONE
    }

    private fun onlineView(){
        binding.tvOnlineData.visibility = View.GONE
        binding.tvOnlinePunchLine.visibility = View.GONE
        binding.btnSave.visibility = View.VISIBLE
        binding.tvJokeView.visibility = View.VISIBLE
        binding.tvPunchLine.visibility = View.VISIBLE
    }

    private fun setupObservers() {
        apiViewModel.jokes.observe(viewLifecycleOwner, Observer {
            it.data?.let { it1 -> Log.d(Constants.TAG, it1.setup)
                saveJoke = it1
                stopProgressBar()
            }
        })
    }

    private fun reFreshJoke(){
        if (context?.isOnline() == true){
            startProgressBar()
            apiViewModel.getJokesFromAPI()
            onlineView()
        }else{
            startProgressBar()
            offlineView()
        }
    }

    private fun startProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun stopProgressBar(){
        binding.progressBar.visibility = View.GONE
    }

}