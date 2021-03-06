/**
 * 
 */
package com.baominh.sdp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baominh.sdp.entity.Setting;

/**
 * @author HungDT
 *
 */
@Repository
public interface SettingRepository extends JpaRepository<Setting, Integer> {
	
}
