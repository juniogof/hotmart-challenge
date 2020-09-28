/**
 * 
 */
package com.hotmart.challenge.task;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hotmart.challenge.BaseTest;
import com.hotmart.challenge.repository.CategoryNewsRepository;
import com.hotmart.challenge.repository.ProductCategoryRepository;
import com.hotmart.challenge.service.NewsApiService;

/**
 * Class for CategoryNewsTask.java unit tests
 * 
 * @author junio
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CategoryNewsTaskTest extends BaseTest {

	@InjectMocks
	private CategoryNewsTask categoryNewsTask;
	@Mock
	private ProductCategoryRepository productCategoryRepository;
	@Mock
	private CategoryNewsRepository categoryNewsRepository;
	@Mock
	private NewsApiService newsApiService;
	
	@Test
	public void whenCreateCategoryNews_thenVerify() {
		when(productCategoryRepository.findAll()).thenReturn(buildProductCategories());
		when(newsApiService.consumeNewsApi(Mockito.anyString())).thenReturn(builderNewsApiDTO());
		when(categoryNewsRepository.save(Mockito.any())).thenReturn(buildCategoryNews());

		categoryNewsTask.scheduledTaskToCreateCategoryNews();

		verify(categoryNewsRepository, times(2)).save(Mockito.any());
	}

	@Test
	public void givenNoProductCategory_whenCreateCategoryNews_thenVerify() {
		when(productCategoryRepository.findAll()).thenReturn(Collections.emptyList());

		categoryNewsTask.scheduledTaskToCreateCategoryNews();

		verify(categoryNewsRepository, times(0)).save(Mockito.any());
	}
}
