package com.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.controller.form.CountActivityCompleteAndMissingForm;
import com.controller.form.CountActivityCompleteByPromoterForm;

@Service
public class ApiService {
	
	@Autowired
	RestTemplate restTemplate;
	
	
	public CountActivityCompleteAndMissingForm[] getCountActivityCompleteAndMissing(LocalDate date){
		var response = restTemplate.getForObject("http://192.168.1.104:8081/datatask/countactivitycomplete?date={date}", CountActivityCompleteAndMissingForm[].class,date.toString());
	    return response;
	}
	
	public CountActivityCompleteByPromoterForm[] getCountActivityCompleteAndMissingByPromoter(LocalDate date){
		var response = restTemplate.getForObject("http://192.168.1.104:8081/datatask/countactivitycompletebypromoter?date={date}", CountActivityCompleteByPromoterForm[].class,date.toString());
	    return response;
	}
	
}
