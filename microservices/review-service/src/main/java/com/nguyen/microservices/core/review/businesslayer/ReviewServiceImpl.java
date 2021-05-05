package com.nguyen.microservices.core.review.businesslayer;

import com.nguyen.api.core.review.Review;
import com.nguyen.microservices.core.review.datalayer.ReviewEntity;
import com.nguyen.microservices.core.review.datalayer.ReviewRepository;
import com.nguyen.utils.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{
    private static final Logger LOG = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private final ReviewRepository repo;
    private final ReviewMapper mapper;
    private final ServiceUtil serviceUtil;

    public ReviewServiceImpl(ReviewRepository repo, ReviewMapper mapper, ServiceUtil serviceUtil) {
        this.repo = repo;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }


    @Override
    public List<Review> findByProductId(int productId) {
        List<ReviewEntity> entities = repo.findByProductId(productId);
        List<Review> reviewList = mapper.entityListToModelList(entities);
        reviewList.forEach(m -> m.setServiceAddress(serviceUtil.getServiceAddress()));
        LOG.debug("Reviews findByProductId: response size: {}",reviewList.size());
        return reviewList;
    }

    @Override
    public Review createReview(Review model) {
        ReviewEntity recommendationEntity = mapper.modelToEntity(model);
        ReviewEntity newEntity = repo.save(recommendationEntity);
        LOG.debug("Reviews createReview: create recommendation entity: {}/{}",model.getProductId(),model.getReviewId());
        return mapper.entityToModel(newEntity);
    }

    @Override
    public void deleteReviews(int productId) {
        LOG.debug("Reviews createRecommendation: deleting all reviews for productId: {}",productId);
        repo.deleteAll(repo.findByProductId(productId));
    }
}
