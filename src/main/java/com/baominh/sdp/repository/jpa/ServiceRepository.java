/**
 * 
 */
package com.baominh.sdp.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baominh.sdp.entity.ServiceEntity;

/**
 * @author HungDT
 *
 */
@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Integer> {
    @Query(value = "SELECT * FROM services WHERE ENABLE = 1", nativeQuery = true)
    List<ServiceEntity> getServiceEntityByEnable();

    @Query(value = "SELECT * FROM services WHERE SDP_PRODUCT_ID = :productId AND ENABLE = 1", nativeQuery = true)
    ServiceEntity getServiceBySdpProductId(@Param("productId") Integer productId);
}
