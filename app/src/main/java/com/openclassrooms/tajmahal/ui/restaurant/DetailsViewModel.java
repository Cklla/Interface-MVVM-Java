package com.openclassrooms.tajmahal.ui.restaurant;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import javax.inject.Inject;

import java.util.Calendar;
import java.util.List;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * MainViewModel is responsible for preparing and managing the data for the {@link DetailsFragment}.
 * It communicates with the {@link RestaurantRepository} to fetch restaurant details and provides
 * utility methods related to the restaurant UI.
 *
 * This ViewModel is integrated with Hilt for dependency injection.
 */
@HiltViewModel
public class DetailsViewModel extends ViewModel {

    private final RestaurantRepository restaurantRepository;

    /**
     * Constructor that Hilt will use to create an instance of MainViewModel.
     *
     * @param restaurantRepository The repository which will provide restaurant data.
     */
    @Inject
    public DetailsViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Fetches the details of the Taj Mahal restaurant.
     *
     * @return LiveData object containing the details of the Taj Mahal restaurant.
     */
    public LiveData<Restaurant> getTajMahalRestaurant() {
        return restaurantRepository.getRestaurant();
    }

    /**
     * Retrieves the current day of the week in French.
     *
     * @return A string representing the current day of the week in French.
     */
    public String getCurrentDay(Context context) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String dayString;

        switch (dayOfWeek) {
            case Calendar.MONDAY:
                dayString = context.getString(R.string.monday);
                break;
            case Calendar.TUESDAY:
                dayString = context.getString(R.string.tuesday);
                break;
            case Calendar.WEDNESDAY:
                dayString = context.getString(R.string.wednesday);
                break;
            case Calendar.THURSDAY:
                dayString = context.getString(R.string.thursday);
                break;
            case Calendar.FRIDAY:
                dayString = context.getString(R.string.friday);
                break;
            case Calendar.SATURDAY:
                dayString = context.getString(R.string.saturday);
                break;
            case Calendar.SUNDAY:
                dayString = context.getString(R.string.sunday);
                break;
            default:
                dayString = "";
        }
        return dayString;
    }

    /**
     * Fetches the list of reviews for the Tajmahal restaurant.
     *
     * @return LiveData holding the list of {@link Review} for the restaurant.
     * @see Review
     */
    public LiveData<List<Review>> getTajMahalReviews() {
        return restaurantRepository.getReviews();
    }

    /**
     * Calculates the average rating from a list of reviews.
     *
     * @param reviews The list of {@link Review} to calculate the average from.
     * @return The average rating as a double, or 0 if the list is null of empty.
     */
    public double getAverageRating(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) return 0f;
        double total = 0;
        for (Review review : reviews) {
            total += review.getRate();
        }
        return total / reviews.size();
    }

    /**
     * Calculates the percentage of reviews that have a given star rating.
     *
     * @param reviews The list of {@link Review} to analyse.
     * @param starCount The star rating to count (1 to 5).
     * @return The percentage (0 to 100) of reviews matching the given star count.
     */
    public int getRatingPercentage(List<Review> reviews, int starCount) {
        if (reviews == null || reviews.isEmpty()) return 0;
        int count = 0;
        for (Review review : reviews) {
            if (review.getRate() == starCount) count++;
        }
        return (int) ((count * 100f) / reviews.size());
    }
}
