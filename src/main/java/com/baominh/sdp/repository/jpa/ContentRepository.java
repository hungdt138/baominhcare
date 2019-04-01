/**
 * 
 */
package com.baominh.sdp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baominh.sdp.entity.Content;

import java.util.List;

/**
 * @author HungDT
 *
 */
@Repository
public interface ContentRepository extends JpaRepository<Content, Integer> {
    @Query(value = "SELECT * FROM content WHERE SERVICEID = :serviceId AND IS_SEND = 0 ORDER BY DATE(FROM_UNIXTIME(SEND_DATE)) DESC  LIMIT 1", nativeQuery = true)
    Content getContentByServiceId(@Param("serviceId") Integer serviceId);

    @Query(value = "SELECT * FROM content WHERE SERVICEID = :serviceId ORDER BY DATE(FROM_UNIXTIME(SEND_DATE)) DESC  LIMIT 1", nativeQuery = true)
    Content getLatestContentByServiceId(@Param("serviceId") Integer serviceId);
}
