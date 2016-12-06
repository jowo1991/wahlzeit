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

package org.wahlzeit.model.persistence;

import com.google.appengine.api.images.Image;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;
import org.wahlzeit.services.LogBuilder;
import org.wahlzeit.services.OfyService;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Adapter for the Google Datastore. Use default constructor to create an instance.
 *
 * @review
 */
public class DatastoreAdapter extends ImageStorage {

    private static final Logger log = Logger.getLogger(DatastoreAdapter.class.getName());


    @Override
    protected void doWriteImage(Serializable image, String photoIdAsString, int size) throws EntitySizeLimitExceededException {
        if (image instanceof Image) {
            final ImageWrapper imageWrapper = new ImageWrapper(photoIdAsString + size);
            imageWrapper.setImage((Image) image);

            ObjectifyService.run(new Work<Boolean>() {
                @Override
                public Boolean run() {
                    OfyService.ofy().save().entity(imageWrapper).now();
                    return null;
                }
            });

            log.config(LogBuilder.createSystemMessage().addMessage("image successfully written").toString());
        } else {
            log.warning(LogBuilder.createSystemMessage().
                    addMessage("did not get an Image type to store").
                    addParameter("image type", image.toString()).toString());
        }
    }

    @Override
    protected Image doReadImage(final String photoIdAsString, final int size) {
        Image result = null;

        ImageWrapper imageWrapper = ObjectifyService.run(new Work<ImageWrapper>() {
            @Override
            public ImageWrapper run() {
                return OfyService.ofy().load().type(ImageWrapper.class).id(photoIdAsString + size).now();
            }
        });

        if (imageWrapper == null) {
            log.info(LogBuilder.createSystemMessage().addMessage("does not exist!").toString());
        } else {
            result = imageWrapper.getImage();
            if (result != null) {
                log.config(LogBuilder.createSystemMessage().addMessage("image successfully read").toString());
            } else {
                log.warning(LogBuilder.createSystemMessage().addMessage("ImageWrapper contains no Image").toString());
            }
        }
        return result;
    }

    @Override
    protected boolean doDoesImageExist(String photoIdAsString, int size) {
        Image image = null;
        boolean result = false;
        try {
            image = doReadImage(photoIdAsString, size);
        } catch (Exception e) {
            log.warning(
                    LogBuilder.createSystemMessage().addException("IOException when checking for Image existance", e)
                            .toString());
        }
        if (image != null) {
            result = true;
        }
        log.config(LogBuilder.createSystemMessage().addParameter("does image exist", result).toString());
        return result;
    }
}
