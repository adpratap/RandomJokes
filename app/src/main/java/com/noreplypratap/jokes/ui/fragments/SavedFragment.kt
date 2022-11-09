package com.noreplypratap.jokes.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.noreplypratap.jokes.databinding.FragmentSavedBinding
import com.noreplypratap.jokes.utils.JokesAdapter
import com.noreplypratap.jokes.viewmodel.DbViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment() {

    private lateinit var binding : FragmentSavedBinding
    private lateinit var jokesAdapter: JokesAdapter
    private val dbViewModel : DbViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbViewModel.getJokesFromDB().observe(viewLifecycleOwner, Observer {
            jokesAdapter = JokesAdapter(it)
            setupRecycler()
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setupDialogBox(id : Int){
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle("Alert")
        builder?.setMessage("Do you want to delete...")

        builder?.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(context,
                android.R.string.yes, Toast.LENGTH_SHORT).show()
            dbViewModel.deleteJokeFromDB(id)
            jokesAdapter.notifyDataSetChanged()
        }

        builder?.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(context,
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }
        builder?.show()
    }

    private fun setupRecycler() {
        binding.rvOfflineJokes.apply {
            adapter = jokesAdapter
            layoutManager = GridLayoutManager(activity,1)
        }
        jokesAdapter.setOnClickListener {
            setupDialogBox(it.id)
        }

    }
}