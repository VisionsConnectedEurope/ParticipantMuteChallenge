package com.vc.assignment.mcubrokerlight.dto;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ParticipantDto {

	private UUID participantId = UUID.randomUUID();
	private boolean muted = false;
}
