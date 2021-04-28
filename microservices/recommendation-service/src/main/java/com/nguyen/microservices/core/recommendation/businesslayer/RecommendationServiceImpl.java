package com.nguyen.microservices.core.recommendation.businesslayer;

import com.mongodb.DuplicateKeyException;
import com.nguyen.api.core.recommendation.Recommendation;
import com.nguyen.microservices.core.recommendation.datalayer.RecommendationEntity;
import com.nguyen.microservices.core.recommendation.datalayer.RecommendationRepository;
import com.nguyen.utils.exceptions.InvalidInputException;
import com.nguyen.utils.exceptions.NotFoundException;
import com.nguyen.utils.http.ServiceUtil;
import com.sun.tools.javac.code.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecommendationServiceImpl implements RecommendationService{
    private static final Logger LOG = LoggerFactory.getLogger(RecommendationServiceImpl.class);
    private final RecommendationRepository repo;
    private final RecommendationMapper mapper;
    private final ServiceUtil serviceUtil;

    public RecommendationServiceImpl(RecommendationRepository repo, RecommendationMapper mapper, ServiceUtil serviceUtil) {
        this.repo = repo;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<Recommendation> findByProductId(int productId) {
        List<RecommendationEntity> entities = repo.findByProductId(productId);
        List<Recommendation> recommendationList = new ArrayList<>();
        for(RecommendationEntity re : entities){
            recommendationList.add(mapper.entityToModel(re));
        }
        recommendationList.forEach(m -> m.setServiceAddress(serviceUtil.getServiceAddress()));
        LOG.debug("Recommendations findByProductId: response size: {}",recommendationList.size());
        return recommendationList;
    }
/*
    @Override
    public Recommendation createRecommendation(Recommendation model) {

    }

    @Override
    public void deleteRecommendation(int productId) {
        try {
            List<RecommendationEntity> recommendationEntities = repo.findByProductId(productId);
            if(recommendationEntities.size() == 0){
                throw new NotFoundException("No recommendations found for productId: "+ productId);
            }else{
                LOG.debug("deleteRecommendation: deleted all recommendations for productId: {}", productId);
                recommendationEntities.clear();
            }
        }catch(NotFoundException e){
            LOG.debug("An error has occurred: {}",e.getMessage());
        }
    }

 */
}
