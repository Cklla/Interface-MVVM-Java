package com.openclassrooms.tajmahal;

import static org.junit.Assert.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.data.service.RestaurantApi;
import com.openclassrooms.tajmahal.data.service.RestaurantFakeApi;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DetailsViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private DetailsViewModel viewModel;

    @Before
    public void setUp() {
        RestaurantApi api = new RestaurantFakeApi();
        RestaurantRepository repository = new RestaurantRepository(api);
        viewModel = new DetailsViewModel(repository);
    }

    @Test
    public void getAverageRating_withNullList_returnsZero() {
        // Act
        double result = viewModel.getAverageRating(null);
        // Assert
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void getAverageRating_withEmptyList_returnsZero() {
        // Arrange
        List<Review> reviews = new ArrayList<>();
        // Act
        double result = viewModel.getAverageRating(reviews);
        // Assert
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void getAverageRating_withTwoReviews_returnsAverage() {
        // Arrange
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review("Alice", "https://xsgames.co/randomusers/assets/avatars/female/20.jpg", "Super !", 4));
        reviews.add(new Review("Antoine", "https://xsgames.co/randomusers/assets/avatars/male/67.jpg", "Bof...", 2));
        // Act
        double result = viewModel.getAverageRating(reviews);
        // Assert
        assertEquals(3.0, result, 0.001);
    }

    @Test
    public void getRatingPercentage_withNullList_returnsZero() {
        // Act
        int result = viewModel.getRatingPercentage(null, 4);
        // Assert
        assertEquals(0, result);
    }

    @Test
    public void getRatingPercentage_withEmptyList_returnsZero() {
        // Arrange
        List<Review> reviews = new ArrayList<>();
        // Act
        int result = viewModel.getRatingPercentage(reviews, 4);
        // Assert
        assertEquals(0, result);
    }

    @Test
    public void getRatingPercentage_withTwoFiveStarOutOfFour_returnsFiftyPercent() {
        // Arrange
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review("Alice", "https://xsgames.co/randomusers/assets/avatars/female/20.jpg", "Super !", 5));
        reviews.add(new Review("Antoine", "https://xsgames.co/randomusers/assets/avatars/male/67.jpg", "Bof...", 5));
        reviews.add(new Review("Delphine", "https://xsgames.co/randomusers/assets/avatars/female/20.jpg", "Super !", 3));
        reviews.add(new Review("Thomas", "https://xsgames.co/randomusers/assets/avatars/male/67.jpg", "Bof...", 3));
        // Act
        int result = viewModel.getRatingPercentage(reviews, 5);
        // Assert
        assertEquals(50, result);
    }

    @Test
    public void addReview_insertsAtPositionZero() {
        // Arrange
        Review review = new Review("Manon Garcia", "https://example.com/avatar.jpg", "Excellent !", 5);
        // Act
        viewModel.getTajMahalReviews().observeForever(reviews -> {});
        viewModel.addReview(review);
        // Assert
        List<Review> reviews = viewModel.getTajMahalReviews().getValue();
        assertEquals("Manon Garcia", reviews.get(0).getUsername());
    }






}
