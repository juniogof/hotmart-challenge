/**
 * 
 */
package com.hotmart.challenge.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Sale Transfer Object
 * 
 * @author junio
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {

	@Min(value = 1, message = "UserScore can't be less than 1")
	@Max(value = 5, message = "UserScore can't be more than 5")
	@ApiModelProperty(value = "user score for the sale. Must be between 1 and 5", name = "userScore", dataType = "Integer", example = "3")
	private Integer userScore;
	
}