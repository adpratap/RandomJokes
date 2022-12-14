package com.noreplypratap.jokes.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.noreplypratap.jokes.R
import com.noreplypratap.jokes.constants.Constants
import com.noreplypratap.jokes.databinding.FragmentSavedBinding
import com.noreplypratap.jokes.utils.JokesAdapter
import com.noreplypratap.jokes.viewmodel.DbViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment(R.layout.fragment_saved) {

    private lateinit var binding : FragmentSavedBinding
    private lateinit var jokesAdapter: JokesAdapter
    private val dbViewModel : DbViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSavedBinding.bind(view)
        setupObservers()
        binding.btnDeleteAll.setOnClickListener {
            setupDialogBox(0,false)
            Log.d(Constants.TAG,"DeletedAll.....")
        }

    }

    private fun setupObservers() {
        dbViewModel.getJokesFromDB().observe(viewLifecycleOwner, Observer {
            jokesAdapter = JokesAdapter(it)
            setupRecycler()
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setupDialogBox(id : Int,boolean: Boolean){
        val builder = context?.let { AlertDialog.Builder(it) }
        if (boolean){
            builder?.setTitle("Alert")
            builder?.setMessage("Do you want to delete this joke ...")
        }else{
            builder?.setTitle("Alert")
            builder?.setMessage("Do you want to delete All ...")
        }
        builder?.setPositiveButton("Yes") { dialog, which ->
            Toast.makeText(context,
                "Yes", Toast.LENGTH_SHORT).show()

            if (boolean){
                dbViewModel.deleteJokeFromDB(id)
                jokesAdapter.notifyDataSetChanged()
            }else{
                dbViewModel.deleteDB()
                jokesAdapter.notifyDataSetChanged()
            }
        }

        builder?.setNegativeButton("No") { dialog, which ->
            Toast.makeText(context,
                "No", Toast.LENGTH_SHORT).show()
        }
        builder?.show()
    }

    private fun setupRecycler() {
        binding.rvOfflineJokes.apply {
            adapter = jokesAdapter
            layoutManager = GridLayoutManager(activity,1)
        }
        jokesAdapter.setOnClickListener {
            setupDialogBox(it.id,true)
        }

    }
}