/*
 * Copyright (c) 2006-2009 by Dirk Riehle, http://dirkriehle.com
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.wahlzeit.services.mailing;

import org.wahlzeit.main.ServiceMain;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EmailServiceManager {

    protected static EmailServiceManager instance = null;
    protected static Lock instanceLock = new ReentrantLock();

    protected static EmailServiceManager testingInstance = null;
    protected static Lock testingInstanceLock = new ReentrantLock();

    protected EmailService defaultService = null;

    protected EmailServiceManager(boolean isInProduction) {
        if (isInProduction) {
            defaultService = new SmtpEmailService();
        } else {
            defaultService = new LoggingEmailService(new MockEmailService());
        }
    }

    public static EmailService getDefaultService() {
        // The lock (or synchronized) mechanism is necessary to ensure thread safety!
        instanceLock.lock();
        try {
            if (instance == null) {
                boolean isInProduction = ServiceMain.getInstance().isInProduction();
                instance = new EmailServiceManager(isInProduction);
            }
        }
        finally {
            instanceLock.unlock();
        }

        return instance.defaultService;
    }

    public static EmailService getTestingService() {
        // The lock (or synchronized) mechanism is necessary to ensure thread safety!
        //(Strictly speaking that wouldn't be necessary for the MockEmailService because it's not expensive to create)
        testingInstanceLock.lock();

        try {
            if(testingInstance == null) {
                testingInstance = new EmailServiceManager(false);
            }
        }
        finally {
            testingInstanceLock.unlock();
        }

        return testingInstance.defaultService;
    }
}
