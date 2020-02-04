package com.sauravsharan.appscrip.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sauravsharan.appscrip.R
import com.sauravsharan.appscrip.data.database.model.Questions
import com.sauravsharan.appscrip.data.database.model.User
import com.sauravsharan.appscrip.databinding.HistoryFragmentBinding

class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    private lateinit var binding: HistoryFragmentBinding
    private lateinit var userList: ArrayList<User>
    private lateinit var questionList: ArrayList<Questions>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = HistoryFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        viewModel.closeButtonListener.observe(viewLifecycleOwner, Observer {
            if (it) {
                view!!.findNavController().navigate(R.id.action_historyFragment_to_homeFragment)
            }
        })

        viewModel.questionList.observe(viewLifecycleOwner, Observer {
            questionList = it as ArrayList<Questions>
        })

        viewModel.userList.observe(viewLifecycleOwner, Observer {
            userList = it as ArrayList<User>
        })

        viewModel.loadData.observe(viewLifecycleOwner, Observer {

            if(it) setUpRecyclerView()

        })

    }

    private fun setUpRecyclerView() {
        val adapter = HistoryAdapter(userList, questionList)
        binding.historyList.adapter = adapter
    }


}
