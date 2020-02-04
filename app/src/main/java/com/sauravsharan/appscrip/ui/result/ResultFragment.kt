package com.sauravsharan.appscrip.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sauravsharan.appscrip.R
import com.sauravsharan.appscrip.databinding.QuizFragmentBinding
import com.sauravsharan.appscrip.databinding.ResultFragmentBinding
import com.sauravsharan.appscrip.ui.quiz.QuizViewModel

class ResultFragment : Fragment() {

    private val viewModel: ResultViewModel by lazy {
        ViewModelProvider(this).get(ResultViewModel::class.java)
    }

    private lateinit var binding: ResultFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ResultFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        viewModel.historyButtonListener.observe(viewLifecycleOwner, Observer {
            if (it) {
                view!!.findNavController().navigate(R.id.action_resultFragment_to_historyFragment)
            }
        })

        viewModel.finishButtonListener.observe(viewLifecycleOwner, Observer {
            if (it) {
                view!!.findNavController().navigate(R.id.action_resultFragment_to_quizFragment)
            }
        })

        viewModel.closeButtonListener.observe(viewLifecycleOwner, Observer {
            if (it) {
                view!!.findNavController().navigate(R.id.action_resultFragment_to_homeFragment)
            }
        })

    }

}
