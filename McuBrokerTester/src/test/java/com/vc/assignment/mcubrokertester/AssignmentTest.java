package com.vc.assignment.mcubrokertester;

import com.vc.assignment.mcubrokerlight.v1.client.ParticipantApi;
import com.vc.assignment.mcubrokerlight.v1.invoker.ApiClient;
import com.vc.assignment.mcubrokerlight.v1.invoker.ApiException;

import java.util.*;
import java.util.stream.*;

import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class AssignmentTest {

	private static ParticipantApi participantApi;

	@BeforeClass
	public static void before() {
		participantApi = new ParticipantApi();
		ApiClient apiClient = participantApi.getApiClient();
		apiClient.setBasePath("http://localhost:8080/mcubrokerlight/api/v1");
	}

	@Test
	public void test() throws ApiException, InterruptedException {
		log.info("Test start");

		String participantId = UUID.randomUUID().toString();
		participantApi.addParticipant(participantId);

		assertNotNull("Participant should not be null", participantApi.getParticipant(participantId));
		participantApi.muteParticipant(participantId);

		IntStream.range(0, 10)
				.forEach(i -> {
					try {
						Assert.assertTrue("Participant mute status is incorrect it should have been true.", participantApi.getParticipant(participantId).getMuted());
						Thread.sleep(800L);
					} catch (ApiException | InterruptedException ex) {
						log.error(ex, ex);
					}
				});

		participantApi.unmuteParticipant(participantId);

		IntStream.range(0, 10)
				.forEach(i -> {
					try {
						Assert.assertFalse("Participant mute status is incorrect it should have been false.", participantApi.getParticipant(participantId).getMuted());
						Thread.sleep(800L);
					} catch (ApiException | InterruptedException ex) {
						log.error(ex, ex);
					}
				});
	}
}
