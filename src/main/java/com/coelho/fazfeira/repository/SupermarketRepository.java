package com.coelho.fazfeira.repository;

import com.coelho.fazfeira.model.Supermarket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SupermarketRepository extends JpaRepository<Supermarket, UUID> {
    String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:latitude)) * cos(radians(s.latitude)) *" +
            " cos(radians(s.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(s.latitude))))";

    @Query("SELECT s FROM Supermarket s WHERE " + HAVERSINE_FORMULA + " <= :distance ORDER BY " + HAVERSINE_FORMULA + " ASC")
    List<Supermarket> findSupermarketWithInDistance(@Param("latitude") double latitude,
                                                    @Param("longitude") double longitude,
                                                    @Param("distance") double distanceWithInKM);

    Page<Supermarket> findByPlaceId(String placeId,
                                    Pageable pageable);

    Page<Supermarket> findByNameIgnoreCaseContaining(Pageable pageable, String name);

}