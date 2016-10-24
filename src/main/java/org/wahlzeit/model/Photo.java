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

import com.google.api.client.util.ArrayMap;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.images.Image;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Parent;
import org.wahlzeit.services.DataObject;
import org.wahlzeit.services.EmailAddress;
import org.wahlzeit.services.Language;
import org.wahlzeit.services.ObjectManager;

import java.util.Map;

/**
 * A photo represents a user-provided (uploaded) photo.
 */
@Entity
public class Photo extends DataObject {

    public static final String IMAGE = "image";
    public static final String THUMB = "thumb";
    public static final String LINK = "link";
    public static final String PRAISE = "praise";
    public static final String NO_VOTES = "noVotes";
    public static final String CAPTION = "caption";
    public static final String DESCRIPTION = "description";
    public static final String KEYWORDS = "keywords";

    public static final String TAGS = "tags";
    public static final String OWNER_ID = "ownerId";

    public static final String STATUS = "status";
    public static final String IS_INVISIBLE = "isInvisible";
    public static final String UPLOADED_ON = "uploadedOn";

    public static final int MAX_PHOTO_WIDTH = 420;
    public static final int MAX_PHOTO_HEIGHT = 600;
    public static final int MAX_THUMB_PHOTO_WIDTH = 105;
    public static final int MAX_THUMB_PHOTO_HEIGHT = 150;

    /**
     * Each photo can be viewed in different sizes (XS, S, M, L, XL)
     * Images are pre-computed in these sizes to optimize bandwidth when requested.
     */
    @Ignore
    transient protected Map<PhotoSize, Image> images = new ArrayMap<>();
    @Parent
    Key parent = ObjectManager.applicationRootKey;

    private PhotoId id = null;
    private String ownerId;
    private boolean ownerNotifyAboutPraise = false;
    private EmailAddress ownerEmailAddress = EmailAddress.EMPTY;
    private Language ownerLanguage = Language.ENGLISH;
    private int width;
    private int height;
    private PhotoSize maxPhotoSize = PhotoSize.MEDIUM; // derived
    private Tags tags = Tags.EMPTY_TAGS;
    private PhotoStatus status = PhotoStatus.VISIBLE;
    private int praiseSum = 10;
    private int noVotes = 1;
    private int noVotesAtLastNotification = 1;
    private long creationTime = System.currentTimeMillis();
    private Location location;
    /**
     * The default type is jpg
     */
    private String ending = "jpg";
    //TODO: change it to a single long
    @Id
    private Long idLong;

    public Photo() {
        id = PhotoId.getNextId();
        incWriteCount();
    }

    public Photo(PhotoId myId) {
        id = myId;

        incWriteCount();
    }

    public Image getImage(PhotoSize photoSize) {
        return images.get(photoSize);
    }

    public void setImage(PhotoSize photoSize, Image image) {
        this.images.put(photoSize, image);
    }

    public String getIdAsString() {
        return id.asString();
    }

    public PhotoId getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String newName) {
        ownerId = newName;
        incWriteCount();
    }

    public String getSummary(ModelConfig cfg) {
        return cfg.asPhotoSummary(ownerId);
    }

    public String getCaption(ModelConfig cfg) {
        String ownerName = UserManager.getInstance().getUserById(ownerId).getNickName();
        return cfg.asPhotoCaption(ownerName);
    }

    public boolean getOwnerNotifyAboutPraise() {
        return ownerNotifyAboutPraise;
    }

    public void setOwnerNotifyAboutPraise(boolean newNotifyAboutPraise) {
        ownerNotifyAboutPraise = newNotifyAboutPraise;
        incWriteCount();
    }

    public Language getOwnerLanguage() {
        return ownerLanguage;
    }

    public void setOwnerLanguage(Language newLanguage) {
        ownerLanguage = newLanguage;
        incWriteCount();
    }

    public boolean hasSameOwner(Photo photo) {
        return photo.getOwnerEmailAddress().equals(ownerEmailAddress);
    }

    public EmailAddress getOwnerEmailAddress() {
        return ownerEmailAddress;
    }

    public void setOwnerEmailAddress(EmailAddress newEmailAddress) {
        ownerEmailAddress = newEmailAddress;
        incWriteCount();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getThumbWidth() {
        return isWiderThanHigher() ? MAX_THUMB_PHOTO_WIDTH : (width * MAX_THUMB_PHOTO_HEIGHT / height);
    }

    public boolean isWiderThanHigher() {
        return (height * MAX_PHOTO_WIDTH) < (width * MAX_PHOTO_HEIGHT);
    }

    public int getThumbHeight() {
        return isWiderThanHigher() ? (height * MAX_THUMB_PHOTO_WIDTH / width) : MAX_THUMB_PHOTO_HEIGHT;
    }

    public void setWidthAndHeight(int newWidth, int newHeight) {
        width = newWidth;
        height = newHeight;

        maxPhotoSize = PhotoSize.getFromWidthHeight(width, height);

        incWriteCount();
    }

    /**
     * Can this photo satisfy provided photo size?
     */
    public boolean hasPhotoSize(PhotoSize size) {
        return maxPhotoSize.asInt() >= size.asInt();
    }

    public PhotoSize getMaxPhotoSize() {
        return maxPhotoSize;
    }

    public String getPraiseAsString(ModelConfig cfg) {
        return cfg.asPraiseString(getPraise());
    }

    public double getPraise() {
        return (double) praiseSum / noVotes;
    }

    public void addToPraise(int value) {
        praiseSum += value;
        noVotes += 1;
        incWriteCount();
    }

    public boolean isVisible() {
        return status.isDisplayable();
    }

    public PhotoStatus getStatus() {
        return status;
    }

    public void setStatus(PhotoStatus newStatus) {
        status = newStatus;
        incWriteCount();
    }

    public boolean hasTag(String tag) {
        return tags.hasTag(tag);
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags newTags) {
        tags = newTags;
        incWriteCount();
    }

    public long getCreationTime() {
        return creationTime;
    }

    public String getEnding() {
        return ending;
    }

    public void setEnding(String ending) {
        this.ending = ending;
    }

    public boolean hasNewPraise() {
        return noVotes > noVotesAtLastNotification;
    }

    public void setNoNewPraise() {
        noVotesAtLastNotification = noVotes;
        incWriteCount();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
