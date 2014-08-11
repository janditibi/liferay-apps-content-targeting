/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.contenttargeting.internal.osgi;

import com.liferay.osgi.util.service.ServiceTrackerUtil;
import com.liferay.portal.contenttargeting.service.CampaignLocalService;
import com.liferay.portal.contenttargeting.service.CampaignService;
import com.liferay.portal.contenttargeting.service.ReportInstanceLocalService;
import com.liferay.portal.contenttargeting.service.ReportInstanceService;
import com.liferay.portal.contenttargeting.service.RuleInstanceLocalService;
import com.liferay.portal.contenttargeting.service.RuleInstanceService;
import com.liferay.portal.contenttargeting.service.TrackingActionInstanceLocalService;
import com.liferay.portal.contenttargeting.service.TrackingActionInstanceService;
import com.liferay.portal.contenttargeting.service.UserSegmentLocalService;
import com.liferay.portal.contenttargeting.service.UserSegmentService;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.messaging.SerialDestination;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import org.springframework.context.ApplicationContext;

/**
 * @author Carlos Sierra Andrés
 */
public class ContentTargetingActivator
	implements BundleActivator, MessageListener {

	@Override
	public synchronized void receive(Message message)
		throws MessageListenerException {

		ApplicationContext applicationContext =
			(ApplicationContext)message.getPayload();

		CampaignLocalService campaignLocalService =
			(CampaignLocalService)applicationContext.getBean(
				CampaignLocalService.class.getName());

		_campaignLocalServiceServiceRegistration =
			_bundleContext.registerService(
				CampaignLocalService.class, campaignLocalService, null);

		CampaignService campaignService =
			(CampaignService)applicationContext.getBean(
				CampaignService.class.getName());

		_campaignServiceServiceRegistration =
			_bundleContext.registerService(
				CampaignService.class, campaignService, null);

		ReportInstanceLocalService reportInstanceLocalService =
			(ReportInstanceLocalService)applicationContext.getBean(
				ReportInstanceLocalService.class.getName());

		_reportInstanceLocalServiceServiceRegistration =
			_bundleContext.registerService(
				ReportInstanceLocalService.class, reportInstanceLocalService,
				null);

		ReportInstanceService reportInstanceService =
			(ReportInstanceService)applicationContext.getBean(
				ReportInstanceService.class.getName());

		_reportInstanceServiceServiceRegistration =
			_bundleContext.registerService(
				ReportInstanceService.class, reportInstanceService, null);

		RuleInstanceLocalService ruleInstanceLocalService =
			(RuleInstanceLocalService)applicationContext.getBean(
				RuleInstanceLocalService.class.getName());

		_ruleInstanceLocalServiceServiceRegistration =
			_bundleContext.registerService(
				RuleInstanceLocalService.class, ruleInstanceLocalService, null);

		RuleInstanceService ruleInstanceService =
			(RuleInstanceService)applicationContext.getBean(
				RuleInstanceService.class.getName());

		_ruleInstanceServiceServiceRegistration =
			_bundleContext.registerService(
				RuleInstanceService.class, ruleInstanceService, null);

		TrackingActionInstanceLocalService trackingActionInstanceLocalService =
			(TrackingActionInstanceLocalService)applicationContext.getBean(
				TrackingActionInstanceLocalService.class.getName());

		_trackingActionInstanceLocalServiceServiceRegistration =
			_bundleContext.registerService(
				TrackingActionInstanceLocalService.class,
				trackingActionInstanceLocalService, null);

		TrackingActionInstanceService trackingActionInstanceService =
			(TrackingActionInstanceService)applicationContext.getBean(
				TrackingActionInstanceService.class.getName());

		_trackingActionInstanceServiceServiceRegistration =
			_bundleContext.registerService(
				TrackingActionInstanceService.class,
				trackingActionInstanceService, null);

		UserSegmentLocalService userSegmentLocalService =
			(UserSegmentLocalService)applicationContext.getBean(
				UserSegmentLocalService.class.getName());

		_userSegmentLocalServiceServiceRegistration =
			_bundleContext.registerService(
				UserSegmentLocalService.class, userSegmentLocalService, null);

		UserSegmentService userSegmentService =
			(UserSegmentService)applicationContext.getBean(
				UserSegmentService.class.getName());

		_userSegmentServiceServiceRegistration = _bundleContext.registerService(
			UserSegmentService.class, userSegmentService, null);
	}

	@Override
	public synchronized void start(BundleContext bundleContext)
		throws Exception {

		_bundleContext = bundleContext;

		SerialDestination destination = new SerialDestination(DESTINATION_NAME);

		MessageBus messageBus = ServiceTrackerUtil.getService(
			MessageBus.class, bundleContext);

		messageBus.addDestination(destination);

		messageBus.registerMessageListener(DESTINATION_NAME, this);
	}

	@Override
	public synchronized void stop(BundleContext context) throws Exception {
		MessageBusUtil.removeDestination(DESTINATION_NAME);

		MessageBusUtil.unregisterMessageListener(DESTINATION_NAME, this);

		if (_campaignLocalServiceServiceRegistration != null) {
			_campaignLocalServiceServiceRegistration.unregister();

			_campaignLocalServiceServiceRegistration = null;
		}

		if (_campaignServiceServiceRegistration != null) {
			_campaignServiceServiceRegistration.unregister();

			_campaignServiceServiceRegistration = null;
		}

		if (_reportInstanceLocalServiceServiceRegistration != null) {
			_reportInstanceLocalServiceServiceRegistration.unregister();

			_reportInstanceLocalServiceServiceRegistration = null;
		}

		if (_reportInstanceServiceServiceRegistration != null) {
			_reportInstanceServiceServiceRegistration.unregister();

			_reportInstanceServiceServiceRegistration = null;
		}

		if (_ruleInstanceLocalServiceServiceRegistration != null) {
			_ruleInstanceLocalServiceServiceRegistration.unregister();

			_ruleInstanceLocalServiceServiceRegistration = null;
		}

		if (_ruleInstanceServiceServiceRegistration != null) {
			_ruleInstanceServiceServiceRegistration.unregister();

			_ruleInstanceServiceServiceRegistration = null;
		}

		if (_trackingActionInstanceLocalServiceServiceRegistration != null) {
			_trackingActionInstanceLocalServiceServiceRegistration.unregister();

			_trackingActionInstanceLocalServiceServiceRegistration = null;
		}

		if (_trackingActionInstanceServiceServiceRegistration != null) {
			_trackingActionInstanceServiceServiceRegistration.unregister();

			_trackingActionInstanceServiceServiceRegistration = null;
		}

		if (_userSegmentLocalServiceServiceRegistration != null) {
			_userSegmentLocalServiceServiceRegistration.unregister();

			_userSegmentLocalServiceServiceRegistration = null;
		}

		if (_userSegmentServiceServiceRegistration != null) {
			_userSegmentServiceServiceRegistration.unregister();

			_userSegmentServiceServiceRegistration = null;
		}
	}

	protected static final String DESTINATION_NAME =
		"content-targeting-api-spring";

	private BundleContext _bundleContext;
	private ServiceRegistration<CampaignLocalService>
		_campaignLocalServiceServiceRegistration;
	private ServiceRegistration<CampaignService>
		_campaignServiceServiceRegistration;
	private ServiceRegistration<ReportInstanceLocalService>
		_reportInstanceLocalServiceServiceRegistration;
	private ServiceRegistration<ReportInstanceService>
		_reportInstanceServiceServiceRegistration;
	private ServiceRegistration<RuleInstanceLocalService>
		_ruleInstanceLocalServiceServiceRegistration;
	private ServiceRegistration<RuleInstanceService>
		_ruleInstanceServiceServiceRegistration;
	private ServiceRegistration<TrackingActionInstanceLocalService>
		_trackingActionInstanceLocalServiceServiceRegistration;
	private ServiceRegistration<TrackingActionInstanceService>
		_trackingActionInstanceServiceServiceRegistration;
	private ServiceRegistration<UserSegmentLocalService>
		_userSegmentLocalServiceServiceRegistration;
	private ServiceRegistration<UserSegmentService>
		_userSegmentServiceServiceRegistration;

}