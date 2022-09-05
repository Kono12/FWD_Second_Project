package com.udacity.asteroidradar.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.DB.DataBaseClass
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.RV.RV_Adapter
import com.udacity.asteroidradar.Repository
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainFragment : Fragment() {


    private val database by lazy { DataBaseClass.getInstance(requireActivity()).Dao }
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        GlobalScope.launch {
            try {
            getDataBaseDataToRV()}
            catch (e : Exception){}
        }

        binding.viewModel = viewModel

        viewModel.response.observe(viewLifecycleOwner, Observer { response ->

            viewModel.adapter = RV_Adapter(viewModel.response.value!!)
            asteroid_recycler.adapter = viewModel.adapter
            asteroid_recycler.layoutManager = LinearLayoutManager(activity)
            GlobalScope.launch {
                try {
                    refreshData()
                }catch (E:Exception){}
            }
        })

        viewModel.pic.observe(viewLifecycleOwner, Observer { pic ->
            Log.i("ResponseAstroids", "pic is here $pic ")
        })


        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }




    suspend fun refreshData() {
        database.clear()
        database.insertAll(viewModel.response.value!!)
    }

    suspend fun getDataBaseDataToRV() {
        viewModel.response.value!!.clear()
        viewModel.response.value!!.addAll(database.getAll().value!!)
        viewModel.adapter.notifyDataSetChanged()
    }


}
