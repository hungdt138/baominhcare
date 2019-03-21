package com.baominh.sdp.repository.jpa;

import com.baominh.sdp.entity.Mtqueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MtqueueRepository extends JpaRepository<Mtqueue, Integer> {

    @Query(value = "select * from mtqueue where status = 1", nativeQuery = true)
    List<Mtqueue> getAllContentInQueue();

    @Query(value = "select * from mtqueue where serviceid = :serviceId status = 1", nativeQuery = true)
    List<Mtqueue> getAllContentInQueueByServiceId(@Param("serviceId") Integer serviceId);
}
