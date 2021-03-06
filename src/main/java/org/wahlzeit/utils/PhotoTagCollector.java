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

package org.wahlzeit.utils;

import org.wahlzeit.model.Photo;
import org.wahlzeit.model.Tags;
import org.wahlzeit.utils.StringUtil;

import java.util.Set;

/**
 * A PhotoTagCollector provides a method to collect all tags for a given photo.
 */
public class PhotoTagCollector {

	/**
	 *
	 */
	public void collect(Set<String> tags, Photo photo) {
		String ownerName = photo.getOwnerId();
		if (!StringUtil.isNullOrEmptyString(ownerName)) {
			String ownerNameAsTag = Tags.asTag(ownerName);
			tags.add("un:" + ownerNameAsTag);
			tags.add("tg:" + ownerNameAsTag);
		}

		String[] photoTags = photo.getTags().asArray();
		for (int i = 0; i < photoTags.length; i++) {
			tags.add("tg:" + photoTags[i]);
		}
	}

}
