/**
 * 
 */
package com.baominh.sdp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baominh.sdp.entity.ServiceEntity;

import java.util.List;

/**
 * @author HungDT
 *
 */
@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Integer> {
    @Query(value = "SELECT * FROM SERVICE WHERE ENABLE = 1", nativeQuery = true)
    List<ServiceEntity> getServiceEntityByEnable();

    @Query(value = "SELECT * FROM SERVICE WHERE SDPPRODUCTID = :productId AND ENABLE = 1", nativeQuery = true)
    ServiceEntity getServiceBySdpProductId(@Param("productId") Integer productId);
}
