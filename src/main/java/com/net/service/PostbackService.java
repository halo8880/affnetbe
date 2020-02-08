package com.net.service;

import com.net.entity.User;
import com.net.entity.UserReferalRelation;
import com.net.repository.UserReferalRelationRepository;
import com.net.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class PostbackService {
	@Autowired
	UserReferalRelationRepository userReferalRelationRepository;
	@Autowired
	UserRepository userRepository;

	public void creditToUpperUser(Long lowerUserId, Integer originalPoint) {
		Optional<UserReferalRelation> refRelation =
				userReferalRelationRepository.findByLowerUserId(lowerUserId);
		if (refRelation.isPresent()) {
			Optional<User> upperUser = userRepository.findById(refRelation.get().getUpperUserId());
			if (upperUser.isPresent()) {
				Integer refComission = originalPoint * refRelation.get().getPercent() / 100;
				Integer newRefPoint = upperUser.get().getRefPoint() + refComission;
				Integer newPoint = upperUser.get().getPoint() + refComission;
				upperUser.get().setPoint(newPoint);
				upperUser.get().setRefPoint(newRefPoint);
				userRepository.save(upperUser.get());
			}else {
				log.info("Upper user not exists");
			}
		}
	}
}
