package org.wahlzeit.utils;

public @interface PatternInstance {
    String name();
    String[] participants() default "";
}
