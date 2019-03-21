package com.baominh.sdp.repository.jpa;

import com.baominh.sdp.entity.Mtqueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MtqueueRepository extends JpaRepository<Mtqueue, Integer> {

}
