package com.net.repository;

import com.net.entity.UpdateInfoRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UpdateInfoRequestRepository extends CrudRepository<UpdateInfoRequest, Long> {
	Optional<UpdateInfoRequest> findFirstByConfirmCodeAndStatus(String confirmCode, UpdateInfoRequest.Status status);
}
