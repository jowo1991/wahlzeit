package org.wahlzeit.model;

import com.google.api.client.util.ArrayMap;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.images.Image;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Parent;
import org.wahlzeit.model.enums.PhotoSize;
import org.wahlzeit.model.enums.PhotoStatus;
import org.wahlzeit.model.persistence.DataObject;
import org.wahlzeit.model.enums.Language;
import org.wahlzeit.services.ObjectManager;
import org.wahlzeit.services.UserManager;
import org.wahlzeit.services.config.ModelConfig;

import java.util.Map;

@Entity
public class LandscapePhoto extends DataObject implements Photo {
    /**
     * Each photo can be viewed in different sizes (XS, S, M, L, XL)
     * Images are pre-computed in these sizes to optimize bandwidth when requested.
     */
    @Ignore
    transient protected Map<PhotoSize, Image> images = new ArrayMap<>();
    protected PhotoId id = null;
    protected String ownerId;
    protected boolean ownerNotifyAboutPraise = false;
    protected EmailAddress ownerEmailAddress = EmailAddress.EMPTY;
    protected Language ownerLanguage = Language.ENGLISH;
    protected int width;
    protected int height;
    protected PhotoSize maxPhotoSize = PhotoSize.MEDIUM; // derived
    protected Tags tags = Tags.EMPTY_TAGS;
    protected PhotoStatus status = PhotoStatus.VISIBLE;
    protected int praiseSum = 10;
    protected int noVotes = 1;
    protected int noVotesAtLastNotification = 1;
    protected long creationTime = System.currentTimeMillis();
    protected Location location;
    /**
     * The default type is jpg
     */
    protected String ending = "jpg";
    @Parent
    Key parent = ObjectManager.applicationRootKey;
    //TODO: change it to a single long
    @Id
    private Long idLong;

    public LandscapePhoto() {
        id = PhotoId.getNextId();
        incWriteCount();
    }

    public LandscapePhoto(PhotoId myId) {
        id = myId;
        incWriteCount();
    }

    @Override
    public Image getImage(PhotoSize photoSize) {
        return images.get(photoSize);
    }

    @Override
    public void setImage(PhotoSize photoSize, Image image) {
        this.images.put(photoSize, image);
    }

    @Override
    public String getIdAsString() {
        return id.asString();
    }

    @Override
    public PhotoId getId() {
        return id;
    }

    @Override
    public String getOwnerId() {
        return ownerId;
    }

    @Override
    public void setOwnerId(String newName) {
        ownerId = newName;
        incWriteCount();
    }

    @Override
    public String getSummary(ModelConfig cfg) {
        return cfg.asPhotoSummary(ownerId);
    }

    @Override
    public String getCaption(ModelConfig cfg) {
        String ownerName = UserManager.getInstance().getUserById(ownerId).getNickName();
        return cfg.asPhotoCaption(ownerName);
    }

    @Override
    public boolean getOwnerNotifyAboutPraise() {
        return ownerNotifyAboutPraise;
    }

    @Override
    public void setOwnerNotifyAboutPraise(boolean newNotifyAboutPraise) {
        ownerNotifyAboutPraise = newNotifyAboutPraise;
        incWriteCount();
    }

    @Override
    public Language getOwnerLanguage() {
        return ownerLanguage;
    }

    @Override
    public void setOwnerLanguage(Language newLanguage) {
        ownerLanguage = newLanguage;
        incWriteCount();
    }

    @Override
    public boolean hasSameOwner(Photo photo) {
        return photo.getOwnerEmailAddress().equals(ownerEmailAddress);
    }

    @Override
    public EmailAddress getOwnerEmailAddress() {
        return ownerEmailAddress;
    }

    @Override
    public void setOwnerEmailAddress(EmailAddress newEmailAddress) {
        ownerEmailAddress = newEmailAddress;
        incWriteCount();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getThumbWidth() {
        return isWiderThanHigher() ? MAX_THUMB_PHOTO_WIDTH : (width * MAX_THUMB_PHOTO_HEIGHT / height);
    }

    @Override
    public boolean isWiderThanHigher() {
        return (height * MAX_PHOTO_WIDTH) < (width * MAX_PHOTO_HEIGHT);
    }

    @Override
    public int getThumbHeight() {
        return isWiderThanHigher() ? (height * MAX_THUMB_PHOTO_WIDTH / width) : MAX_THUMB_PHOTO_HEIGHT;
    }

    @Override
    public void setWidthAndHeight(int newWidth, int newHeight) {
        width = newWidth;
        height = newHeight;

        maxPhotoSize = PhotoSize.getFromWidthHeight(width, height);

        incWriteCount();
    }

    @Override
    public boolean hasPhotoSize(PhotoSize size) {
        return maxPhotoSize.asInt() >= size.asInt();
    }

    @Override
    public PhotoSize getMaxPhotoSize() {
        return maxPhotoSize;
    }

    @Override
    public String getPraiseAsString(ModelConfig cfg) {
        return cfg.asPraiseString(getPraise());
    }

    @Override
    public double getPraise() {
        return (double) praiseSum / noVotes;
    }

    @Override
    public void addToPraise(int value) {
        praiseSum += value;
        noVotes += 1;
        incWriteCount();
    }

    @Override
    public boolean isVisible() {
        return status.isDisplayable();
    }

    @Override
    public PhotoStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(PhotoStatus newStatus) {
        status = newStatus;
        incWriteCount();
    }

    @Override
    public boolean hasTag(String tag) {
        return tags.hasTag(tag);
    }

    @Override
    public Tags getTags() {
        return tags;
    }

    @Override
    public void setTags(Tags newTags) {
        tags = newTags;
        incWriteCount();
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public String getEnding() {
        return ending;
    }

    @Override
    public void setEnding(String ending) {
        this.ending = ending;
    }

    @Override
    public boolean hasNewPraise() {
        return noVotes > noVotesAtLastNotification;
    }

    @Override
    public void setNoNewPraise() {
        noVotesAtLastNotification = noVotes;
        incWriteCount();
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }
}
