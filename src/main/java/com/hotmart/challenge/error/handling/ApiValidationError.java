/**
 * 
 */
package com.hotmart.challenge.error.handling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Class to response API validation errors
 * 
 * @author junio
 *
 */

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ApiValidationError implements ApiSubError {

	private String field;
	private Object rejectedValue;
	private String message;

}