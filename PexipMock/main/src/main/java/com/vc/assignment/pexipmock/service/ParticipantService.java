package com.vc.assignment.pexipmock.service;

import com.vc.assignment.pexipmock.dto.ParticipantDto;
import com.vc.assignment.pexipmock.service.DelayedParticipantMuteChange.MuteAction;

import java.util.*;
import java.util.concurrent.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;

@Service @Log4j
public class ParticipantService {

	// Map<participantId, muted>
	private final Map<UUID, ParticipantDto> participants = new HashMap<>();
	private final DelayQueue<DelayedParticipantMuteChange> participantsMuteChange = new DelayQueue<>();

	private final Thread delayQueueThread = new Thread(() -> {
		log.info("delayQueueThread started");
		try {
			for (;;) {
				applyMuteChange(participantsMuteChange.poll(30, TimeUnit.SECONDS));
			}
		} catch (InterruptedException ex) {
			log.info("participantsMuteChange Interrupted");
		}
	}, "delayQueueThread");

	@PostConstruct
	public void init() {
		delayQueueThread.start();
	}

	@PreDestroy
	public void destroy() throws InterruptedException {
		delayQueueThread.interrupt();
		delayQueueThread.join();
		log.info("delayQueueThread stopped");
	}

	public Map<UUID, ParticipantDto> retrieveParticipants() {
		return participants;
	}

	public ParticipantDto retrieveParticipant(UUID participantId) {
		participants.computeIfAbsent(participantId, id -> ParticipantDto.builder().participantId(id).build());
		return participants.get(participantId);
	}

	public void muteParticipant(UUID participantId) {
		log.info("mute participant");
		participants.computeIfAbsent(participantId, id -> ParticipantDto.builder().participantId(id).build());
		participantsMuteChange.add(new DelayedParticipantMuteChange(participantId, MuteAction.MUTE));
	}

	public void unmuteParticipant(UUID participantId) {
		log.info("unmute participant");
		participants.computeIfAbsent(participantId, id -> ParticipantDto.builder().participantId(id).build());
		participantsMuteChange.add(new DelayedParticipantMuteChange(participantId, MuteAction.UNMUTE));
	}

	private void applyMuteChange(DelayedParticipantMuteChange dpmc) {
		if (null == dpmc) {
			log.debug("Got a null from participantsMuteChange");
			return;
		}

		log.debug("Timeout passed actual change the mute status for " + dpmc.getParticipantId() + " to mute state " + dpmc.getMuteAction());
		participants.compute(dpmc.getParticipantId(),
							 (id, p) -> ParticipantDto
							 .builder()
							 .participantId(id)
							 .muted(dpmc.getMuteAction() == MuteAction.MUTE)
							 .build());
	}
}
