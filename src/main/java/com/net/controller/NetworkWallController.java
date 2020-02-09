package com.net.controller;

import com.net.dto.NetworkWallDTO;
import com.net.entity.NetworkWall;
import com.net.mapper.NetworkWallMapper;
import com.net.repository.NetworkWallRepository;
import com.net.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/network")
public class NetworkWallController {
	@Autowired
	NetworkWallRepository networkWallRepository;
	@Autowired
	NetworkWallMapper networkWallMapper;
	@Autowired
	UserService userService;

	@GetMapping("wall")
	public List<NetworkWallDTO> getAlLWalls() {
		return ((List<NetworkWall>) networkWallRepository.findAll()).stream()
				.map(networkWallMapper::networkWallToNetworkWallDTO)
				.collect(Collectors.toList());
	}

	@GetMapping("one-wall")
	public NetworkWallDTO getWall(@RequestParam("netId") String netId) throws Exception {
		Long id = 0L;
		try {
			id = Long.parseLong(netId);
		} catch (Exception e) {
			System.out.println(e);
		}
		Optional<NetworkWall> networkWall = networkWallRepository.findById(id);
		if (!networkWall.isPresent()) {
			return convertWithModifiedUrl(
					((List<NetworkWall>) networkWallRepository.findAll()).stream().findFirst().orElseThrow(() -> new Exception("No network available"))
			);
		} else {
			return convertWithModifiedUrl(networkWall.get());
		}
	}

	private NetworkWallDTO convertWithModifiedUrl(NetworkWall networkWall) throws Exception {
		NetworkWallDTO rs = networkWallMapper.networkWallToNetworkWallDTO(networkWall);
		if (rs.getNetworkName().equalsIgnoreCase("adworkmedia")) {
			rs.setIframeUrl(rs.getIframeUrl() + "/" + userService.getLoggedInUser().getId());
		}
		if (rs.getNetworkName().equalsIgnoreCase("cpalead")) {
			rs.setIframeUrl(rs.getIframeUrl() + "?subid=" + userService.getLoggedInUser().getId());
		}
		if (rs.getNetworkName().equalsIgnoreCase("cpagrip")) {
			rs.setIframeUrl(rs.getIframeUrl() + "?tracking_id=" + userService.getLoggedInUser().getId());
		}
		return rs;
	}
}
