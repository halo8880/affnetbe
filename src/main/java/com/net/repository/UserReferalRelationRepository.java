package com.net.repository;

import com.net.entity.UserReferalRelation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserReferalRelationRepository extends CrudRepository<UserReferalRelation, Long> {
	Optional<UserReferalRelation> findByLowerUserId(Long lowerUserId);
}
