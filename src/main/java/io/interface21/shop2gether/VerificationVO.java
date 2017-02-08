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
 * A VerificationVO.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Getter
@Setter
public class VerificationVO implements Serializable{

    private String code, phonenumber;

    public VerificationVO() {
    }

    public VerificationVO(String code, String phonenumber) {
        this.code = code;
        this.phonenumber = phonenumber;
    }

    public boolean codeEquals(String verificationCode) {
        return verificationCode == null && this.code == null || verificationCode != null && verificationCode.equals(code);
    }

    public boolean hasExpired(Date verificationCodeSent) {
        return verificationCodeSent == null || TimeUnit.HOURS.toHours(new Date().getTime() - verificationCodeSent.getTime()) > 1;
    }
}
