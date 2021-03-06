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

package org.wahlzeit.model.users;

import com.googlecode.objectify.annotation.Subclass;
import org.wahlzeit.model.enums.AccessRights;
import org.wahlzeit.model.EmailAddress;


/**
 * An Administrator is a moderator with administration privileges.
 */
@Subclass(index = true)
public class Administrator extends Moderator {

	/**
	 *
	 */
	public Administrator(String userId, String nickName, String myEmailAddress, Client previousClient) {
		this(userId, nickName, EmailAddress.getFromString(myEmailAddress), previousClient);
	}

	/**
	 *
	 */
	public Administrator(String userId, String nickName, EmailAddress emailAddress, Client previousClient) {
		initialize(userId, nickName, emailAddress, AccessRights.ADMINISTRATOR, previousClient);
	}

	/**
	 *
	 */
	protected Administrator() {
		// do nothing
	}

}
