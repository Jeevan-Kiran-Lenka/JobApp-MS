package com.jeeorg.reviewms.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){
        return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestParam Long companyId ,@RequestBody Review review){
        Review reviewCreated = reviewService.createReview(companyId, review);
        if (reviewCreated != null) {
            return new ResponseEntity<>(review, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable Long id){
        Review review = reviewService.getReview(id);
        if (review == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else
            return new ResponseEntity<>(review, HttpStatus.FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review newReview){
        Review review = reviewService.updateReview(id, newReview);
        if (review != null) {
            return new ResponseEntity<>(review, HttpStatus.CREATED);
        }else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview( @PathVariable Long id){
        if(reviewService.deleteReview(id))
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        return new ResponseEntity<>("Deletion Unsuccessful", HttpStatus.NOT_FOUND);
    }

}
