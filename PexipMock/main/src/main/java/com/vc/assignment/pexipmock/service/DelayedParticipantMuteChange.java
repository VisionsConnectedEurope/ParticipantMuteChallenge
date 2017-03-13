package com.vc.assignment.pexipmock.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

import lombok.Getter;

public class DelayedParticipantMuteChange implements Delayed {

	private static final long EXPIRE_DURATION_IN_SCONDS = 5L;

	private final LocalDateTime expirationTime = LocalDateTime.now().plusSeconds(EXPIRE_DURATION_IN_SCONDS);
	@Getter private final UUID participantId;
	@Getter private final MuteAction muteAction;

	public DelayedParticipantMuteChange(UUID participantId, MuteAction muteAction) {
		this.participantId = participantId;
		this.muteAction = muteAction;
	}

	@Override
	public int compareTo(Delayed other) {
		return (int)(this.getDelay(TimeUnit.MILLISECONDS) - other.getDelay(TimeUnit.MILLISECONDS));
	}

	@Override
	public long getDelay(TimeUnit unit) {
		Duration between = Duration.between(LocalDateTime.now(), expirationTime);
		return unit.convert(between.toMillis(), TimeUnit.MILLISECONDS);
	}

	public enum MuteAction {
		MUTE,
		UNMUTE
	}
}
