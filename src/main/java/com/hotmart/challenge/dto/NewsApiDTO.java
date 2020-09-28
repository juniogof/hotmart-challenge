/**
 * 
 */
package com.hotmart.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * News Api Data Transfer Object
 * 
 * @author junio
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsApiDTO {

	private String status;

	private Long totalResults;

}