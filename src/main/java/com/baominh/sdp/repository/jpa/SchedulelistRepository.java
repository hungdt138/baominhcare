/**
 * 
 */
package com.baominh.sdp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baominh.sdp.entity.Schedulelist;

import java.util.List;

/**
 * @author HungDT
 *
 */
@Repository
public interface SchedulelistRepository extends JpaRepository<Schedulelist, Integer> {

    @Query(value = "select * from schedulelist where serviceID = :serviceId", nativeQuery = true)
    List<Schedulelist> getAllScheduleListByServiceId(@Param("serviceId") Integer serviceId);
}
