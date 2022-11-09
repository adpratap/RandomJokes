package com.noreplypratap.jokes.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.noreplypratap.jokes.constants.Constants
import com.noreplypratap.jokes.databinding.FragmentHomeBinding
import com.noreplypratap.jokes.model.JokesData
import com.noreplypratap.jokes.utils.isOnline
import com.noreplypratap.jokes.viewmodel.APIViewModel
import com.noreplypratap.jokes.viewmodel.DbViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val apiViewModel : APIViewModel by viewModels()
    private val dbViewModel : DbViewModel by viewModels()
    private var saveJoke : JokesData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startProgressBar()

        binding.lifecycleOwner = this
        binding.apijoke = apiViewModel

        if (context?.isOnline() == true){
            apiViewModel.getJokesFromAPI()
            apiViewModel.jokes.observe(viewLifecycleOwner, Observer {
                it.data?.let { it1 -> Log.d(Constants.TAG, it1.setup)
                    stopProgressBar()
                    saveJoke = it1
                }
            })
        }else{
            binding.tvOnlineData.text = "No Internet"
            binding.tvOnlineData.visibility = View.VISIBLE
            binding.tvOnlinePunchLine.text = "Please check your internet and restart ..."
            binding.tvOnlinePunchLine.visibility = View.VISIBLE
            binding.btnSave.visibility = View.GONE

        }

        binding.btnSave.setOnClickListener {
            saveJoke?.let { it1 -> dbViewModel.saveJokesIntoDB(it1)
                Log.d(Constants.TAG,"Saved To DB...... $it1")
                Toast.makeText(context, "Saved In DB", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun startProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }
    fun stopProgressBar(){
        binding.progressBar.visibility = View.GONE
    }
}