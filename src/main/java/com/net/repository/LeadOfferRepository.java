package com.net.repository;

import com.net.entity.LeadOffer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LeadOfferRepository extends CrudRepository<LeadOffer, Long> {
	List<LeadOffer> findByUserId(Long userId);
}
