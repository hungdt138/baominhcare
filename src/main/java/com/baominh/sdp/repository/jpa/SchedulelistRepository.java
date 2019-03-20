/**
 * 
 */
package com.baominh.sdp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baominh.sdp.entity.Schedulelist;

/**
 * @author HungDT
 *
 */
@Repository
public interface SchedulelistRepository extends JpaRepository<Schedulelist, Integer> {

}
