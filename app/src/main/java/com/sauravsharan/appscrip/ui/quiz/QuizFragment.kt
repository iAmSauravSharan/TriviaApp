package com.sauravsharan.appscrip.ui.quiz

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

class QuizFragment : Fragment() {

    private val viewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    private lateinit var binding: QuizFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = QuizFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        viewModel.submitQuizListener.observe(viewLifecycleOwner, Observer {
            if (it) {
                view!!.findNavController().navigate(R.id.action_quizFragment_to_resultFragment)
            }
        })

    }

}
