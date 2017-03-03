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
package io.interface21.shop2gether;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A VerificationVO encapsulates all data necessary for performing an verification with
 * the backend.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Getter
@Setter
public class VerificationVO implements Serializable {

    private String code, phonenumber;

    /**
     * For Jackson.
     */
    VerificationVO() {
    }

    private VerificationVO(String code, String phonenumber) {
        this.code = code;
        this.phonenumber = phonenumber;
    }

    /**
     * Factory method to create one.
     *
     * @param code        The verification code
     * @param phonenumber The corresponding phonenumber
     * @return An instance
     */
    public static VerificationVO of(String code, String phonenumber) {
        return new VerificationVO(code, phonenumber);
    }

    /**
     * Compare the given {@code verificationCode} with the own one.
     *
     * @param verificationCode Code to compare with
     * @return {@code true} if matches, otherwise {@code false}
     */
    public boolean codeEquals(String verificationCode) {
        return verificationCode == null && this.code == null || verificationCode != null && verificationCode.equals(code);
    }

    /**
     * Checks if the given {@code verificationCodeSent} is older than 1 hour or
     * {@literal null}.
     *
     * @param verificationCodeSent The date to compare with
     * @return {@code true} if expired, otherwise {@code false}
     */
    public boolean hasExpired(Date verificationCodeSent) {
        return verificationCodeSent == null || TimeUnit.MILLISECONDS.toHours(new Date()
                .getTime() - verificationCodeSent.getTime()) > 0;
    }
}
