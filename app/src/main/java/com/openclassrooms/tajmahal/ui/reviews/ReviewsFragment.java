package com.openclassrooms.tajmahal.ui.reviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.openclassrooms.tajmahal.databinding.FragmentReviewsBinding;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReviewsFragment extends Fragment {

    private FragmentReviewsBinding binding;
    private ReviewsAdapter reviewsAdapter;
    private DetailsViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReviewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(DetailsViewModel.class);
        binding.rvReviews.setLayoutManager(new LinearLayoutManager(requireContext()));
        reviewsAdapter = new ReviewsAdapter(new ArrayList<>());
        binding.rvReviews.setAdapter(reviewsAdapter);
        Glide.with(binding.ivUserAvatar.getContext())
                .load("https://xsgames.co/randomusers/assets/avatars/female/26.jpg")
                .circleCrop()
                .into(binding.ivUserAvatar);
        viewModel.getTajMahalReviews().observe(getViewLifecycleOwner(), reviews -> {
            reviewsAdapter.setReviews(reviews);
            reviewsAdapter.notifyDataSetChanged();
        });
        binding.btnSubmitReview.setOnClickListener(v -> {
            String comment = binding.etReviewComment.getText().toString();
            float rate = binding.rbNewReview.getRating();
            Review review = new Review("Manon Garcia", "https://xsgames.co/randomusers/assets/avatars/female/26.jpg", comment, (int) rate);
            viewModel.addReview(review);
        });
    }
}