package io.realm;


public interface ChatRealmProxyInterface {
    public RealmList<com.verbosetech.yoohoo.models.Message> realmGet$messages();
    public void realmSet$messages(RealmList<com.verbosetech.yoohoo.models.Message> value);
    public String realmGet$lastMessage();
    public void realmSet$lastMessage(String value);
    public String realmGet$myId();
    public void realmSet$myId(String value);
    public String realmGet$userId();
    public void realmSet$userId(String value);
    public String realmGet$groupId();
    public void realmSet$groupId(String value);
    public long realmGet$timeUpdated();
    public void realmSet$timeUpdated(long value);
    public com.verbosetech.yoohoo.models.User realmGet$user();
    public void realmSet$user(com.verbosetech.yoohoo.models.User value);
    public com.verbosetech.yoohoo.models.Group realmGet$group();
    public void realmSet$group(com.verbosetech.yoohoo.models.Group value);
    public boolean realmGet$read();
    public void realmSet$read(boolean value);
}
