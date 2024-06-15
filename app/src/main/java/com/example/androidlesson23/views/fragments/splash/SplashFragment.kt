package com.example.androidlesson23.views.fragments.splash

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.androidlesson23.databinding.FragmentSplashBinding
import com.example.androidlesson23.views.fragments.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateScreen()
    }

    private fun navigateScreen() {
        lifecycleScope.launch {
            val isAuth = getAuth()
            delay(3000)
            if (isAuth) {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
            } else {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }

        }
    }

    private fun getAuth(): Boolean {
        val sp = requireActivity().getSharedPreferences("product_local", Context.MODE_PRIVATE)

        return sp.getBoolean("isAuth", false)
    }

}