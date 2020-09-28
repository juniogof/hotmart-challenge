/**
 * 
 */
package com.hotmart.challenge.controller;

import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotmart.challenge.domain.Product;
import com.hotmart.challenge.dto.RankingDTO;
import com.hotmart.challenge.service.RankingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;

/**
 * Endpoint to products ranking
 * @author junio
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
@Api(tags = {"Ranking"})
public class RankingController {
	
	private final RankingService rankingService; 
	
	@GetMapping
	@ApiOperation(value = "Rank products")
	@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully listed the rank")
	public ResponseEntity<RankingDTO> ranking(
			@RequestParam(name = "term", defaultValue = "") 
			@ApiParam(name = "term", type = "String", value = "Product Category or Product Name", example = "Books") 
			String term) {
		
		List<Product> products = rankingService.findProducts(term);
		
		return ResponseEntity.ok(RankingDTO.builder()
						.currentDateTime(LocalDateTime.now())
						.term(term)
						.products(products)
						.build());
	}
}
