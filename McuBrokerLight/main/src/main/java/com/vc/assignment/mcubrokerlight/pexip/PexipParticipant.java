package com.vc.assignment.mcubrokerlight.pexip;

import java.util.*;

import lombok.Data;

@Data
public class PexipParticipant {

	private UUID participantId;
	private boolean muted = false;
}
