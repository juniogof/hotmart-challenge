/**
 * 
 */
package com.hotmart.challenge.controller;

import java.net.HttpURLConnection;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotmart.challenge.dto.SaleDTO;
import com.hotmart.challenge.service.SaleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

/**
 * Endpoint to score sales
 * 
 * @author junio
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sales")
@Api(tags = { "Sale" })
public class ScoreController {

	private final SaleService saleService;

	@PutMapping("/{id}/score")
	@ApiOperation(value = "Score a sale")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Successfully updated a product"),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "UserScore must be between 1 and 5"),
			@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found the sale")
	})
	public ResponseEntity<Object> scoreSale(@PathVariable
			@ApiParam(name = "id", type = "Long", value = "Sale ID", example = "1")
			final Long id,
			@RequestBody @Valid SaleDTO saleDTO) {

		saleService.updateScore(id, saleDTO.getUserScore());

		return ResponseEntity.noContent().build();
	}
}
