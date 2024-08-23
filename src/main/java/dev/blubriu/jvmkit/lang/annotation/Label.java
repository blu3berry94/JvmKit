package dev.blubriu.jvmkit.lang.annotation;

import dev.blubriu.jvmkit.lang.enumeration.ComparisonOption;
import dev.blubriu.jvmkit.utils.ObjectUtil;

import java.lang.annotation.*;

/**
 * This annotation is used to label the target.
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Label {
    /**
     * This label is used during the deep comparison process.<br>
     * For classes, it informs that the target class has field restriction.<br>
     * For fields, it indicates which fields are restricted.
     * @see ObjectUtil#deepCompare(Object, Object, ComparisonOption...)
     */
    String COMPARISON_LABEL = "ObjectUtil#deepCompare";

    /**
     * Returns all labels.
     * @return array of labels
     */
    String[] value() default "";
}
