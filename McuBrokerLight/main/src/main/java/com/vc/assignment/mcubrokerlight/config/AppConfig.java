package com.vc.assignment.mcubrokerlight.config;

import com.vc.assignment.mcubrokerlight.controller.ParticipantsApiController;
import com.vc.assignment.mcubrokerlight.pexip.PexipParticipantCache;
import com.vc.assignment.mcubrokerlight.service.ParticipantService;
import com.vc.assignment.pexipmock.v1.client.ParticipantApi;
import com.vc.assignment.pexipmock.v1.invoker.ApiClient;

import java.time.Clock;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
@EnableAsync

@ComponentScan(basePackageClasses = {ParticipantsApiController.class, WebMvcConfig.class, ParticipantService.class, PexipParticipantCache.class})
public class AppConfig implements SchedulingConfigurer {

	private final AtomicInteger threadNumber = new AtomicInteger(0);

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskExecutor());
	}

	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		ThreadFactory t = (Runnable r) -> {
			Thread thread = Executors.defaultThreadFactory().newThread(r);
			thread.setDaemon(true);
			thread.setName("Generated task thread #" + threadNumber.incrementAndGet());
			return thread;
		};

		return Executors.newScheduledThreadPool(4, t);
	}

	@Bean
	public Clock clock() {
		return Clock.systemUTC();
	}

	@Bean
	public ParticipantApi participantApi() {
		ParticipantApi participantApi = new ParticipantApi();
		ApiClient apiClient = participantApi.getApiClient();
		apiClient.setBasePath("http://localhost:8080/pexipmock/api/v1");
		return participantApi;
	}
}
