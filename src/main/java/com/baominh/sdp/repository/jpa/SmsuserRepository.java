/**
 * 
 */
package com.baominh.sdp.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baominh.sdp.entity.Smsuser;

/**
 * @author HungDT
 *
 */
@Repository
public interface SmsuserRepository extends JpaRepository<Smsuser, Integer> {

    @Query(value = "SELECT * FROM smsuser WHERE SERVICEID = :serviceId AND ACTIVE = 1 ", nativeQuery = true)
    List<Smsuser> getSubsriberByServiceId(@Param("serviceId") Integer serviceId);

    @Query(value = "SELECT * FROM smsuser WHERE SERVICEID = :serviceId AND phone = :isdn AND ACTIVE = 1 ", nativeQuery = true)
    Smsuser getSubscriberByIsdn(@Param("serviceId") Integer serviceId, @Param("isdn") String isdn);
    
    @Query(value = "SELECT * FROM smsuser WHERE phone = :isdn AND ACTIVE = 1", nativeQuery = true)
    List<Smsuser> getAllSubscriptionByIsdn(@Param("isdn") String isdn);
}
