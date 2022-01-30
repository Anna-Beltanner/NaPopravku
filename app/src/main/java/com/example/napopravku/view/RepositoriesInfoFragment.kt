package com.example.napopravku.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.napopravku.R
import com.example.napopravku.data.model.RepositoriesModel

class RepositoriesInfoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repositories_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val model = arguments?.getParcelable<RepositoriesModel>(KEY_REPOSITORIES_MODEL)
        //получаем модель
    }

//    private fun setupFragment() {
//
//        val fragment = RepositoriesInfoFragment.newInstance(model)
//        val fragmentManager: FragmentManager = supportFragmentManager
//        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.container, fragment)
//        fragmentTransaction.commit()

    companion object {
        val KEY_REPOSITORIES_MODEL = "keyRepositoriesModel"


        @JvmStatic
        fun newInstance(model: RepositoriesModel) =
            RepositoriesInfoFragment().apply {
                arguments = Bundle().apply {
                    //putParcelable(KEY_REPOSITORIES_MODEL, model)
                }
            }
    }
}