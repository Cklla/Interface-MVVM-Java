package com.openclassrooms.tajmahal.ui.reviews;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.databinding.ItemReviewBinding;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.List;

/**
 * RecyclerView adapter for displaying a list of {@link Review} items.
 * <p>
 * Each item is rendered using the {@code item_review.xml} layout, which shows
 * the reviewer's avatar, name, rating, and comment.
 * </p>
 *
 * @see Review
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private final List<Review> reviews;

    /**
     * Constructs a new ReviewsAdapter with the given list of reviews.
     * @param reviews the list of reviews to display in RecyclerView
     */
    public ReviewsAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Called when RecyclerView when it needs a new empty row.
     * Inflates the {@code item_review.xml} layout and wraps it in a {link ReviewViewHolder}.
     *
     * @param parent The ViewGroup into which the new View will be added
     * @param viewType The view type of the new View (unused here - only one type of row)
     * @return a new {@link ReviewViewHolder} holding the inflated view
     */
    @NonNull
    @Override
    public ReviewsAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReviewBinding binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ReviewViewHolder(binding);
    }

    /**
     * Called by the RecyclerView to fill a row with data.
     * Binds the {@link Review} at the given position to the views in the holder.
     *
     * @param holder The ViewHolder whose views should be updated
     * @param position The position of the review in the list
     */
    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.bind(review);
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {

        private final ItemReviewBinding binding;

        /**
         * Constructs a ReviewViewHolder form an inflated binding.
         *
         * @param binding the View Binding for {@code item_review.xml}
         */
        ReviewViewHolder(ItemReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Populates the row views with data from the given {@link Review}.
         *
         * @param review the review whose data should be displayed
         */
        public void bind(Review review) {
            binding.tvReviewerName.setText(review.getUsername());
            binding.rbReviewRating.setRating((float) review.getRate());
            binding.tvReviewComment.setText(review.getComment());
            Glide.with(binding.ivReviewerAvatar.getContext())
                    .load(review.getPicture())
                    .circleCrop()
                    .into(binding.ivReviewerAvatar);
        }
    }

    /**
     * Updates the list of reviews displayed by the adapter.
     * @param newReviews The new list of {@link Review} to display.
     */
    public void setReviews(List<Review> newReviews) {
        reviews.clear();
        reviews.addAll(newReviews);
    }


    /**
     * Returns the total number of reviews in the list.
     *
     * @return the size of the reviews list
     */
    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
