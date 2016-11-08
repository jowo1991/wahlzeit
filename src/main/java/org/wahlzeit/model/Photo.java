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

package org.wahlzeit.model;

import com.google.appengine.api.images.Image;
import com.googlecode.objectify.annotation.Entity;
import org.wahlzeit.model.enums.PhotoSize;
import org.wahlzeit.model.enums.PhotoStatus;
import org.wahlzeit.model.persistence.Persistent;
import org.wahlzeit.services.config.ModelConfig;
import org.wahlzeit.model.enums.Language;

/**
 * A photo represents a user-provided (uploaded) photo.
 */
public interface Photo extends Persistent {
    String ID = "id";
    String IMAGE = "image";
    String THUMB = "thumb";
    String LINK = "link";
    String PRAISE = "praise";
    String NO_VOTES = "noVotes";
    String CAPTION = "caption";
    String DESCRIPTION = "description";
    String KEYWORDS = "keywords";

    String TAGS = "tags";
    String OWNER_ID = "ownerId";

    String STATUS = "status";
    String IS_INVISIBLE = "isInvisible";
    String UPLOADED_ON = "uploadedOn";

    int MAX_PHOTO_WIDTH = 420;
    int MAX_PHOTO_HEIGHT = 600;
    int MAX_THUMB_PHOTO_WIDTH = 105;
    int MAX_THUMB_PHOTO_HEIGHT = 150;

    Image getImage(PhotoSize photoSize);

    void setImage(PhotoSize photoSize, Image image);

    String getIdAsString();

    PhotoId getId();

    String getOwnerId();

    void setOwnerId(String newName);

    String getSummary(ModelConfig cfg);

    String getCaption(ModelConfig cfg);

    boolean getOwnerNotifyAboutPraise();

    void setOwnerNotifyAboutPraise(boolean newNotifyAboutPraise);

    Language getOwnerLanguage();

    void setOwnerLanguage(Language newLanguage);

    boolean hasSameOwner(Photo photo);

    EmailAddress getOwnerEmailAddress();

    void setOwnerEmailAddress(EmailAddress newEmailAddress);

    int getWidth();

    int getHeight();

    int getThumbWidth();

    boolean isWiderThanHigher();

    int getThumbHeight();

    void setWidthAndHeight(int newWidth, int newHeight);

    /**
     * Can this photo satisfy provided photo size?
     */
    boolean hasPhotoSize(PhotoSize size);

    PhotoSize getMaxPhotoSize();

    String getPraiseAsString(ModelConfig cfg);

    double getPraise();

    void addToPraise(int value);

    boolean isVisible();

    PhotoStatus getStatus();

    void setStatus(PhotoStatus newStatus);

    boolean hasTag(String tag);

    Tags getTags();

    void setTags(Tags newTags);

    long getCreationTime();

    String getEnding();

    void setEnding(String ending);

    boolean hasNewPraise();

    void setNoNewPraise();

    Location getLocation();

    void setLocation(Location location);
}
