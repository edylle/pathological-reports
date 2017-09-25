package com.edylle.pathologicalreports.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.edylle.pathologicalreports.service.RecoverPasswordService;

@Component
public class DeleteTokensSchedule {

	@Autowired
	private RecoverPasswordService recoverPasswordService;

	/**
	 * This schedule method clears every token that has been persisted for more than
	 * 30 minutes
	 */
	@Scheduled(cron = "0 0/30 * * * ?")
	public void reportCurrentTime() {

		recoverPasswordService.deleteEntitiesBefore(new Date());
	}

}
