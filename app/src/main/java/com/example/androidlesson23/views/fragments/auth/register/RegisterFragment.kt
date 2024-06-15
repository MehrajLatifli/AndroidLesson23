package com.example.androidlesson23.views.fragments.auth.register

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidlesson23.R
import com.example.androidlesson23.databinding.FragmentRegisterBinding
import com.example.androidlesson23.utilities.gone
import com.example.androidlesson23.utilities.visible
import com.example.androidlesson23.viewmodels.AuthViewModel
import com.example.androidlesson23.views.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment  : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        binding.buttonRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.editEmailLogin.text.toString().trim()
        val password = binding.editPasswordLogin.text.toString().trim()
        val rePassword = binding.editRePassword.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty() && rePassword.isNotEmpty()) {
            if (password == rePassword) {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    viewModel.register(email, password)
                } else {
                    Toast.makeText(context, "Email düz deyil", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Şifreler eyni değil", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Boş ola bilmez", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeData() {
        viewModel.isLogin.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Kayıt başarılı", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
            } else {
                Toast.makeText(context, "Xetalı Giriş", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) binding.animationView.visible() else binding.animationView.gone()
        }

        viewModel.error.observe(viewLifecycleOwner){
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }


}