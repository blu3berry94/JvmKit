package dev.blubriu.jvmkit.helpers;

import dev.blubriu.jvmkit.utils.Condition;
import dev.blubriu.jvmkit.utils.MathUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * This class helps you to divide a set of data into small ones per page.<br>
 * All pages have the same slot. Each slot can hold one element.
 * @param <T> data type
 */
public class PaginationHelper<T> {
    private T[] data;
    private int slotPerPage;
    private int totalSlot;
    private int currentPage;
    private int totalPage;

    /**
     * Constructs an instance of {@code PaginationHelper}.
     * @param data an array of data
     * @param slot the number of slots per page
     */
    public PaginationHelper(@NotNull T[] data, int slot){
        Condition.argNotNull("data", data);
        Condition.check(slot > 0, "the slot number must be higher than zero");
        this.data = data;
        this.slotPerPage = slot;
        this.totalSlot = data.length;
        this.currentPage = 1;
        this.totalPage = (data.length % slot == 0) ? data.length/slot : (data.length/slot + 1);
    }

    /**
     * Opens a page with the given index.<br>
     * If the index is out of bound, the first index or the last one will be selected instead.
     * @param index the page index (starting from one)
     * @return this object whose given page is opened
     */
    @Contract("_ -> this")
    public PaginationHelper<T> open(int index){
        currentPage = MathUtil.clampInt(index, 1, totalPage);
        return this;
    }

    /**
     * Opens the next page.
     * @return this object whose next page is opened
     */
    @Contract("-> this")
    public PaginationHelper<T> next(){
        return open(currentPage+1);
    }

    /**
     * Opens the previous page.
     * @return this object whose previous page is opened
     */
    @Contract("-> this")
    public PaginationHelper<T> prev(){
        return open(currentPage-1);
    }

    /**
     * Returns the data
     * @return data
     */
    @NotNull
    public T[] getData() {
        return data;
    }

    /**
     * Returns the number of slots per page.
     * @return slot per page
     */
    public int getSlotPerPage() {
        return slotPerPage;
    }

    /**
     * Returns the total number of slots.
     * @return total number of slots
     */
    public int getTotalSlot() {
        return totalSlot;
    }

    /**
     * Returns the current page.
     * @return page index (starting from 1)
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Returns the total number of pages.<br>
     * @return total number of pages (0 if given data is empty)
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * Collects all elements in the current page.
     * @return an array of elements
     */
    @NotNull
    public T[] collect(){
        Condition.check(totalPage > 0, "Cannot handling #collect() while totalPage == 0");
        return Arrays.copyOfRange(data, (currentPage-1)*slotPerPage, Math.min(totalSlot, currentPage*slotPerPage));
    }

    /**
     * Creates a stream though all elements in the current page.
     * @return stream (maybe empty if the given data is empty)
     */
    @NotNull
    public Stream<T> stream(){
        return totalPage > 0 ? Arrays.stream(collect()) : Stream.empty();
    }

    /**
     * Executes the given operation for each element in the current page.
     * @param consumer consumer
     * @return this object
     */
    @Contract("_ -> this")
    public PaginationHelper<T> each(@Nullable Consumer<T> consumer){
        if(consumer != null && totalPage > 0) {
            for (T e : collect()) consumer.accept(e);
        }
        return this;
    }
}
