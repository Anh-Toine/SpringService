package com.nguyen.microservices.core.recommendation.businesslayer;

import com.nguyen.api.core.recommendation.Recommendation;
import com.nguyen.microservices.core.recommendation.datalayer.RecommendationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {
    @Mappings({
            @Mapping(target = "rate",source = "entity.rating"),
            @Mapping(target = "serviceAddress",ignore = true)
    })
    Recommendation entityToModel(RecommendationEntity entity);
    @Mappings({
            @Mapping(target = "rating",source = "model.rate"),
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "version",ignore = true)
    })
    RecommendationEntity modelToEntity(Recommendation model);

    List<Recommendation> entityListToModelList(List<RecommendationEntity> entities);
    List<RecommendationEntity> modelListToEntityList(List<Recommendation> models);
}
