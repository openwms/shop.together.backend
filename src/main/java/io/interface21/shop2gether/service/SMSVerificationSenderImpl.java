/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package io.interface21.shop2gether.service;

import org.ameba.exception.IntegrationLayerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

/**
 * A SMSVerificationSenderImpl.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Repository
class SMSVerificationSenderImpl implements VerificationSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(SMSVerificationSenderImpl.class);
    @Autowired
    private MailSender mailSender;
    @Value("${mail.from}")
    private String from;
    @Value("${mail.toHost}")
    private String toHost;

    public SMSVerificationSenderImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    @Override
    public void send(String verification, String receiptId) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(receiptId + "@" + toHost);
        msg.setSubject("Your Loobo verification code is");
        msg.setText(verification);
        try {
            // TODO [openwms]: 18/02/17 Activate sender here and remove code from result object
            //    this.mailSender.send(msg);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new IntegrationLayerException(ex.getMessage());
        }
    }
}
