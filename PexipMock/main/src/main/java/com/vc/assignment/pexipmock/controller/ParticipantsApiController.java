package com.vc.assignment.pexipmock.controller;

import com.vc.assignment.pexipmock.controller.exception.ApiExceptionService;
import com.vc.assignment.pexipmock.dto.ParticipantDto;
import com.vc.assignment.pexipmock.service.ParticipantService;
import com.vc.assignment.pexipmock.v1.model.Participant;
import com.vc.assignment.pexipmock.v1.model.ParticipantList;
import com.vc.assignment.pexipmock.v1.server.ParticipantsApi;

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

	@RequestMapping(value = "/participants/{participantId}/unmute", produces = {"application/json"}, method = RequestMethod.POST)
	@Override
	public ResponseEntity<Void> unmuteParticipant(@PathVariable("participantId") String participantId) {
		participantService.unmuteParticipant(UUID.fromString(participantId));
		return ParticipantsApi.super.unmuteParticipant(participantId);
	}

	@RequestMapping(value = "/participants/{participantId}/mute", produces = {"application/json"}, method = RequestMethod.POST)
	@Override
	public ResponseEntity<Void> muteParticipant(@PathVariable("participantId") String participantId) {
		participantService.muteParticipant(UUID.fromString(participantId));
		return ParticipantsApi.super.muteParticipant(participantId);
	}

	@RequestMapping(value = "/participants", produces = {"application/json"}, method = RequestMethod.GET)
	@Override
	public ResponseEntity<ParticipantList> getParticipants() {
		return handleApiException(() -> {
			ParticipantList participantList = new ParticipantList();
			Map<UUID, ParticipantDto> retrieveParticipants = participantService.retrieveParticipants();

			retrieveParticipants.forEach((k, v) -> {
				participantList.addParticipantsItem(new Participant().participantId(k.toString()).muted(v.isMuted()));
			});

			return ResponseEntity.status(HttpStatus.OK).body(participantList);
		});
	}

	@RequestMapping(value = "/participants/{participantId}", produces = {"application/json"}, method = RequestMethod.GET)
	@Override
	public ResponseEntity<Participant> getParticipant(@PathVariable("participantId") String participantId) {
		return handleApiException(() -> {
			ParticipantDto participantDto = participantService.retrieveParticipant(UUID.fromString(participantId));
			return ResponseEntity.status(HttpStatus.OK).body(new Participant().participantId(participantDto.getParticipantId().toString()).muted(participantDto.isMuted()));
		});
	}
}
