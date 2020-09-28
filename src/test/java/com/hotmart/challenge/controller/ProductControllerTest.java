/**
 * 
 */
package com.hotmart.challenge.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.hotmart.challenge.BaseTest;
import com.hotmart.challenge.domain.Product;
import com.hotmart.challenge.dto.ProductDTO;
import com.hotmart.challenge.error.handling.EntityNotFoundException;
import com.hotmart.challenge.service.ProductCategoryService;
import com.hotmart.challenge.service.ProductService;

/**
 * Class for ProductController.java unit tests 
 * @author junio
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest extends BaseTest {
	
	@MockBean
	private ProductService productService;
	
	@MockBean
	private ProductCategoryService productCategoryService;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void givenAllArgs_whenCreate_thenCreated() throws Exception {
		ProductDTO productDTO = buildProductDTO();
		Product productCreated = buildProductCreated();

		when(productService.create(productDTO)).thenReturn(productCreated);
		
		mockMvc.perform(
				post("/products")
				.content("{\"name\":\"" + productDTO.getName() + "\", \"description\": \"" + productDTO.getDescription() + "\", \"productCategoryName\": \"" + productDTO.getProductCategoryName() + "\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void givenBadArgName_whenCreate_thenBadRequest() throws Exception {
		ProductDTO productDTO = buildProductDTO();
		
		mockMvc.perform(
				post("/products")
				.content("{\"name\":\"\", \"description\": \"" + productDTO.getDescription() + "\", \"productCategoryName\": \"" + productDTO.getProductCategoryName() + "\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(result -> assertThat(result.getResolvedException().getMessage()).containsIgnoringCase("Name is required"));
	}
	
	@Test
	public void givenTooLongArgName_whenCreate_thenBadRequest() throws Exception {
		ProductDTO productDTO = buildProductDTO();
		productDTO.setName("Lorem ipsum dolor sit amet, consectetur adipiscing elit vel.");
		
		mockMvc.perform(
				post("/products")
				.content("{\"name\":\"\", \"description\": \"" + productDTO.getDescription() + "\", \"productCategoryName\": \"" + productDTO.getProductCategoryName() + "\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(result -> assertThat(result.getResolvedException().getMessage()).containsIgnoringCase("Name is too long"));
	}
	
	@Test
	public void givenBadArgDescription_whenCreate_thenBadRequest() throws Exception {
		ProductDTO productDTO = buildProductDTO();
		
		mockMvc.perform(
				post("/products")
				.content("{\"name\":\"" + productDTO.getName() + "\", \"description\": \"\", \"productCategoryName\": \"" + productDTO.getProductCategoryName() + "\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(result -> assertThat(result.getResolvedException().getMessage()).containsIgnoringCase("Description is required"));
	}
	
	@Test
	public void givenTooLongArgDescription_whenCreate_thenBadRequest() throws Exception {
		ProductDTO productDTO = buildProductDTO();
		productDTO.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam nec vulputate nulla, sit amet vehicula lorem efficitur.");
		
		mockMvc.perform(
				post("/products")
				.content("{\"name\":\"" + productDTO.getName() + "\", \"description\": \"\", \"productCategoryName\": \"" + productDTO.getProductCategoryName() + "\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(result -> assertThat(result.getResolvedException().getMessage()).containsIgnoringCase("Description is too long"));
	}
	
	@Test
	public void givenBadArgProductCategory_whenCreate_thenBadRequest() throws Exception {
		ProductDTO productDTO = buildProductDTO();
		
		mockMvc.perform(
				post("/products")
				.content("{\"name\":\"" + productDTO.getName() + "\", \"description\": \"" + productDTO.getDescription() + "\", \"productCategoryName\": \"\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(result -> assertThat(result.getResolvedException().getMessage()).containsIgnoringCase("Product Category is required"));
	}
	
	@Test
	public void givenTooLongArgProductCategory_whenCreate_thenBadRequest() throws Exception {
		ProductDTO productDTO = buildProductDTO();
		productDTO.setProductCategoryName("Lorem ipsum dolor sit amet, consectetur adipiscing elit vel.");
		
		mockMvc.perform(
				post("/products")
				.content("{\"name\":\"" + productDTO.getName() + "\", \"description\": \"" + productDTO.getDescription() + "\", \"productCategoryName\": \"\"}")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
				.andExpect(result -> assertThat(result.getResolvedException().getMessage()).containsIgnoringCase("Product Category is too long"));
	}
	
	@Test
	public void givenId_whenFind_thenOk() throws Exception {
		Long id = 1L;

		when(productService.find(id)).thenReturn(Product.builder().id(1L).build());

		mockMvc.perform(get("/products/{id}", id)).andExpect(status().isOk());
	}

	@Test
	public void givenBadId_whenFind_thenNotFound() throws Exception {
		Long id = -1L;
		
		when(productService.find(id)).thenThrow(EntityNotFoundException.class);
		
		mockMvc.perform(get("/products/{id}", id))
			.andExpect(status().isNotFound())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
	}
	
	@Test
	public void whenFindAll_thenOk() throws Exception {
		List<Product> products = new ArrayList<Product>();
		products.add(Product.builder().id(1L).build());
		products.add(Product.builder().id(2L).build());
		products.add(Product.builder().id(3L).build());

		when(productService.findAll(PageRequest.of(0, 20))).thenReturn(new PageImpl<Product>(products));

		mockMvc.perform(get("/products")).andExpect(status().isOk());
	}
	
	@Test
	public void whenUpdate_thenNoContent() throws Exception {
		ProductDTO productDTO = buildProductDTO();
		Product product = buildProductCreated();
		Long id = 1L; 

		when(productService.update(id, productDTO)).thenReturn(product);
		
		mockMvc.perform(put("/products/{id}", id)
			.content("{\"name\":\"" + productDTO.getName() + "\", \"description\": \"" + productDTO.getDescription() + "\", \"productCategoryName\": \"" + productDTO.getProductCategoryName() + "\"}")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}
	
	@Test
	public void whenDelete_thenNoContent() throws Exception {
		Long id = 1L;

		doNothing().when(productService).delete(1L);

		mockMvc.perform(delete("/products/{id}", id)).andExpect(status().isNoContent());
	}
	
}
