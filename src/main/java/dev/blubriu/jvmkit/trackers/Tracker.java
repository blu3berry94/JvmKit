package dev.blubriu.jvmkit.trackers;

import dev.blubriu.jvmkit.lang.annotation.Beta;
import org.jetbrains.annotations.NotNull;
import dev.blubriu.jvmkit.trackers.reports.Report;

/**
 * This class represents a tracker implementation.
 * @param <R> the type of the report
 */
@Beta
public interface Tracker<R extends Report> {
    /**
     * Starts to track the target action.
     * @param report an empty/unused report
     * @param endCallback the callback which will be called at the end of the tracking
     */
    void start(@NotNull R report, @NotNull Runnable endCallback);
}
