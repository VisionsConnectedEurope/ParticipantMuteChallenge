package com.vc.assignment.mcubrokerlight.service;

import com.vc.assignment.mcubrokerlight.pexip.PexipParticipant;
import com.vc.assignment.mcubrokerlight.pexip.PexipParticipantCache;
import com.vc.assignment.pexipmock.v1.client.ParticipantApi;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;

@Service @Log4j
public class ParticipantService {

	private final ParticipantApi participantApi;
	private final PexipParticipantCache pexipParticipantCache;

	@Autowired
	public ParticipantService(PexipParticipantCache pexipParticipantCache, ParticipantApi participantApi) {
		this.pexipParticipantCache = pexipParticipantCache;
		this.participantApi = participantApi;
	}

	@Scheduled(fixedDelay = 1000)
	void updateCaches() {
		pexipParticipantCache.buildCache();
	}

	public List<PexipParticipant> getParticipants() {
		return new ArrayList<>(pexipParticipantCache.getParticipants().values());
	}

	public PexipParticipant getParticipant(UUID participantId) {
		return pexipParticipantCache.getParticipant(participantId);
	}

	@SneakyThrows
	public void addParticipant(UUID participantId) {
		participantApi.getParticipant(participantId.toString());
		pexipParticipantCache.buildCache();
	}

	@SneakyThrows
	public void muteParticipant(UUID participantId) {
		participantApi.muteParticipant(participantId.toString());
	}

	@SneakyThrows
	public void unmuteParticipant(UUID participantId) {
		participantApi.muteParticipant(participantId.toString());
	}
}
