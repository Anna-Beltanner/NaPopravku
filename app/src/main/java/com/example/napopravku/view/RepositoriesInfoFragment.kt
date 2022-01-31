package com.example.napopravku.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.napopravku.App
import com.example.napopravku.data.model.RepositoriesModel
import com.example.napopravku.databinding.FragmentRepositoriesInfoBinding
import com.example.napopravku.viewmodel.RepositoriesInfoViewModel
import com.example.napopravku.viewmodel.RepositoriesViewModel

class RepositoriesInfoFragment : Fragment() {


    private lateinit var binding: FragmentRepositoriesInfoBinding
    private val viewModel by lazy { ViewModelProvider(this).get(RepositoriesInfoViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoriesInfoBinding.inflate(inflater, container, false)

        // init view model retrofit
        val retrofitInstance = (requireActivity().application as App).retrofitInstance
        viewModel.setRetrofitInstance(retrofitInstance)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = arguments?.getParcelable<RepositoriesModel>(KEY_REPOSITORIES_MODEL)

        viewModel.loadLastCommit(model?.owner?.login ?: return, model.name)
        //получаем модель и заполняем данные на экране
    }

    companion object {
        const val KEY_REPOSITORIES_MODEL = "keyRepositoriesModel"

        @JvmStatic
        fun newInstance(model: RepositoriesModel) =
            RepositoriesInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_REPOSITORIES_MODEL, model)
                }
            }
    }
}