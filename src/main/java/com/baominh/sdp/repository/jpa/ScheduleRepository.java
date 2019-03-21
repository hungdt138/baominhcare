/**
 * 
 */
package com.baominh.sdp.repository.jpa;

import com.baominh.sdp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author HungDT
 *
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

}
