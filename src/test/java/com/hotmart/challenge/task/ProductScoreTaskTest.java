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
import com.hotmart.challenge.repository.ProductRepository;
import com.hotmart.challenge.repository.SaleRepository;

/**
 * Class for ProductScoreTask.java unit tests
 * 
 * @author junio
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductScoreTaskTest extends BaseTest {

	@InjectMocks
	private ProductScoreTask productScoreTask;
	
	@Mock
	private CategoryNewsRepository categoryNewsRepository;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private SaleRepository saleRepository;

	@Test
	public void whenCalcProductScore_thenVerify() {
		when(productRepository.findAll()).thenReturn(buildProducts());
		when(saleRepository.averageUserScoreByProductIdAndSaleDateGreaterThanAndUserScoreIsNotNull(Mockito.anyLong(),
				Mockito.any())).thenReturn(2.5f);
		when(saleRepository.countByProductId(Mockito.anyLong())).thenReturn(20L);
		when(categoryNewsRepository.findFirstByProductCategoryIdAndNewsDateOrderByUpdateAtDesc(Mockito.anyLong(),
				Mockito.any())).thenReturn(buildCategoryNews());
		when(productRepository.save(Mockito.any())).thenReturn(buildProductScored());
		
		productScoreTask.scheduledTaskToCalcProductScore();
		
		verify(productRepository, times(3)).save(Mockito.any());
	}
	
	@Test
	public void givenNoneSale_whenCalcProductScore_thenVerify() {
		when(productRepository.findAll()).thenReturn(buildProducts());
		when(saleRepository.averageUserScoreByProductIdAndSaleDateGreaterThanAndUserScoreIsNotNull(Mockito.anyLong(),
				Mockito.any())).thenReturn(2.5f);
		when(saleRepository.countByProductId(Mockito.anyLong())).thenReturn(0L);
		when(categoryNewsRepository.findFirstByProductCategoryIdAndNewsDateOrderByUpdateAtDesc(Mockito.anyLong(),
				Mockito.any())).thenReturn(buildCategoryNews());
		when(productRepository.save(Mockito.any())).thenReturn(buildProductScored());
		
		productScoreTask.scheduledTaskToCalcProductScore();
		
		verify(productRepository, times(3)).save(Mockito.any());
	}
	
	@Test
	public void givenNoProduct_whenCalcProductScore_thenVerify() {
		when(productRepository.findAll()).thenReturn(Collections.emptyList());
		
		productScoreTask.scheduledTaskToCalcProductScore();
		
		verify(productRepository, times(0)).save(Mockito.any());
	}
}
