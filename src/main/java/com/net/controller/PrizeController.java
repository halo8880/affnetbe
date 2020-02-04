package com.net.controller;

import com.net.dto.prize.PrizeTypeDTO;
import com.net.dto.prize.RequestPrizeDTO;
import com.net.entity.PrizePointValue;
import com.net.entity.PrizeRequest;
import com.net.entity.PrizeType;
import com.net.entity.User;
import com.net.enums.PrizeEnums;
import com.net.mapper.PrizeTypeMapper;
import com.net.repository.PrizeRequestRepository;
import com.net.repository.PrizeTypeRepository;
import com.net.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prize")
public class PrizeController {
	@Autowired
	UserService userService;
	@Autowired
	PrizeTypeRepository prizeTypeRepository;
	@Autowired
	PrizeRequestRepository prizeRequestRepository;

	@Autowired
	PrizeTypeMapper prizeTypeMapper;

	@GetMapping("/prize-types")
	public List<PrizeTypeDTO> getAllPrizeTypes() {
		return ((List<PrizeType>) prizeTypeRepository.findAll()).stream()
				.map(prizeTypeMapper::prizeTypeToDTO)
				.collect(Collectors.toList());
	}

	@PostMapping("/request")
	public PrizeRequest claimRequest(@RequestBody RequestPrizeDTO requestPrizeDTO) throws Exception {
		PrizeRequest entity = new PrizeRequest();
		entity.setStatus(PrizeEnums.PrizeRequestStatus.WAITING);
		entity.setUser(new User(userService.getLoggedInUser().getId()));
		entity.setPrizePointValue(new PrizePointValue(Long.parseLong(requestPrizeDTO.getPrizeValueId())));
		entity.setPrizeType(new PrizeType(Long.parseLong(requestPrizeDTO.getPrizeId())));
		entity.setRequestDate(LocalDate.now(ZoneOffset.UTC));
		return prizeRequestRepository.save(entity);
	}
}
