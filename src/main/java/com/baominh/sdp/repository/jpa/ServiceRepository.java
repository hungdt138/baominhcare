/**
 * 
 */
package com.baominh.sdp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baominh.sdp.entity.Service;

/**
 * @author HungDT
 *
 */
@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

}
