/**
 * 
 */
package com.hotmart.challenge.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product Data Transfer Object
 * 
 * @author junio
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	@NotBlank(message = "Name is required")
	@Length(min = 1, max = 50, message = "Name is too long")
	@ApiModelProperty(value = "name of the product", name = "name", dataType = "String", example = "Playstation 5 Console")
	private String name;

	@NotBlank(message = "Description is required")
	@Length(min = 1, max = 100, message = "Description is too long")
	@ApiModelProperty(value = "decription of the product", name = "description", dataType = "String", example = "The PS5 console unleashes new gaming possibilities that you never anticipated.")
	private String description;

	@NotBlank(message = "Product Category is required")
	@Length(min = 1, max = 50, message = "Product Category is too long")
	@ApiModelProperty(value = "name of the category product", name = "productCategoryName", dataType = "String", example = "Video Game Consoles")
	private String productCategoryName;
}