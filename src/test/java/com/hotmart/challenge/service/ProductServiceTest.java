/**
 * 
 */
package com.hotmart.challenge.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.hotmart.challenge.BaseTest;
import com.hotmart.challenge.domain.Product;
import com.hotmart.challenge.domain.ProductCategory;
import com.hotmart.challenge.dto.ProductDTO;
import com.hotmart.challenge.error.handling.EntityNotFoundException;
import com.hotmart.challenge.repository.ProductRepository;

/**
 * Class for ProductService.java unit tests
 * 
 * @author junio
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest extends BaseTest {

	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private ProductCategoryService productCategoryService;

	@Test
	public void whenCreate_thenReturnNotNull() {
		ProductDTO productDTO = buildProductDTO();
		Product productCreated = buildProductCreated();

		when(productRepository.save(Mockito.any())).thenReturn(productCreated);

		assertNotNull(productService.create(productDTO));
	}
	
	@Test
	public void givenBadProductCategory_whenCreate_thenNotFound() {
		ProductDTO productDTO = buildProductDTO();
		productDTO.setProductCategoryName("Product category was not found");

		when(productCategoryService.findByName(productDTO.getProductCategoryName()))
				.thenThrow(EntityNotFoundException.class);

		assertThatThrownBy(() -> {
			productService.create(productDTO);
		}).isInstanceOf(EntityNotFoundException.class);
	}
	
	@Test
	public void whenFind_thenReturnEquals() {
		Long id = 1L;
		Product product = buildProductCreated();
		
		when(productRepository.findById(id)).thenReturn(Optional.of(product));
		
		assertEquals(product, productService.find(id));
	}

	@Test
	public void givenBadId_whenFind_thenNotFound() {
		Long id = -1L;

		when(productRepository.findById(id)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> {
			productService.find(id);
		}).isInstanceOf(EntityNotFoundException.class).hasMessageContaining("was not found for parameters");
	}

	@Test
	public void whenFindAll_thenReturnEquals() {
		List<Product> products = new ArrayList<Product>();
		products.add(Product.builder().id(1L).build());
		products.add(Product.builder().id(2L).build());
		products.add(Product.builder().id(3L).build());
		Page<Product> page = new PageImpl<Product>(products);
		
		when(productRepository.findAll(PageRequest.of(0, 20))).thenReturn(page);
		
		assertEquals(page, productService.findAll(PageRequest.of(0, 20)));
	}
	
	@Test
	public void whenUpdate_thenReturnEquals() {
		ProductDTO productDTO = buildProductDTOUpdate();
		ProductCategory productCategory = buildProductCategoryUpdate();
		Product productOld = buildProductCreated();
		Product product = buildProductUpdated();
		Long id = 1L;

		when(productRepository.findById(id)).thenReturn(Optional.of(productOld));
		when(productCategoryService.findByName(productDTO.getProductCategoryName())).thenReturn(productCategory);
		when(productRepository.save(product)).thenReturn(product);

		assertEquals(product, productService.update(id, productDTO));
	}

	@Test
	public void givenBadId_whenUpdate_thenNotFound() {
		ProductDTO productDTO = buildProductDTO();
		Long id = -1L;

		when(productRepository.findById(id)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> {
			productService.update(id, productDTO);
		}).isInstanceOf(EntityNotFoundException.class).hasMessageContaining("was not found for parameters");
	}

	@Test
	public void givenBadProductCategory_whenUpdate_thenNotFound() {
		String badProductCategory = "This category was not found";
		ProductDTO productDTO = buildProductDTOUpdate();
		productDTO.setProductCategoryName(badProductCategory);
		Product product = buildProductCreated();
		Long id = 1L;

		when(productRepository.findById(id)).thenReturn(Optional.of(product));
		when(productCategoryService.findByName(badProductCategory)).thenThrow(EntityNotFoundException.class);

		assertThatThrownBy(() -> {
			productService.update(id, productDTO);
		}).isInstanceOf(EntityNotFoundException.class);
	}

	@Test
	public void whenDelete_thenVerify() {
		Product product = buildProductCreated();

		when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
		doNothing().when(productRepository).delete(product);

		productService.delete(product.getId());

		verify(productRepository, times(1)).delete(product);
	}

	@Test
	public void givenBadId_whenDelete_thenNotFound() {
		Long id = -1L;

		when(productRepository.findById(id)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> {
			productService.delete(id);
		}).isInstanceOf(EntityNotFoundException.class).hasMessageContaining("was not found for parameters");
	}
}
