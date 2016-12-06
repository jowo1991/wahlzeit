package org.wahlzeit.model.persistence;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Wrapper class to store {@link Image}s in the Google Datastore with Objectify.
 *
 * @review
 */
@Entity
public class ImageWrapper {

    // see https://cloud.google.com/datastore/docs/tools/administration
    public final int maxEntitySize = 1024 * 1024; // = 1 MB

    @Id
    private String id;

    private byte[] imageData;

    public ImageWrapper() {
        // just for Objectify to load it from Datastore
    }

    public ImageWrapper(String id) {
        this.id = id;
    }

    /**
     * @methodtype get
     */
    public Image getImage() {
        return ImagesServiceFactory.makeImage(imageData);
    }

    /**
     * @methodtype set <p> Can not handle images >= 1 MB because this is the upper limit of entities in Google
     * Datastore. </p>
     */
    public void setImage(Image image) throws EntitySizeLimitExceededException {
        if (image.getImageData().length >= maxEntitySize) {
            throw new EntitySizeLimitExceededException("Can not store images >= 1 MB in the Google Datastore.", image.getImageData().length, maxEntitySize);
        } else {
            imageData = image.getImageData();
        }
    }
}
