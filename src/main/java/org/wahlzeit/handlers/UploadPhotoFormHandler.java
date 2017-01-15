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

package org.wahlzeit.handlers;

import com.google.appengine.api.images.Image;
import org.wahlzeit.agents.AsyncTaskExecutor;
import org.wahlzeit.model.LandscapeType;
import org.wahlzeit.model.Location;
import org.wahlzeit.model.Photo;
import org.wahlzeit.model.Tags;
import org.wahlzeit.model.coordinate.SphericalCoordinate;
import org.wahlzeit.model.enums.AccessRights;
import org.wahlzeit.model.users.User;
import org.wahlzeit.model.users.UserSession;
import org.wahlzeit.services.LandscapeTypeManager;
import org.wahlzeit.services.LogBuilder;
import org.wahlzeit.services.PhotoManager;
import org.wahlzeit.services.config.ModelConfig;
import org.wahlzeit.utils.StringUtil;
import org.wahlzeit.webparts.WebPart;

import java.util.Map;
import java.util.logging.Logger;

/**
 * A handler class for a specific web form.
 */
public class UploadPhotoFormHandler extends AbstractWebFormHandler {
    private static LandscapeTypeManager manager = LandscapeTypeManager.getInstance();
    private static String NEWLINE = "\n";
    private static Logger log = Logger.getLogger(UploadPhotoFormHandler.class.getName());

    public UploadPhotoFormHandler() {
        initialize(PartUtil.UPLOAD_PHOTO_FORM_FILE, AccessRights.USER);
    }

    /**
     * Prepare the html template
     */
    protected void doMakeWebPart(UserSession us, WebPart part) {
        Map<String, Object> args = us.getSavedArgs();
        part.addStringFromArgs(args, UserSession.MESSAGE);

        part.maskAndAddStringFromArgs(args, Photo.TAGS);
        part.addString("typeDropdownItems", buildOptions());
    }

    private void writeOption(LandscapeType type, String prefix, StringBuilder builder) {
        builder.append(String.format("<option value=\"%s\">", type.getCanonicalId()));
        builder.append(prefix);
        builder.append(type.getName());
        builder.append("</option>");
        builder.append(NEWLINE);
    }

    private String buildOptions() {
        StringBuilder builder = new StringBuilder();
        for (LandscapeType rootType : manager.getRootTypes()) {
            printType(rootType, "", builder);
        }

        return builder.toString();
    }

    /**
     * Recursively append the given {@link LandscapeType} and its children to the given builder
     */
    private void printType(LandscapeType type, String prefix, StringBuilder builder) {
        writeOption(type, prefix, builder);

        for(LandscapeType t : type.getSubTypes()) {
            printType(t, prefix + "--", builder);
        }
    }

    /**
     *
     */
    protected String doHandlePost(UserSession us, Map args) {
        String tags = us.getAndSaveAsString(args, Photo.TAGS);

        ModelConfig config = us.getClient().getLanguageConfiguration();
        if (!StringUtil.isLegalTagsString(tags)) {
            us.setMessage(config.getInputIsInvalid());
            return PartUtil.UPLOAD_PHOTO_PAGE_NAME;
        }

        try {
            PhotoManager pm = PhotoManager.getInstance();
            String fileName = us.getAsString(args, "fileName");
            User user = (User) us.getClient();
            Image uploadedImage = user.getUploadedImage();
            Photo photo = pm.createPhoto(fileName, uploadedImage);

            user.addPhoto(photo);

            String coordinateString = us.getAsString(args, "coordinate");
            SphericalCoordinate coordinate = SphericalCoordinate.tryParse(coordinateString);
            if (coordinate != null) {
                photo.setLocation(new Location(coordinate));
            }

            photo.setType(us.getAsString(args, "type"));
            photo.setTags(new Tags(tags));

            log.config(LogBuilder.createUserMessage().
                    addAction("Upload Photo").
                    addParameter("Photo", photo.getId().asString()).
                    addParameter("tags", photo.getTags().asString()).
                    addParameter("location", String.valueOf(photo.getLocation())).toString());

            us.setTwoLineMessage(config.getPhotoUploadSucceeded(), config.getKeepGoing());
            log.config(LogBuilder.createSystemMessage().
                    addAction("Calling async task to save Photo").
                    addParameter("ID", photo.getId().asString()).toString());

            AsyncTaskExecutor.savePhotoAsync(photo.getId().asString());
        } catch (Exception ex) {
            log.warning(LogBuilder.createSystemMessage().addException("uploading photo failed", ex).toString());
            us.setMessage(config.getPhotoUploadFailed());
        }

        return PartUtil.UPLOAD_PHOTO_PAGE_NAME;
    }
}
