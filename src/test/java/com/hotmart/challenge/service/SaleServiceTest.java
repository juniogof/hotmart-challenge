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
import com.hotmart.challenge.domain.Sale;
import com.hotmart.challenge.error.handling.EntityNotFoundException;
import com.hotmart.challenge.repository.SaleRepository;

/**
 * Class for SaleService.java unit tests
 * 
 * @author junio
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SaleServiceTest extends BaseTest {

	@InjectMocks
	private SaleService saleService;
	@Mock
	private SaleRepository saleRepository;

	@Test
	public void whenFind_thenReturnEquals() {
		Long id = 1L;
		Sale sale = buildSale();
		
		when(saleRepository.findById(id)).thenReturn(Optional.of(sale));
		
		assertEquals(sale, saleService.find(id));
	}

	@Test
	public void givenBadId_whenFind_thenNotFound() {
		Long id = -1L;

		when(saleRepository.findById(id)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> {
			saleService.find(id);
		}).isInstanceOf(EntityNotFoundException.class).hasMessageContaining("was not found for parameters");
	}
	
	@Test
	public void whenUpdate_thenReturnEquals() {
		Sale sale = buildSale();

		when(saleRepository.findById(sale.getId())).thenReturn(Optional.of(sale));
		when(saleRepository.save(sale)).thenReturn(sale);

		assertEquals(sale, saleService.updateScore(sale.getId(), sale.getUserScore()));
	}
	
	@Test
	public void givenBadId_whenUpdate_thenNotFound() {
		Long id = -1L;
		when(saleRepository.findById(id)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> {
			saleService.updateScore(id, 3);
		}).isInstanceOf(EntityNotFoundException.class).hasMessageContaining("was not found for parameters");
	}
}
