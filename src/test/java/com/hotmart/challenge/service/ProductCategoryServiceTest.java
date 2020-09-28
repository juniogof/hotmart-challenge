/**
 * 
 */
package com.hotmart.challenge.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.hotmart.challenge.BaseTest;
import com.hotmart.challenge.domain.ProductCategory;
import com.hotmart.challenge.error.handling.EntityNotFoundException;
import com.hotmart.challenge.repository.ProductCategoryRepository;

/**
 * Class for ProductCategoryService.java unit tests
 * 
 * @author junio
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductCategoryServiceTest extends BaseTest {

	@InjectMocks
	private ProductCategoryService service;

	@Mock
	private ProductCategoryRepository repository;

	@Test
	public void whenFindByName_thenReturnEquals() {
		ProductCategory productCategory = buildProductCategory();

		when(repository.findByName(productCategory.getName())).thenReturn(Optional.of(productCategory));

		assertEquals(productCategory, service.findByName(productCategory.getName()));
	}

	@Test
	public void givenBadName_whenFindByName_thenNotFound() {
		when(repository.findByName("This category was not found")).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> {
			service.findByName("This category was not found");
		}).isInstanceOf(EntityNotFoundException.class).hasMessageContaining("was not found for parameters");
	}
}
