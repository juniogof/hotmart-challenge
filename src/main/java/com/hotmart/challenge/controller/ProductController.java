package com.hotmart.challenge.controller;

import java.net.HttpURLConnection;
import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hotmart.challenge.domain.Product;
import com.hotmart.challenge.dto.ProductDTO;
import com.hotmart.challenge.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

/**
 * Endpoints to products maintence
 * @author junio
 *
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Api(tags = {"Product CRUD"})
public class ProductController {

	private final ProductService productService;

	@PostMapping
	@ApiOperation(value = "Create a new product")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "Successfully created a product"),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "A parameter is required"),
			@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found the product category")
	})
	public ResponseEntity<Object> create(@RequestBody @Valid ProductDTO productDTO) {
		Product product = productService.create(productDTO);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(product.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Find a product")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully found a product"),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "A parameter is required"),
			@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found the product")
	})
	public ResponseEntity<Product> find(@PathVariable
			@ApiParam(name = "id", type = "Long", value = "Product ID", example = "1")
			final Long id) {
		return ResponseEntity.ok(productService.find(id));
	}

	@GetMapping
	@ApiOperation(value = "List all products pageable")
	@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Successfully found the products")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page"),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc) "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	public ResponseEntity<Page<Product>> findAll(Pageable pageable) {
		return ResponseEntity.ok(productService.findAll(pageable));
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Update a product")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Successfully updated a product"),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "A parameter is required"),
			@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found the product to update")
	})
	public ResponseEntity<Object> update(@PathVariable
			@ApiParam(name = "id", type = "Long", value = "Product ID", example = "1")
			final Long id, @RequestBody ProductDTO productDTO) {
		productService.update(id, productDTO);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a product")
	@ApiResponses(value = {
			@ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Successfully updated a product"),
			@ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "A parameter is required"),
			@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found the product to delete")
	})
	public ResponseEntity<Object> delete(@PathVariable
			@ApiParam(name = "id", type = "Long", value = "Product ID", example = "1")
			final Long id) {
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}

}