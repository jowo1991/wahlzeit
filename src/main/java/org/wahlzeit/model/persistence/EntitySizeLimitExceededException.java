package org.wahlzeit.model.persistence;

public class EntitySizeLimitExceededException extends Exception {
    private static final long serialVersionUID = 3239743487358275131L;

    public EntitySizeLimitExceededException(String message, int actual, int limit) {
        super(message + "\n\n" + "actual: " + actual + "\n" + "limit: " + limit);
    }
}
