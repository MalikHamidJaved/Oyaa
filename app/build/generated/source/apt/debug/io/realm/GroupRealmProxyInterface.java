package io.realm;


public interface GroupRealmProxyInterface {
    public String realmGet$id();
    public void realmSet$id(String value);
    public String realmGet$name();
    public void realmSet$name(String value);
    public String realmGet$status();
    public void realmSet$status(String value);
    public String realmGet$image();
    public void realmSet$image(String value);
    public RealmList<com.verbosetech.yoohoo.models.MyString> realmGet$userIds();
    public void realmSet$userIds(RealmList<com.verbosetech.yoohoo.models.MyString> value);
}
