/**
 * 
 */
package com.baominh.sdp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baominh.sdp.entity.Charging;
import com.baominh.sdp.entity.Charginglog;

/**
 * @author HungDT
 *
 */
public interface ChargingRepository extends JpaRepository<Charging, Integer> {

}
