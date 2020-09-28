/**
 * 
 */
package com.hotmart.challenge.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hotmart.challenge.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ranking Data Transfer Object
 * 
 * @author junio
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankingDTO {

	@JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
	private LocalDateTime currentDateTime;
	private String term;
	private List<Product> products;

}