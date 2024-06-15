package com.example.androidlesson23.views.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.androidlesson23.R
import com.example.androidlesson23.databinding.FragmentDetailBinding
import com.example.androidlesson23.utilities.gone
import com.example.androidlesson23.utilities.loadImageWithoutBindingAdapter
import com.example.androidlesson23.utilities.visible
import com.example.androidlesson23.viewmodels.DetailViewModel
import com.example.androidlesson23.views.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel by viewModels<DetailViewModel>()
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val guidelinePercent = if (isTablet()) 0.50f else 0.40f
        val scaleType = if (isTablet()) ImageView.ScaleType.FIT_XY else ImageView.ScaleType.CENTER_CROP
        binding.guideline5.setGuidelinePercent(guidelinePercent)
        binding.imageView.scaleType = scaleType



        val productId = args.id.toInt()
        viewModel.getProductById(productId)
        observeData()


    }

    private fun observeData() {
        viewModel.product.observe(viewLifecycleOwner) { item ->
            item?.let {

                binding.textView1.text = it.title?:"No description"
                binding.textView2.text = it.description?:"No description"
                binding.textView3.text = "Price: ${it.price?.toString() ?: ""} $"
                binding.imageView.loadImageWithoutBindingAdapter(it.image)

                setStarRating(it.rating?.rate, binding.star1, binding.star2, binding.star3, binding.star4, binding.star5)


            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBarContainer.progressBar2.visible()
                hideOtherWidgets()
            } else {
                binding.progressBarContainer.progressBar2.gone()
                showOtherWidgets()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun hideOtherWidgets() {
        binding.detailConstraintLayout.gone()
    }

    private fun showOtherWidgets() {
        binding.starConstraintLayout.visible()
        binding.detailConstraintLayout.visible()
    }


    private fun setStarRating(rating: Double?, vararg starImageViews: ImageView) {
        val maxStars = starImageViews.size
        val filledStars = (rating ?: 0.0).coerceIn(0.0, maxStars.toDouble()).toInt()

        for (i in 0 until maxStars) {
            if (i < filledStars) {
                starImageViews[i].setImageResource(R.drawable.purplestar)
            } else {
                starImageViews[i].setImageResource(R.drawable.greystar)
            }
        }
    }


    private fun isTablet(): Boolean {

        val configuration = resources.configuration
        return configuration.smallestScreenWidthDp >= 600
    }

}