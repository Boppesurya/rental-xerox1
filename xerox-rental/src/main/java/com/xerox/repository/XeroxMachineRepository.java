package com.xerox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xerox.entity.XeroxMachine;

public interface XeroxMachineRepository extends JpaRepository<XeroxMachine, Long> {
	List<XeroxMachine> findByOwnerId(Long ownerId);
    List<XeroxMachine> findByRentalId(Long rentalId);

}
