/**
 * 
 */
package com.baominh.sdp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baominh.sdp.entity.Smslog;

/**
 * @author HungDT
 *
 */
@Repository
public interface SmslogRepository extends JpaRepository<Smslog, Integer> {

}
