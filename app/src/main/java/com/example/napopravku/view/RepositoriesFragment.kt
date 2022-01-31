package com.example.napopravku.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.napopravku.App
import com.example.napopravku.R
import com.example.napopravku.adapters.RepositoriesAdapters
import com.example.napopravku.data.model.RepositoriesModel
import com.example.napopravku.databinding.FragmentRepositoriesBinding
import com.example.napopravku.viewmodel.RepositoriesViewModel


class RepositoriesFragment : Fragment() {

    private lateinit var binding: FragmentRepositoriesBinding
    private lateinit var recyclerAdapter: RepositoriesAdapters
    private val viewModel by lazy { ViewModelProvider(this).get(RepositoriesViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoriesBinding.inflate(inflater, container, false)

        // init view model retrofit
        val retrofitInstance = (requireActivity().application as App).retrofitInstance
        viewModel.setRetrofitInstance(retrofitInstance)

        initRecyclerView()
        initViewModel()
        return binding.root
    }

    //инициализуем RecyclerView
    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        binding.recyclerView.addItemDecoration(decoration)

        recyclerAdapter = RepositoriesAdapters({
            goToRepositoriesInfo(it)
        }, {
            viewModel.createApiCall(it)
        })
        binding.recyclerView.adapter = recyclerAdapter

    }

    //инициализуем viewModel
    private fun initViewModel() {
        viewModel.getRepositoriesModelObserver()
            .observe(viewLifecycleOwner) {
                if (it != null) {
                    recyclerAdapter.setUpdatedData(it)
                } else {
                    Toast.makeText(activity, "Error in getting data", Toast.LENGTH_SHORT).show()
                }
            }

        viewModel.getErrorLiveData()
            .observe(viewLifecycleOwner) {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }

        viewModel.createApiCall()
    }

    private fun goToRepositoriesInfo(model: RepositoriesModel) {
        val fragment = RepositoriesInfoFragment.newInstance(model)
        val fragmentManager: FragmentManager = requireActivity()?.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.addToBackStack(fragment.tag)
        fragmentTransaction.commit()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            RepositoriesFragment()
    }
}
