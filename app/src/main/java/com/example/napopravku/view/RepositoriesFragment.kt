package com.example.napopravku.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.napopravku.App
import com.example.napopravku.R
import com.example.napopravku.adapters.RepositoriesAdapters
import com.example.napopravku.data.model.RepositoriesModel
import com.example.napopravku.viewmodel.RepositoriesViewModel


class RepositoriesFragment : Fragment() {

    private lateinit var recyclerAdapter: RepositoriesAdapters
    val viewModel by lazy { ViewModelProvider(this).get(RepositoriesViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_repositories, container, false)

        initView(view)
        initViewModel()
        return view


    }

    //инициализуем view
    private fun initView(view: View) {
        val repositoriesRecyclerView = view.findViewById<RecyclerView>(R.id.rv)

        repositoriesRecyclerView.layoutManager = LinearLayoutManager(activity)

        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        repositoriesRecyclerView.addItemDecoration(decoration)

        val retrofitInstance = (requireActivity().application as App).retrofitInstance

        recyclerAdapter = RepositoriesAdapters { viewModel.createApiCall(retrofitInstance, it) }
        repositoriesRecyclerView.adapter = recyclerAdapter

    }

    //инициализуем viewModel
    private fun initViewModel() {

        viewModel.getRepositoriesModelObserver()
            .observe(viewLifecycleOwner, Observer<List<RepositoriesModel>> {
                if (it != null) {
                    recyclerAdapter.setUpdatedData(it)
                } else {
                    Toast.makeText(activity, "Error in getting data", Toast.LENGTH_SHORT).show()

                }
            })

        val retrofitInstance = (requireActivity().application as App).retrofitInstance
        //создаем инстанс ретрофита, приводим к App, передаем в createApiCall


        viewModel.createApiCall(retrofitInstance)

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            RepositoriesFragment()
    }
}
