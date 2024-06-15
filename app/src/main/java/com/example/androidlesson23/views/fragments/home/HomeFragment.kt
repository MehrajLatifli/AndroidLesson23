package com.example.androidlesson23.views.fragments.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidlesson23.R
import com.example.androidlesson23.databinding.FragmentHomeBinding
import com.example.androidlesson23.models.responses.get.category.CategoryAdapter
import com.example.androidlesson23.utilities.gone
import com.example.androidlesson23.utilities.visible
import com.example.androidlesson23.viewmodels.HomeViewModel
import com.example.androidlesson23.views.adapters.ProductAdapter
import com.example.androidlesson23.views.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()
    private val categoryAdapter = CategoryAdapter()

    private val productAdapter = ProductAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        observeData()


        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategory.layoutManager = linearLayoutManager
        binding.rvCategory.adapter = categoryAdapter


        val spanCount = if (isTablet()) 4 else 2
        val gridLayoutManager = GridLayoutManager(context, spanCount)
        binding.rvProduct.layoutManager = gridLayoutManager
        binding.rvProduct.adapter = productAdapter



        categoryAdapter.onClickItem = { categoryName ->

            viewModel.getProductsByCategory(categoryName)



            with(binding.outlinedTextField.editText!!) {
                clearFocus()
                clearComposingText()
                text?.clear()
            }


        }

        binding.imageView5.setOnClickListener{
            val searchText = binding.outlinedTextField.editText?.text.toString().trim()
            viewModel.searchProducts(searchText)
        }

        productAdapter.onClickItem = { productId ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(productId)
            findNavController().navigate(action)
        }


    }

    private fun observeData() {
        viewModel.categories.observe(viewLifecycleOwner) {categories ->



        categoryAdapter.updateList(categories)
        }

        viewModel.products.observe(viewLifecycleOwner) {products ->


            productAdapter.updateList(products)
        }


        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBarContainer.progressBar2.visible()

            } else {
                binding.progressBarContainer.progressBar2.gone()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun isTablet(): Boolean {
        val configuration = resources.configuration
        return configuration.smallestScreenWidthDp >= 600
    }


}


