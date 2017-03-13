package com.vc.assignment.mcubrokerlight.controller;

import com.vc.assignment.mcubrokerlight.controller.exception.ApiExceptionService;
import com.vc.assignment.mcubrokerlight.pexip.PexipParticipant;
import com.vc.assignment.mcubrokerlight.service.ParticipantService;
import com.vc.assignment.mcubrokerlight.v1.model.Participant;
import com.vc.assignment.mcubrokerlight.v1.model.ParticipantList;
import com.vc.assignment.mcubrokerlight.v1.server.ParticipantsApi;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@Log4j
@RestController
@RequestMapping(value = "/api/v1")
public class ParticipantsApiController implements ParticipantsApi, ApiExceptionService {

	private final ParticipantService participantService;

	@Autowired
	public ParticipantsApiController(ParticipantService participantService) {
		this.participantService = participantService;
	}

	@Override
	public void logError(String error, Exception e) {
		log.error(error, e);
	}

	@Override
	public ResponseEntity<Void> addParticipant(@PathVariable("participantId") String participantId) {
		return handleApiException(() -> {
			UUID participantUId = UUID.fromString(participantId);

			if (null != participantService.getParticipant(participantUId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

			participantService.addParticipant(participantUId);
			return ResponseEntity.status(HttpStatus.OK).build();
		});
	}

	@RequestMapping(value = "/participants/{participantId}/unmute", produces = {"application/json"}, method = RequestMethod.POST)
	@Override
	public ResponseEntity<Void> unmuteParticipant(@PathVariable("participantId") String participantId) {
		return handleApiException(() -> {
			participantService.unmuteParticipant(UUID.fromString(participantId));
			return ResponseEntity.status(HttpStatus.OK).build();
		});
	}

	@RequestMapping(value = "/participants/{participantId}/mute", produces = {"application/json"}, method = RequestMethod.POST)
	@Override
	public ResponseEntity<Void> muteParticipant(@PathVariable("participantId") String participantId) {
		return handleApiException(() -> {
			participantService.muteParticipant(UUID.fromString(participantId));
			return ResponseEntity.status(HttpStatus.OK).build();
		});
	}

	@RequestMapping(value = "/participants", produces = {"application/json"}, method = RequestMethod.GET)
	@Override
	public ResponseEntity<ParticipantList> getParticipants() {
		return handleApiException(() -> {
			List<PexipParticipant> participants = participantService.getParticipants();
			ParticipantList participantList = new ParticipantList();

			participants.forEach(p -> {
				participantList.addParticipantsItem(new Participant().participantId(p.getParticipantId().toString()).muted(p.isMuted()));
			});

			return ResponseEntity.status(HttpStatus.OK).body(participantList);
		});
	}

	@RequestMapping(value = "/participants/{participantId}", produces = {"application/json"}, method = RequestMethod.GET)
	@Override
	public ResponseEntity<Participant> getParticipant(@PathVariable("participantId") String participantId) {
		return handleApiException(() -> {
			PexipParticipant p = participantService.getParticipant(UUID.fromString(participantId));

			if (null == p) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
			}

			return ResponseEntity.status(HttpStatus.OK).body(new Participant().participantId(p.getParticipantId().toString()).muted(p.isMuted()));
		});
	}
}
