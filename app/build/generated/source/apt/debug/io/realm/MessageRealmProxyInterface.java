package io.realm;


public interface MessageRealmProxyInterface {
    public String realmGet$body();
    public void realmSet$body(String value);
    public String realmGet$senderName();
    public void realmSet$senderName(String value);
    public String realmGet$senderId();
    public void realmSet$senderId(String value);
    public String realmGet$recipientId();
    public void realmSet$recipientId(String value);
    public String realmGet$id();
    public void realmSet$id(String value);
    public long realmGet$date();
    public void realmSet$date(long value);
    public long realmGet$duration();
    public void realmSet$duration(long value);
    public boolean realmGet$delivered();
    public void realmSet$delivered(boolean value);
    public boolean realmGet$sent();
    public void realmSet$sent(boolean value);
    public int realmGet$attachmentType();
    public void realmSet$attachmentType(int value);
    public com.verbosetech.yoohoo.models.Attachment realmGet$attachment();
    public void realmSet$attachment(com.verbosetech.yoohoo.models.Attachment value);
}
