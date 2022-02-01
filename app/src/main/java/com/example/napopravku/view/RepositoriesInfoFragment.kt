package com.example.napopravku.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.napopravku.App
import com.example.napopravku.data.model.RepositoriesModel
import com.example.napopravku.databinding.FragmentRepositoriesInfoBinding
import com.example.napopravku.viewmodel.RepositoriesInfoViewModel
import com.example.napopravku.viewmodel.RepositoriesViewModel
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class RepositoriesInfoFragment : Fragment() {

    private val inputDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    private val outputDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())


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

        initViewModel()

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = arguments?.getParcelable<RepositoriesModel>(KEY_REPOSITORIES_MODEL)

        viewModel.loadLastCommit(model?.owner?.login ?: return, model.name)
        //получаем модель и заполняем данные на экране
        binding.fullName.setText(model.full_name)
        binding.ownerLogin.setText(model.owner.login)

        val url = model.owner.avatar_url
        Glide.with(requireContext())
            .load(url)
            .into(binding.ownerAvatar)

    }

    private fun initViewModel() {
        viewModel.getLastCommitLiveData()
            .observe(viewLifecycleOwner) {
                if (it != null) {

                    val formatDate = inputDate.parse(it.commit.author.date)

                    binding.lastCommitAuthorName.setText(it.commit.author.name)
                    binding.lastCommitDate.setText(outputDate.format(formatDate ?: Date()))
                    binding.lastCommitMessage.setText(it.commit.message)


                    var sha: String = ""
                    it.parents.forEach {
                        sha += "${it.sha}, "
                    }

                    binding.lastCommitSha.setText(sha)

                } else {
                    Toast.makeText(context, "Error in getting data", Toast.LENGTH_SHORT).show()
                }
            }
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