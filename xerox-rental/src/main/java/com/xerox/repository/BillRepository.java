package com.xerox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xerox.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
	List<Bill> findByRentalId(Long rentalId);
    List<Bill> findByOwnerId(Long ownerId);
}
