/**
 * 
 */
package com.baominh.sdp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baominh.sdp.entity.Content;

/**
 * @author HungDT
 *
 */
@Repository
public interface ContentRepository extends JpaRepository<Content, Integer> {

}
