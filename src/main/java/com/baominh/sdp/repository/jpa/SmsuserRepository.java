/**
 * 
 */
package com.baominh.sdp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baominh.sdp.entity.Smsuser;

/**
 * @author HungDT
 *
 */
@Repository
public interface SmsuserRepository extends JpaRepository<Smsuser, Integer> {

}
