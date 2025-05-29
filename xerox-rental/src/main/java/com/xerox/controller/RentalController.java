package com.xerox.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xerox.entity.Bill;
import com.xerox.entity.User;
import com.xerox.entity.XeroxMachine;
import com.xerox.repository.BillRepository;
import com.xerox.repository.UserRepository;
import com.xerox.repository.XeroxMachineRepository;
@RestController
@RequestMapping("/api/rental")
public class RentalController {
	  @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private XeroxMachineRepository xeroxMachineRepository;

	    @Autowired
	    private BillRepository billRepository;

	    /**
	     * Lists all machines assigned to the rental.
	     */
	    @GetMapping("/machines")
	    public ResponseEntity<List<XeroxMachine>> getMachines(Principal principal) {
	        User rental = userRepository.findByUsername(principal.getName()).orElse(null);
	        if (rental == null) {
	            return ResponseEntity.status(401).build();
	        }
	        return ResponseEntity.ok(xeroxMachineRepository.findByRentalId(rental.getId()));
	    }

	    /**
	     * Lists all bills assigned to the rental.
	     */
	    @GetMapping("/bills")
	    public ResponseEntity<List<Bill>> getBills(Principal principal) {
	        User rental = userRepository.findByUsername(principal.getName()).orElse(null);
	        if (rental == null) {
	            return ResponseEntity.status(401).build();
	        }
	        return ResponseEntity.ok(billRepository.findByRentalId(rental.getId()));
	    }

	    /**
	     * Marks a bill as paid.
	     */
	    @PostMapping("/bills/{billId}/pay")
	    public ResponseEntity<?> payBill(@PathVariable Long billId, Principal principal) {
	        User rental = userRepository.findByUsername(principal.getName()).orElse(null);
	        Bill bill = billRepository.findById(billId).orElse(null);
	        if (rental == null || bill == null || !bill.getRental().getId().equals(rental.getId())) {
	            return ResponseEntity.badRequest().body(Map.of("error", "Invalid request"));
	        }
	        bill.setStatus("Paid");
	        billRepository.save(bill);
	        return ResponseEntity.ok(Map.of("message", "Bill paid"));
	    }

}
