/**
 * 
 */
package com.hotmart.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import com.hotmart.challenge.BaseTest;
import com.hotmart.challenge.domain.CategoryNews;
import com.hotmart.challenge.domain.ProductCategory;
import com.hotmart.challenge.dto.NewsApiDTO;
import com.hotmart.challenge.repository.CategoryNewsRepository;
import com.hotmart.challenge.repository.ProductCategoryRepository;

/**
 * Class for ProductCategoryService.java unit tests
 * 
 * @author junio
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class NewsApiServiceTest extends BaseTest {

	@InjectMocks
	private NewsApiService newsApiService;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private CategoryNewsRepository categoryNewsRepository;

	@Mock
	private ProductCategoryRepository productCategoryRepository;

	@Test
	public void whenConsumeNewsApi_thenAssertEquals() {
		ProductCategory productCategory = buildProductCategory();
		NewsApiDTO newsApiDTO = builderNewsApiDTO();

		when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(newsApiDTO);

		assertEquals(newsApiDTO, newsApiService.consumeNewsApi(productCategory.getName()));
	}

	@Test
	public void whenFallBack_thenAssertEquals() {
		ProductCategory productCategory = buildProductCategory();
		CategoryNews categoryNews = buildCategoryNews();

		when(productCategoryRepository.findByName(productCategory.getName())).thenReturn(Optional.of(productCategory));
		when(categoryNewsRepository.findFirstByProductCategoryIdAndNewsDateOrderByUpdateAtDesc(Mockito.anyLong(),
				Mockito.any())).thenReturn(categoryNews);
		
		assertEquals(categoryNews.getTotalResults(),
				newsApiService.fallBack(productCategory.getName()).getTotalResults());
	}
}
