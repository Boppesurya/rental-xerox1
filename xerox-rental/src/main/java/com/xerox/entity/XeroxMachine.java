package com.xerox.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "xerox_machine")
public class XeroxMachine {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String model;

	    @ManyToOne
	    @JoinColumn(name = "rental_id", nullable = false)
	    private User rental;

	    @ManyToOne
	    @JoinColumn(name = "owner_id", nullable = false)
	    private User owner;

	    @Column(nullable = false)
	    private String status;

}
