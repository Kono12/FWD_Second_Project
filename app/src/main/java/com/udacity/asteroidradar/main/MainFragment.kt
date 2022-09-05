package com.udacity.asteroidradar.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.Asteroid
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


        binding.viewModel = viewModel

        if (viewModel.checkForInternet(requireContext())) {

            viewModel.getAstroids()
            viewModel.getPicture()
            viewModel.response.observe(viewLifecycleOwner, Observer { response ->

                viewModel.adapter = RV_Adapter(viewModel.response.value!!)
                asteroid_recycler.adapter = viewModel.adapter
                asteroid_recycler.layoutManager = LinearLayoutManager(activity)
                viewModel.adapter.setOnItemClick(object : RV_Adapter.OnItemClick {
                    override fun OnItemClick(position: Int) {
                        //todo: pass astroid to detail fragment

                        findNavController().navigate(
                            MainFragmentDirections.actionShowDetail(
                                viewModel.response.value!![position]
                            )
                        )
                    }
                })
                GlobalScope.launch {
                    database.insertAll(viewModel.response.value!!)
                }


            })
        } else {
            //  Toast.makeText(context,"Offline mode",Toast.LENGTH_SHORT).show()
            try {
                GlobalScope.launch {
                    viewModel.arraylist = database.getAll() as ArrayList<Asteroid>
                    viewModel.adapter = RV_Adapter(viewModel.arraylist)
                    asteroid_recycler.adapter = viewModel.adapter
                    asteroid_recycler.layoutManager = LinearLayoutManager(activity)

                    viewModel.adapter.setOnItemClick(object : RV_Adapter.OnItemClick {
                        override fun OnItemClick(position: Int) {
                            //todo: pass astroid to detail fragment

                            findNavController().navigate(
                                MainFragmentDirections.actionShowDetail(
                                    viewModel.arraylist[position]
                                )
                            )
                        }
                    })

                }
            } catch (E: Exception) {
                // Toast.makeText(context, "Try again", Toast.LENGTH_SHORT).show()
            }


        }



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


}
