/**
 * 
 */
package com.hotmart.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.hotmart.challenge.BaseTest;
import com.hotmart.challenge.domain.Product;
import com.hotmart.challenge.repository.ProductRepository;

/**
 * Class for RankingService.java unit tests
 * 
 * @author junio
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RankingServiceTest extends BaseTest {

	@InjectMocks
	private RankingService rankingService;

	@Mock
	private ProductRepository productRepository;

	@Test
	public void givenNoArgs_whenFindProducts_thenReturnEquals() {
		String term = "";
		List<Product> products = buildProducts();

		when(productRepository.findAll()).thenReturn(products);

		assertEquals(products, rankingService.findProducts(term));
	}
	
	@Test
	public void givenTerm_whenFindProducts_thenReturnEquals() {
		String term = "Books";
		List<Product> products = buildBooksProducts();

		when(productRepository.findByNameOrProductCategoryNameOrderByScoreDescNameAscProductCategoryNameAsc(term, term)).thenReturn(products);

		assertEquals(products, rankingService.findProducts(term));
	}
}
