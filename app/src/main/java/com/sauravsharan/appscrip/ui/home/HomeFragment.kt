package com.sauravsharan.appscrip.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sauravsharan.appscrip.R
import com.sauravsharan.appscrip.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = HomeFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        viewModel.submitButtonListener.observe(viewLifecycleOwner, Observer {
            if (it) {
                view!!.findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
            }
        })

        viewModel.nameError.observe(viewLifecycleOwner, Observer {
            Snackbar.make(binding.homeLayout, "Invalid Name", Snackbar.LENGTH_SHORT).show()
        })

        binding.userNameField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                view!!.findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
            }
            true
        }

    }


}
