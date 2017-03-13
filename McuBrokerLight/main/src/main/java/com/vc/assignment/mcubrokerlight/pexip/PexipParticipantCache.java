package com.vc.assignment.mcubrokerlight.pexip;

import com.vc.assignment.pexipmock.v1.client.ParticipantApi;
import com.vc.assignment.pexipmock.v1.model.Participant;
import com.vc.assignment.pexipmock.v1.model.ParticipantList;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;

@Service @Log4j
public class PexipParticipantCache {

	private final ParticipantApi participantApi;
	@Getter private volatile Map<UUID, PexipParticipant> participants = new HashMap<>();

	@Autowired
	public PexipParticipantCache(ParticipantApi participantApi) {
		this.participantApi = participantApi;
	}

	public PexipParticipant getParticipant(UUID participantId) {
		return participants.get(participantId);
	}

	@SneakyThrows
	public void buildCache() {
		Map<UUID, PexipParticipant> participantsNew = new HashMap<>();
		ParticipantList participantsFromPexip = participantApi.getParticipants();

		participantsFromPexip.getParticipants().forEach(p -> {
			participantsNew.put(UUID.fromString(p.getParticipantId()), convert(p));
		});

		this.participants = participantsNew;
	}

	private PexipParticipant convert(Participant p) {
		PexipParticipant pexipParticipant = new PexipParticipant();
		pexipParticipant.setParticipantId(UUID.fromString(p.getParticipantId()));
		pexipParticipant.setMuted(p.getMuted());
		return pexipParticipant;
	}
}
