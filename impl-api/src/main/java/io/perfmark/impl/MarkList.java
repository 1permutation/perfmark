package io.perfmark.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public final class MarkList {

  public static Builder newBuilder() {
    return new Builder();
  }

  private final List<Mark> marks;
  private final long threadId;
  private final String threadName;

  MarkList(Builder builder) {
    if (builder.marks == null) {
      throw new NullPointerException("marks");
    }
    this.marks = builder.marks;
    if (builder.threadName == null) {
      throw new NullPointerException("threadName");
    }
    this.threadName = builder.threadName;
    this.threadId = builder.threadId;
  }

  public List<Mark> getMarks() {
    return marks;
  }

  public String getThreadName() {
    return threadName;
  }

  public long getThreadId() {
    return threadId;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof MarkList)) {
      return false;
    }
    MarkList that = (MarkList) obj;
    return Mark.equal(this.marks, that.marks)
        && this.threadId == that.threadId
        && Mark.equal(this.threadName, that.threadName);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(new Object[] {marks, threadId, threadName});
  }

  @Override
  public String toString() {
    return "MarkList{"
        + "marks=" + marks + ", "
        + "threadId=" + threadId + ", "
        + "threadName=" + threadName + "}";
  }

  public Builder toBuilder() {
    Builder builder = newBuilder();
    builder.marks = marks;
    return builder.setThreadName(threadName).setThreadId(threadId);
  }

  public static final class Builder {

    List<Mark> marks;
    String threadName;
    long threadId;

    public MarkList build() {
      return new MarkList(this);
    }

    /**
     * Sets the marks for this MarkList builder.
     *
     * @throws NullPointerException if any element in this list is {@code null}.
     * @param marks the marks to set.
     * @return this
     */
    public Builder setMarks(List<Mark> marks) {
      if (marks == null) {
        throw new NullPointerException("marks");
      }
      ArrayList<Mark> copy = new ArrayList<Mark>(marks.size());
      ListIterator<Mark> it = marks.listIterator();
      while (it.hasNext()) {
        Mark mark = it.next();
        if (mark == null) {
          throw new NullPointerException("mark is null at pos " + (it.nextIndex() - 1));
        }
        copy.add(mark);
      }
      this.marks = Collections.unmodifiableList(copy);
      return this;
    }

    /**
     * Sets the thread name for this MarkList builder.
     *
     * @param threadName
     * @return this
     */
    public Builder setThreadName(String threadName) {
      this.threadName = threadName;
      return this;
    }

    /**
     * Sets the thread ID for this MarkList builder.
     *
     * @param threadId
     * @return this
     */
    public Builder setThreadId(long threadId) {
      this.threadId = threadId;
      return this;
    }
  }
}