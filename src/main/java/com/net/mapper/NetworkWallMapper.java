package com.net.mapper;

import com.net.dto.NetworkWallDTO;
import com.net.entity.NetworkWall;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NetworkWallMapper {
	NetworkWallDTO networkWallToNetworkWallDTO(NetworkWall networkWall);
}
