/**
 * 
 */
package com.baominh.sdp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author HungDT
 *
 */
@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleRepository, Integer> {

}
