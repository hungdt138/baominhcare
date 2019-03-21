/**
 * 
 */
package com.baominh.sdp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baominh.sdp.entity.Charginglog;

/**
 * @author HungDT
 *
 */
@Repository
public interface ChargingLogRepository extends JpaRepository<Charginglog, Integer> {

}
