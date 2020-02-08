package com.net.controller;

import com.net.dto.user.DashboardDTO;
import com.net.dto.user.DashboardLeadOfferDTO;
import com.net.dto.user.IncomeChartInfoDTO;
import com.net.dto.user.SignUpUserDTO;
import com.net.dto.user.UserCredentials;
import com.net.dto.user.UserDTO;
import com.net.entity.LeadOffer;
import com.net.entity.UpdateInfoRequest;
import com.net.entity.User;
import com.net.mapper.UserMapper;
import com.net.repository.LeadOfferRepository;
import com.net.repository.UpdateInfoRequestRepository;
import com.net.repository.UserRepository;
import com.net.service.EmailSenderService;
import com.net.service.UserDetailsServiceImpl;
import com.net.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserMapper userMapper;
	@Autowired
	UpdateInfoRequestRepository updateInfoRequestRepository;
	@Autowired
	LeadOfferRepository leadOfferRepository;
	@Autowired
	EmailSenderService emailSenderService;

	@GetMapping("/user/current")
	public UserDTO loggedUser() throws Exception {
		User user = userService.getLoggedInUser();
		return userMapper.userToUserDTO(user);
	}

	@PostMapping("signup")
	public String signup(@RequestBody UserCredentials user) throws Exception {
		return userService.signUp(user).getUsername();
	}

	@GetMapping("/user/confirm")
	public void confirmUpdateInfo(@RequestParam("confirmCode") String confirmCode) throws Exception {
		Optional<UpdateInfoRequest> updateRequest = updateInfoRequestRepository.findFirstByConfirmCodeAndStatus(confirmCode, UpdateInfoRequest.Status.WAITING);
		if (updateRequest.isPresent()) {
			if (updateRequest.get().getInfoType() == UpdateInfoRequest.InfoType.PAYPAL) {
				User user = updateRequest.get().getUser();
				user.setPaypal(updateRequest.get().getNewInfo());
				userRepository.save(user);
				updateRequest.get().setStatus(UpdateInfoRequest.Status.PROCESSED);
				updateInfoRequestRepository.save(updateRequest.get());
			}
		} else {
			throw new Exception("Request does not exists");
		}
	}

	@PostMapping("/user/update-paypal")
	public void updatePaypalInfo(@RequestBody Map<String, String> updatedInfo) throws Exception {
		userService.updatePaypal(updatedInfo);
	}



	@GetMapping("/user/dashboard")
	public DashboardDTO dashboardInfo() throws Exception {
		DashboardDTO rs = new DashboardDTO();
		User user = userService.getLoggedInUser();

		List<LeadOffer> leads = leadOfferRepository.findByUserId(user.getId());
		leads.sort(Comparator.comparing(LeadOffer::getLeadDate).reversed());
		YearMonth thisMonth = YearMonth.now(ZoneOffset.UTC);
		LocalDate firstDateOfMonth = thisMonth.atDay(1);
		List<LeadOffer> leadsThisMonth = leads.stream()
				.filter(leadOffer -> !leadOffer.getLeadDate().isBefore(firstDateOfMonth))
				.collect(Collectors.toList());
		Map<LocalDate, Integer> infoDTOList = new HashMap<>();
		LocalDate lastDateOfMonth = LocalDate.now(ZoneOffset.UTC);
		for (LocalDate tempDate = LocalDate.from(firstDateOfMonth); !tempDate.isAfter(lastDateOfMonth); tempDate = tempDate.plusDays(1)) {
			infoDTOList.put(tempDate, 0);
		}

		leadsThisMonth.forEach(lead -> {
			if (infoDTOList.get(lead.getLeadDate()) == null) {
				infoDTOList.put(lead.getLeadDate(), 0);
			} else {
				infoDTOList.put(lead.getLeadDate(), infoDTOList.get(lead.getLeadDate()) + 1);
			}
		});
		List<IncomeChartInfoDTO> chartInfoDTOs = new ArrayList<>();
		infoDTOList.forEach((localDate, integer) -> {
			IncomeChartInfoDTO chartInfoDTO = new IncomeChartInfoDTO();
			chartInfoDTO.setTime(localDate);
			chartInfoDTO.setNumberOfLeads(integer);
			chartInfoDTOs.add(chartInfoDTO);
		});
		chartInfoDTOs.sort(Comparator.comparing(IncomeChartInfoDTO::getTime));
		rs.setIncomeChartInfos(chartInfoDTOs);
		rs.setTotalLeadThisMonth(leadsThisMonth.size());
		rs.setUserInfo(userMapper.userToUserDTO(user));

		List<DashboardLeadOfferDTO> top10 = leads.stream()
				.map(leadOffer -> {
					DashboardLeadOfferDTO dashboardLeadOfferDTO = new DashboardLeadOfferDTO();
					dashboardLeadOfferDTO.setLeadDate(leadOffer.getLeadDate());
					dashboardLeadOfferDTO.setCampaignName(leadOffer.getCampName());
					dashboardLeadOfferDTO.setPoint(Integer.parseInt(leadOffer.getCommission()));
					return dashboardLeadOfferDTO;
				}).collect(Collectors.toList());
		rs.setTop10LatestLeadOffers(top10);
		return rs;
	}
}
