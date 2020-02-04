package com.net.mapper;

import com.net.dto.prize.PrizeTypeDTO;
import com.net.entity.PrizeType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrizeTypeMapper {
	PrizeTypeDTO prizeTypeToDTO(PrizeType prizeType);
}
