/**
 * 
 */
package com.hotmart.challenge.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hotmart.challenge.domain.Sale;
import com.hotmart.challenge.error.handling.EntityNotFoundException;
import com.hotmart.challenge.repository.SaleRepository;

import lombok.AllArgsConstructor;

/**
 * Ranking service
 * 
 * @author junio
 *
 */
@Service
@AllArgsConstructor
public class SaleService {

	private final SaleRepository saleRepository;

	public Sale updateScore(Long idSale, Integer userScore) {
		Sale sale = find(idSale);
		sale.setUserScore(userScore);

		return saleRepository.save(sale);
	}

	public Sale find(Long id) {
		Optional<Sale> sale = saleRepository.findById(id);

		return sale.orElseThrow(() -> new EntityNotFoundException(Sale.class, "id", id.toString()));
	}

}
