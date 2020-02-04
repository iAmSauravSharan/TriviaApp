package com.sauravsharan.appscrip.ui.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.BuildConfig
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sauravsharan.appscrip.R
import com.sauravsharan.appscrip.databinding.SplashFragmentBinding
import timber.log.Timber

class SplashFragment : Fragment() {

    private val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    private lateinit var binding: SplashFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SplashFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        val splashText = "${getString(R.string.app_name)}  ${BuildConfig.VERSION_NAME}"
        binding.splashText.text = splashText

        viewModel.launchTimer.observe(viewLifecycleOwner, Observer {
            if (it) {
                view!!.findNavController()
                    .navigate(R.id.action_splashFragment_to_homeFragment)
            }
        })

    }

}
