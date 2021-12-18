package com.iit.demo.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Medication.
 */
@RestController
public class HealthCheck {

	@GetMapping("/health")
	public String healthCheck() {
		return "in healthy";
	}

}
