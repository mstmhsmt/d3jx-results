package twitter4j.internal.json;

import twitter4j.*;
import javax.annotation.Generated;
import java.util.Date;

@Generated(value = "generate-lazy-objects.sh", comments = "This is Tool Generated Code. DO NOT EDIT", date = "2011-07-13")
final class LazyStatus implements twitter4j.Status {

    private twitter4j.internal.http.HttpResponse res;

    private z_T4JInternalFactory factory;

    private Status target = null;

    LazyStatus(twitter4j.internal.http.HttpResponse res, z_T4JInternalFactory factory) {
        this.res = res;
        this.factory = factory;
    }

    private Status getTarget() {
        if (target == null) {
            try {
                target = factory.createStatus(res);
            } catch (TwitterException e) {
                throw new TwitterRuntimeException(e);
            }
        }
        return target;
    }

    public Date getCreatedAt() {
        return getTarget().getCreatedAt();
    }

    public long getId() {
        return getTarget().getId();
    }

    public String getText() {
        return getTarget().getText();
    }

    public String getSource() {
        return getTarget().getSource();
    }

    public boolean isTruncated() {
        return getTarget().isTruncated();
    }

    public long getInReplyToStatusId() {
        return getTarget().getInReplyToStatusId();
    }

    public long getInReplyToUserId() {
        return getTarget().getInReplyToUserId();
    }

    public String getInReplyToScreenName() {
        return getTarget().getInReplyToScreenName();
    }

    public GeoLocation getGeoLocation() {
        return getTarget().getGeoLocation();
    }

    public Place getPlace() {
        return getTarget().getPlace();
    }

    public boolean isFavorited() {
        return getTarget().isFavorited();
    }

    public User getUser() {
        return getTarget().getUser();
    }

    public boolean isRetweet() {
        return getTarget().isRetweet();
    }

    public Status getRetweetedStatus() {
        return getTarget().getRetweetedStatus();
    }

    public long[] getContributors() {
        return getTarget().getContributors();
    }

    public long getRetweetCount() {
        return getTarget().getRetweetCount();
    }

    public boolean isRetweetedByMe() {
        return getTarget().isRetweetedByMe();
    }

    public UserMentionEntity[] getUserMentionEntities() {
        return getTarget().getUserMentionEntities();
    }

    public URLEntity[] getURLEntities() {
        return getTarget().getURLEntities();
    }

    public HashtagEntity[] getHashtagEntities() {
        return getTarget().getHashtagEntities();
    }

    public MediaEntity[] getMediaEntities() {
        return getTarget().getMediaEntities();
    }

    public RateLimitStatus getRateLimitStatus() {
        return getTarget().getRateLimitStatus();
    }

    public int getAccessLevel() {
        return getTarget().getAccessLevel();
    }

    public int compareTo(Status target) {
        return getTarget().compareTo(target);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Status))
            return false;
        return getTarget().equals(o);
    }

    @Override
    public int hashCode() {
        return getTarget().hashCode();
    }

    @Override
    public String toString() {
        return "LazyStatus{" + "target=" + getTarget() + "}";
    }

    public Status getMyRetweet() {
        return getTarget().getMyRetweet();
    }

    @Override
    public boolean isPossiblySensitive() {
        return getTarget().isPossiblySensitive();
    }
}
