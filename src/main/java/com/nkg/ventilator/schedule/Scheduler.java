package com.nkg.ventilator.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nkg.ventilator.service.EshopOrderService;

@Component
public class Scheduler {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private EshopOrderService eshopOrderService;

	@Scheduled(cron = "${cron.sync-eshop-orders}")
	public void syncEShopOrders() {
		logger.info("syncEShopOrders() Running...");

		try {
			int count = eshopOrderService.syncXYZEShop();
			logger.info("syncEShopOrders() update count: " + count);
		} catch (Exception e) {
			logger.error("syncEShopOrders()", e);
		}
	}
}
