package twitter4j.api;

import twitter4j.*;

public interface FriendsFollowersResources {

    IDs getFriendsIDs(long cursor) throws TwitterException;

    IDs getFriendsIDs(long userId, long cursor) throws TwitterException;

    IDs getFriendsIDs(String screenName, long cursor) throws TwitterException;

    IDs getFollowersIDs(long cursor) throws TwitterException;

    IDs getFollowersIDs(long userId, long cursor) throws TwitterException;

    IDs getFollowersIDs(String screenName, long cursor) throws TwitterException;

    ResponseList<Friendship> lookupFriendships(long[] ids) throws TwitterException;

    ResponseList<Friendship> lookupFriendships(String[] screenNames) throws TwitterException;

    IDs getIncomingFriendships(long cursor) throws TwitterException;

    IDs getOutgoingFriendships(long cursor) throws TwitterException;

    User createFriendship(long userId) throws TwitterException;

    User createFriendship(String screenName) throws TwitterException;

    User createFriendship(long userId, boolean follow) throws TwitterException;

    User createFriendship(String screenName, boolean follow) throws TwitterException;

    User destroyFriendship(long userId) throws TwitterException;

    User destroyFriendship(String screenName) throws TwitterException;

    Relationship updateFriendship(long userId, boolean enableDeviceNotification, boolean retweets) throws TwitterException;

    Relationship updateFriendship(String screenName, boolean enableDeviceNotification, boolean retweets) throws TwitterException;

    Relationship showFriendship(long sourceId, long targetId) throws TwitterException;

    Relationship showFriendship(String sourceScreenName, String targetScreenName) throws TwitterException;

    PagableResponseList<User> getFriendsList(long userId, long cursor) throws TwitterException;

    PagableResponseList<User> getFriendsList(String screenName, long cursor) throws TwitterException;

    PagableResponseList<User> getFollowersList(long userId, long cursor) throws TwitterException;

    PagableResponseList<User> getFollowersList(String screenName, long cursor) throws TwitterException;

    PagableResponseList<User> getFollowersList(long userId, long cursor, int count) throws TwitterException;

    PagableResponseList<User> getFollowersList(String screenName, long cursor, int count) throws TwitterException;

    PagableResponseList<User> getFollowersList(String screenName, long cursor, int count, boolean skipStatus, boolean includeUserEntities) throws TwitterException;

    PagableResponseList<User> getFriendsList(String screenName, long cursor, int count, boolean skipStatus, boolean includeUserEntities) throws TwitterException;

    PagableResponseList<User> getFollowersList(long userId, long cursor, int count, boolean skipStatus, boolean includeUserEntities) throws TwitterException;

    PagableResponseList<User> getFriendsList(long userId, long cursor, int count, boolean skipStatus, boolean includeUserEntities) throws TwitterException;

    PagableResponseList<User> getFriendsList(String screenName, long cursor, int count) throws TwitterException;

    PagableResponseList<User> getFriendsList(long userId, long cursor, int count) throws TwitterException;
}
