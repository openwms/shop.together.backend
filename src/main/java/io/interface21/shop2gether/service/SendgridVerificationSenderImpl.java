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
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;

/**
 * A VerificationSenderImpl.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@Repository
class SendgridVerificationSenderImpl implements VerificationSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendgridVerificationSenderImpl.class);
    private final MailSender mailSender;
    private final SimpleMailMessage templateMessage;

    public SendgridVerificationSenderImpl(MailSender mailSender, SimpleMailMessage templateMessage) {
        this.mailSender = mailSender;
        this.templateMessage = templateMessage;
    }

    @Override
    public void send(String verification, String receiptId) {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(receiptId+"@sendgrid.com");
        msg.setText(verification);
        try{
            this.mailSender.send(msg);
        }
        catch (MailException ex) {
            LOGGER.error(ex.getMessage(), e);
            throw new IntegrationLayerException(ex.getMessage());
        }
    }
}
