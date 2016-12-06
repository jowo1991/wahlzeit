package org.wahlzeit.model.coordinate;

/**
 * Thrown to indicate that the Coordinate conversion is not supported.
 */
public class CoordinateConversionNotSupportedException extends Exception {
    private static final long serialVersionUID = -7392792784934419040L;

    public CoordinateConversionNotSupportedException() { }

    public CoordinateConversionNotSupportedException(String message) {
        super(message);
    }

    public CoordinateConversionNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoordinateConversionNotSupportedException(Throwable cause) {
        super(cause);
    }
}
