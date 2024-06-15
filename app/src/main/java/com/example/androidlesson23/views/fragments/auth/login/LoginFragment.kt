package com.example.androidlesson23.views.fragments.auth.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidlesson23.R
import com.example.androidlesson23.databinding.FragmentLoginBinding
import com.example.androidlesson23.utilities.gone
import com.example.androidlesson23.utilities.visible
import com.example.androidlesson23.viewmodels.AuthViewModel
import com.example.androidlesson23.views.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()

        binding.buttonLogin.setOnClickListener {
            login()
        }

        binding.buttonIsUser.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    private fun observeData() {
        viewModel.isLogin.observe(viewLifecycleOwner) {
            if (it) {
                if (binding.materialSwitch.isChecked) setUserAuth()
                Toast.makeText(context, "Giriş başarılı", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            } else {
                Toast.makeText(context, "Xetalı Giriş", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) binding.animationView.visible() else binding.animationView.gone()
        }
    }

    private fun login() {
        val email = binding.editEmailLogin.text.toString().trim()
        val password = binding.editPasswordLogin.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModel.login(email, password)
        } else {
            Toast.makeText(context, "Boş ola bilmez", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUserAuth() {
        val sp = requireActivity().getSharedPreferences("product_local", Context.MODE_PRIVATE)

        sp.edit().putBoolean("isAuth", true).apply()
    }

}