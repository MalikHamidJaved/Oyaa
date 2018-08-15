package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SecretChatRealmProxy extends com.verbosetech.yoohoo.models.SecretChat
    implements RealmObjectProxy, SecretChatRealmProxyInterface {

    static final class SecretChatColumnInfo extends ColumnInfo
        implements Cloneable {

        public long messagesIndex;
        public long lastMessageIndex;
        public long myIdIndex;
        public long userIdIndex;
        public long groupIdIndex;
        public long timeUpdatedIndex;
        public long userIndex;
        public long groupIndex;
        public long readIndex;

        SecretChatColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(9);
            this.messagesIndex = getValidColumnIndex(path, table, "SecretChat", "messages");
            indicesMap.put("messages", this.messagesIndex);
            this.lastMessageIndex = getValidColumnIndex(path, table, "SecretChat", "lastMessage");
            indicesMap.put("lastMessage", this.lastMessageIndex);
            this.myIdIndex = getValidColumnIndex(path, table, "SecretChat", "myId");
            indicesMap.put("myId", this.myIdIndex);
            this.userIdIndex = getValidColumnIndex(path, table, "SecretChat", "userId");
            indicesMap.put("userId", this.userIdIndex);
            this.groupIdIndex = getValidColumnIndex(path, table, "SecretChat", "groupId");
            indicesMap.put("groupId", this.groupIdIndex);
            this.timeUpdatedIndex = getValidColumnIndex(path, table, "SecretChat", "timeUpdated");
            indicesMap.put("timeUpdated", this.timeUpdatedIndex);
            this.userIndex = getValidColumnIndex(path, table, "SecretChat", "user");
            indicesMap.put("user", this.userIndex);
            this.groupIndex = getValidColumnIndex(path, table, "SecretChat", "group");
            indicesMap.put("group", this.groupIndex);
            this.readIndex = getValidColumnIndex(path, table, "SecretChat", "read");
            indicesMap.put("read", this.readIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final SecretChatColumnInfo otherInfo = (SecretChatColumnInfo) other;
            this.messagesIndex = otherInfo.messagesIndex;
            this.lastMessageIndex = otherInfo.lastMessageIndex;
            this.myIdIndex = otherInfo.myIdIndex;
            this.userIdIndex = otherInfo.userIdIndex;
            this.groupIdIndex = otherInfo.groupIdIndex;
            this.timeUpdatedIndex = otherInfo.timeUpdatedIndex;
            this.userIndex = otherInfo.userIndex;
            this.groupIndex = otherInfo.groupIndex;
            this.readIndex = otherInfo.readIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final SecretChatColumnInfo clone() {
            return (SecretChatColumnInfo) super.clone();
        }

    }
    private SecretChatColumnInfo columnInfo;
    private ProxyState<com.verbosetech.yoohoo.models.SecretChat> proxyState;
    private RealmList<com.verbosetech.yoohoo.models.Message> messagesRealmList;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("messages");
        fieldNames.add("lastMessage");
        fieldNames.add("myId");
        fieldNames.add("userId");
        fieldNames.add("groupId");
        fieldNames.add("timeUpdated");
        fieldNames.add("user");
        fieldNames.add("group");
        fieldNames.add("read");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    SecretChatRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (SecretChatColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.verbosetech.yoohoo.models.SecretChat>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    public RealmList<com.verbosetech.yoohoo.models.Message> realmGet$messages() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (messagesRealmList != null) {
            return messagesRealmList;
        } else {
            LinkView linkView = proxyState.getRow$realm().getLinkList(columnInfo.messagesIndex);
            messagesRealmList = new RealmList<com.verbosetech.yoohoo.models.Message>(com.verbosetech.yoohoo.models.Message.class, linkView, proxyState.getRealm$realm());
            return messagesRealmList;
        }
    }

    @Override
    public void realmSet$messages(RealmList<com.verbosetech.yoohoo.models.Message> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("messages")) {
                return;
            }
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.verbosetech.yoohoo.models.Message> original = value;
                value = new RealmList<com.verbosetech.yoohoo.models.Message>();
                for (com.verbosetech.yoohoo.models.Message item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        LinkView links = proxyState.getRow$realm().getLinkList(columnInfo.messagesIndex);
        links.clear();
        if (value == null) {
            return;
        }
        for (RealmModel linkedObject : (RealmList<? extends RealmModel>) value) {
            if (!(RealmObject.isManaged(linkedObject) && RealmObject.isValid(linkedObject))) {
                throw new IllegalArgumentException("Each element of 'value' must be a valid managed object.");
            }
            if (((RealmObjectProxy)linkedObject).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("Each element of 'value' must belong to the same Realm.");
            }
            links.add(((RealmObjectProxy)linkedObject).realmGet$proxyState().getRow$realm().getIndex());
        }
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$lastMessage() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.lastMessageIndex);
    }

    @Override
    public void realmSet$lastMessage(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.lastMessageIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.lastMessageIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.lastMessageIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.lastMessageIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$myId() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.myIdIndex);
    }

    @Override
    public void realmSet$myId(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.myIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.myIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.myIdIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.myIdIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$userId() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.userIdIndex);
    }

    @Override
    public void realmSet$userId(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.userIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.userIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.userIdIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.userIdIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$groupId() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.groupIdIndex);
    }

    @Override
    public void realmSet$groupId(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.groupIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.groupIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.groupIdIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.groupIdIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$timeUpdated() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.timeUpdatedIndex);
    }

    @Override
    public void realmSet$timeUpdated(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.timeUpdatedIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.timeUpdatedIndex, value);
    }

    @Override
    public com.verbosetech.yoohoo.models.User realmGet$user() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.userIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.verbosetech.yoohoo.models.User.class, proxyState.getRow$realm().getLink(columnInfo.userIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$user(com.verbosetech.yoohoo.models.User value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("user")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.userIndex);
                return;
            }
            if (!RealmObject.isValid(value)) {
                throw new IllegalArgumentException("'value' is not a valid managed object.");
            }
            if (((RealmObjectProxy) value).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("'value' belongs to a different Realm.");
            }
            row.getTable().setLink(columnInfo.userIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.userIndex);
            return;
        }
        if (!(RealmObject.isManaged(value) && RealmObject.isValid(value))) {
            throw new IllegalArgumentException("'value' is not a valid managed object.");
        }
        if (((RealmObjectProxy)value).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
            throw new IllegalArgumentException("'value' belongs to a different Realm.");
        }
        proxyState.getRow$realm().setLink(columnInfo.userIndex, ((RealmObjectProxy)value).realmGet$proxyState().getRow$realm().getIndex());
    }

    @Override
    public com.verbosetech.yoohoo.models.Group realmGet$group() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.groupIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.verbosetech.yoohoo.models.Group.class, proxyState.getRow$realm().getLink(columnInfo.groupIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$group(com.verbosetech.yoohoo.models.Group value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("group")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.groupIndex);
                return;
            }
            if (!RealmObject.isValid(value)) {
                throw new IllegalArgumentException("'value' is not a valid managed object.");
            }
            if (((RealmObjectProxy) value).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("'value' belongs to a different Realm.");
            }
            row.getTable().setLink(columnInfo.groupIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.groupIndex);
            return;
        }
        if (!(RealmObject.isManaged(value) && RealmObject.isValid(value))) {
            throw new IllegalArgumentException("'value' is not a valid managed object.");
        }
        if (((RealmObjectProxy)value).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
            throw new IllegalArgumentException("'value' belongs to a different Realm.");
        }
        proxyState.getRow$realm().setLink(columnInfo.groupIndex, ((RealmObjectProxy)value).realmGet$proxyState().getRow$realm().getIndex());
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$read() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.readIndex);
    }

    @Override
    public void realmSet$read(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.readIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.readIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("SecretChat")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("SecretChat");
            if (!realmSchema.contains("Message")) {
                MessageRealmProxy.createRealmObjectSchema(realmSchema);
            }
            realmObjectSchema.add("messages", RealmFieldType.LIST, realmSchema.get("Message"));
            realmObjectSchema.add("lastMessage", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("myId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("userId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("groupId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("timeUpdated", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            if (!realmSchema.contains("User")) {
                UserRealmProxy.createRealmObjectSchema(realmSchema);
            }
            realmObjectSchema.add("user", RealmFieldType.OBJECT, realmSchema.get("User"));
            if (!realmSchema.contains("Group")) {
                GroupRealmProxy.createRealmObjectSchema(realmSchema);
            }
            realmObjectSchema.add("group", RealmFieldType.OBJECT, realmSchema.get("Group"));
            realmObjectSchema.add("read", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            return realmObjectSchema;
        }
        return realmSchema.get("SecretChat");
    }

    public static SecretChatColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (!sharedRealm.hasTable("class_SecretChat")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'SecretChat' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_SecretChat");
        final long columnCount = table.getColumnCount();
        if (columnCount != 9) {
            if (columnCount < 9) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 9 but was " + columnCount);
            }
            if (allowExtraColumns) {
                RealmLog.debug("Field count is more than expected - expected 9 but was %1$d", columnCount);
            } else {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 9 but was " + columnCount);
            }
        }
        Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
        for (long i = 0; i < columnCount; i++) {
            columnTypes.put(table.getColumnName(i), table.getColumnType(i));
        }

        final SecretChatColumnInfo columnInfo = new SecretChatColumnInfo(sharedRealm.getPath(), table);

        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }

        if (!columnTypes.containsKey("messages")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'messages'");
        }
        if (columnTypes.get("messages") != RealmFieldType.LIST) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Message' for field 'messages'");
        }
        if (!sharedRealm.hasTable("class_Message")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_Message' for field 'messages'");
        }
        Table table_0 = sharedRealm.getTable("class_Message");
        if (!table.getLinkTarget(columnInfo.messagesIndex).hasSameSchema(table_0)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmList type for field 'messages': '" + table.getLinkTarget(columnInfo.messagesIndex).getName() + "' expected - was '" + table_0.getName() + "'");
        }
        if (!columnTypes.containsKey("lastMessage")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'lastMessage' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("lastMessage") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'lastMessage' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.lastMessageIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'lastMessage' is required. Either set @Required to field 'lastMessage' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("myId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'myId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("myId") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'myId' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.myIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'myId' is required. Either set @Required to field 'myId' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("userId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'userId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("userId") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'userId' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.userIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'userId' is required. Either set @Required to field 'userId' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("groupId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'groupId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("groupId") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'groupId' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.groupIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'groupId' is required. Either set @Required to field 'groupId' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("timeUpdated")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'timeUpdated' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("timeUpdated") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'timeUpdated' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.timeUpdatedIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'timeUpdated' does support null values in the existing Realm file. Use corresponding boxed type for field 'timeUpdated' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("user")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'user' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("user") != RealmFieldType.OBJECT) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'User' for field 'user'");
        }
        if (!sharedRealm.hasTable("class_User")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_User' for field 'user'");
        }
        Table table_6 = sharedRealm.getTable("class_User");
        if (!table.getLinkTarget(columnInfo.userIndex).hasSameSchema(table_6)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmObject for field 'user': '" + table.getLinkTarget(columnInfo.userIndex).getName() + "' expected - was '" + table_6.getName() + "'");
        }
        if (!columnTypes.containsKey("group")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'group' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("group") != RealmFieldType.OBJECT) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Group' for field 'group'");
        }
        if (!sharedRealm.hasTable("class_Group")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_Group' for field 'group'");
        }
        Table table_7 = sharedRealm.getTable("class_Group");
        if (!table.getLinkTarget(columnInfo.groupIndex).hasSameSchema(table_7)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmObject for field 'group': '" + table.getLinkTarget(columnInfo.groupIndex).getName() + "' expected - was '" + table_7.getName() + "'");
        }
        if (!columnTypes.containsKey("read")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'read' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("read") != RealmFieldType.BOOLEAN) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'read' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.readIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'read' does support null values in the existing Realm file. Use corresponding boxed type for field 'read' or migrate using RealmObjectSchema.setNullable().");
        }

        return columnInfo;
    }

    public static String getTableName() {
        return "class_SecretChat";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.verbosetech.yoohoo.models.SecretChat createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(3);
        if (json.has("messages")) {
            excludeFields.add("messages");
        }
        if (json.has("user")) {
            excludeFields.add("user");
        }
        if (json.has("group")) {
            excludeFields.add("group");
        }
        com.verbosetech.yoohoo.models.SecretChat obj = realm.createObjectInternal(com.verbosetech.yoohoo.models.SecretChat.class, true, excludeFields);
        if (json.has("messages")) {
            if (json.isNull("messages")) {
                ((SecretChatRealmProxyInterface) obj).realmSet$messages(null);
            } else {
                ((SecretChatRealmProxyInterface) obj).realmGet$messages().clear();
                JSONArray array = json.getJSONArray("messages");
                for (int i = 0; i < array.length(); i++) {
                    com.verbosetech.yoohoo.models.Message item = MessageRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    ((SecretChatRealmProxyInterface) obj).realmGet$messages().add(item);
                }
            }
        }
        if (json.has("lastMessage")) {
            if (json.isNull("lastMessage")) {
                ((SecretChatRealmProxyInterface) obj).realmSet$lastMessage(null);
            } else {
                ((SecretChatRealmProxyInterface) obj).realmSet$lastMessage((String) json.getString("lastMessage"));
            }
        }
        if (json.has("myId")) {
            if (json.isNull("myId")) {
                ((SecretChatRealmProxyInterface) obj).realmSet$myId(null);
            } else {
                ((SecretChatRealmProxyInterface) obj).realmSet$myId((String) json.getString("myId"));
            }
        }
        if (json.has("userId")) {
            if (json.isNull("userId")) {
                ((SecretChatRealmProxyInterface) obj).realmSet$userId(null);
            } else {
                ((SecretChatRealmProxyInterface) obj).realmSet$userId((String) json.getString("userId"));
            }
        }
        if (json.has("groupId")) {
            if (json.isNull("groupId")) {
                ((SecretChatRealmProxyInterface) obj).realmSet$groupId(null);
            } else {
                ((SecretChatRealmProxyInterface) obj).realmSet$groupId((String) json.getString("groupId"));
            }
        }
        if (json.has("timeUpdated")) {
            if (json.isNull("timeUpdated")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'timeUpdated' to null.");
            } else {
                ((SecretChatRealmProxyInterface) obj).realmSet$timeUpdated((long) json.getLong("timeUpdated"));
            }
        }
        if (json.has("user")) {
            if (json.isNull("user")) {
                ((SecretChatRealmProxyInterface) obj).realmSet$user(null);
            } else {
                com.verbosetech.yoohoo.models.User userObj = UserRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("user"), update);
                ((SecretChatRealmProxyInterface) obj).realmSet$user(userObj);
            }
        }
        if (json.has("group")) {
            if (json.isNull("group")) {
                ((SecretChatRealmProxyInterface) obj).realmSet$group(null);
            } else {
                com.verbosetech.yoohoo.models.Group groupObj = GroupRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("group"), update);
                ((SecretChatRealmProxyInterface) obj).realmSet$group(groupObj);
            }
        }
        if (json.has("read")) {
            if (json.isNull("read")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'read' to null.");
            } else {
                ((SecretChatRealmProxyInterface) obj).realmSet$read((boolean) json.getBoolean("read"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.verbosetech.yoohoo.models.SecretChat createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        com.verbosetech.yoohoo.models.SecretChat obj = new com.verbosetech.yoohoo.models.SecretChat();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("messages")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((SecretChatRealmProxyInterface) obj).realmSet$messages(null);
                } else {
                    ((SecretChatRealmProxyInterface) obj).realmSet$messages(new RealmList<com.verbosetech.yoohoo.models.Message>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.verbosetech.yoohoo.models.Message item = MessageRealmProxy.createUsingJsonStream(realm, reader);
                        ((SecretChatRealmProxyInterface) obj).realmGet$messages().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("lastMessage")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((SecretChatRealmProxyInterface) obj).realmSet$lastMessage(null);
                } else {
                    ((SecretChatRealmProxyInterface) obj).realmSet$lastMessage((String) reader.nextString());
                }
            } else if (name.equals("myId")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((SecretChatRealmProxyInterface) obj).realmSet$myId(null);
                } else {
                    ((SecretChatRealmProxyInterface) obj).realmSet$myId((String) reader.nextString());
                }
            } else if (name.equals("userId")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((SecretChatRealmProxyInterface) obj).realmSet$userId(null);
                } else {
                    ((SecretChatRealmProxyInterface) obj).realmSet$userId((String) reader.nextString());
                }
            } else if (name.equals("groupId")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((SecretChatRealmProxyInterface) obj).realmSet$groupId(null);
                } else {
                    ((SecretChatRealmProxyInterface) obj).realmSet$groupId((String) reader.nextString());
                }
            } else if (name.equals("timeUpdated")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'timeUpdated' to null.");
                } else {
                    ((SecretChatRealmProxyInterface) obj).realmSet$timeUpdated((long) reader.nextLong());
                }
            } else if (name.equals("user")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((SecretChatRealmProxyInterface) obj).realmSet$user(null);
                } else {
                    com.verbosetech.yoohoo.models.User userObj = UserRealmProxy.createUsingJsonStream(realm, reader);
                    ((SecretChatRealmProxyInterface) obj).realmSet$user(userObj);
                }
            } else if (name.equals("group")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((SecretChatRealmProxyInterface) obj).realmSet$group(null);
                } else {
                    com.verbosetech.yoohoo.models.Group groupObj = GroupRealmProxy.createUsingJsonStream(realm, reader);
                    ((SecretChatRealmProxyInterface) obj).realmSet$group(groupObj);
                }
            } else if (name.equals("read")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'read' to null.");
                } else {
                    ((SecretChatRealmProxyInterface) obj).realmSet$read((boolean) reader.nextBoolean());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static com.verbosetech.yoohoo.models.SecretChat copyOrUpdate(Realm realm, com.verbosetech.yoohoo.models.SecretChat object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.verbosetech.yoohoo.models.SecretChat) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static com.verbosetech.yoohoo.models.SecretChat copy(Realm realm, com.verbosetech.yoohoo.models.SecretChat newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.verbosetech.yoohoo.models.SecretChat) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            com.verbosetech.yoohoo.models.SecretChat realmObject = realm.createObjectInternal(com.verbosetech.yoohoo.models.SecretChat.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);

            RealmList<com.verbosetech.yoohoo.models.Message> messagesList = ((SecretChatRealmProxyInterface) newObject).realmGet$messages();
            if (messagesList != null) {
                RealmList<com.verbosetech.yoohoo.models.Message> messagesRealmList = ((SecretChatRealmProxyInterface) realmObject).realmGet$messages();
                for (int i = 0; i < messagesList.size(); i++) {
                    com.verbosetech.yoohoo.models.Message messagesItem = messagesList.get(i);
                    com.verbosetech.yoohoo.models.Message cachemessages = (com.verbosetech.yoohoo.models.Message) cache.get(messagesItem);
                    if (cachemessages != null) {
                        messagesRealmList.add(cachemessages);
                    } else {
                        messagesRealmList.add(MessageRealmProxy.copyOrUpdate(realm, messagesList.get(i), update, cache));
                    }
                }
            }

            ((SecretChatRealmProxyInterface) realmObject).realmSet$lastMessage(((SecretChatRealmProxyInterface) newObject).realmGet$lastMessage());
            ((SecretChatRealmProxyInterface) realmObject).realmSet$myId(((SecretChatRealmProxyInterface) newObject).realmGet$myId());
            ((SecretChatRealmProxyInterface) realmObject).realmSet$userId(((SecretChatRealmProxyInterface) newObject).realmGet$userId());
            ((SecretChatRealmProxyInterface) realmObject).realmSet$groupId(((SecretChatRealmProxyInterface) newObject).realmGet$groupId());
            ((SecretChatRealmProxyInterface) realmObject).realmSet$timeUpdated(((SecretChatRealmProxyInterface) newObject).realmGet$timeUpdated());

            com.verbosetech.yoohoo.models.User userObj = ((SecretChatRealmProxyInterface) newObject).realmGet$user();
            if (userObj != null) {
                com.verbosetech.yoohoo.models.User cacheuser = (com.verbosetech.yoohoo.models.User) cache.get(userObj);
                if (cacheuser != null) {
                    ((SecretChatRealmProxyInterface) realmObject).realmSet$user(cacheuser);
                } else {
                    ((SecretChatRealmProxyInterface) realmObject).realmSet$user(UserRealmProxy.copyOrUpdate(realm, userObj, update, cache));
                }
            } else {
                ((SecretChatRealmProxyInterface) realmObject).realmSet$user(null);
            }

            com.verbosetech.yoohoo.models.Group groupObj = ((SecretChatRealmProxyInterface) newObject).realmGet$group();
            if (groupObj != null) {
                com.verbosetech.yoohoo.models.Group cachegroup = (com.verbosetech.yoohoo.models.Group) cache.get(groupObj);
                if (cachegroup != null) {
                    ((SecretChatRealmProxyInterface) realmObject).realmSet$group(cachegroup);
                } else {
                    ((SecretChatRealmProxyInterface) realmObject).realmSet$group(GroupRealmProxy.copyOrUpdate(realm, groupObj, update, cache));
                }
            } else {
                ((SecretChatRealmProxyInterface) realmObject).realmSet$group(null);
            }
            ((SecretChatRealmProxyInterface) realmObject).realmSet$read(((SecretChatRealmProxyInterface) newObject).realmGet$read());
            return realmObject;
        }
    }

    public static long insert(Realm realm, com.verbosetech.yoohoo.models.SecretChat object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.verbosetech.yoohoo.models.SecretChat.class);
        long tableNativePtr = table.getNativeTablePointer();
        SecretChatColumnInfo columnInfo = (SecretChatColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.SecretChat.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);

        RealmList<com.verbosetech.yoohoo.models.Message> messagesList = ((SecretChatRealmProxyInterface) object).realmGet$messages();
        if (messagesList != null) {
            long messagesNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.messagesIndex, rowIndex);
            for (com.verbosetech.yoohoo.models.Message messagesItem : messagesList) {
                Long cacheItemIndexmessages = cache.get(messagesItem);
                if (cacheItemIndexmessages == null) {
                    cacheItemIndexmessages = MessageRealmProxy.insert(realm, messagesItem, cache);
                }
                LinkView.nativeAdd(messagesNativeLinkViewPtr, cacheItemIndexmessages);
            }
        }

        String realmGet$lastMessage = ((SecretChatRealmProxyInterface)object).realmGet$lastMessage();
        if (realmGet$lastMessage != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.lastMessageIndex, rowIndex, realmGet$lastMessage, false);
        }
        String realmGet$myId = ((SecretChatRealmProxyInterface)object).realmGet$myId();
        if (realmGet$myId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.myIdIndex, rowIndex, realmGet$myId, false);
        }
        String realmGet$userId = ((SecretChatRealmProxyInterface)object).realmGet$userId();
        if (realmGet$userId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.userIdIndex, rowIndex, realmGet$userId, false);
        }
        String realmGet$groupId = ((SecretChatRealmProxyInterface)object).realmGet$groupId();
        if (realmGet$groupId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.groupIdIndex, rowIndex, realmGet$groupId, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.timeUpdatedIndex, rowIndex, ((SecretChatRealmProxyInterface)object).realmGet$timeUpdated(), false);

        com.verbosetech.yoohoo.models.User userObj = ((SecretChatRealmProxyInterface) object).realmGet$user();
        if (userObj != null) {
            Long cacheuser = cache.get(userObj);
            if (cacheuser == null) {
                cacheuser = UserRealmProxy.insert(realm, userObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
        }

        com.verbosetech.yoohoo.models.Group groupObj = ((SecretChatRealmProxyInterface) object).realmGet$group();
        if (groupObj != null) {
            Long cachegroup = cache.get(groupObj);
            if (cachegroup == null) {
                cachegroup = GroupRealmProxy.insert(realm, groupObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.groupIndex, rowIndex, cachegroup, false);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.readIndex, rowIndex, ((SecretChatRealmProxyInterface)object).realmGet$read(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.verbosetech.yoohoo.models.SecretChat.class);
        long tableNativePtr = table.getNativeTablePointer();
        SecretChatColumnInfo columnInfo = (SecretChatColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.SecretChat.class);
        com.verbosetech.yoohoo.models.SecretChat object = null;
        while (objects.hasNext()) {
            object = (com.verbosetech.yoohoo.models.SecretChat) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);

                RealmList<com.verbosetech.yoohoo.models.Message> messagesList = ((SecretChatRealmProxyInterface) object).realmGet$messages();
                if (messagesList != null) {
                    long messagesNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.messagesIndex, rowIndex);
                    for (com.verbosetech.yoohoo.models.Message messagesItem : messagesList) {
                        Long cacheItemIndexmessages = cache.get(messagesItem);
                        if (cacheItemIndexmessages == null) {
                            cacheItemIndexmessages = MessageRealmProxy.insert(realm, messagesItem, cache);
                        }
                        LinkView.nativeAdd(messagesNativeLinkViewPtr, cacheItemIndexmessages);
                    }
                }

                String realmGet$lastMessage = ((SecretChatRealmProxyInterface)object).realmGet$lastMessage();
                if (realmGet$lastMessage != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.lastMessageIndex, rowIndex, realmGet$lastMessage, false);
                }
                String realmGet$myId = ((SecretChatRealmProxyInterface)object).realmGet$myId();
                if (realmGet$myId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.myIdIndex, rowIndex, realmGet$myId, false);
                }
                String realmGet$userId = ((SecretChatRealmProxyInterface)object).realmGet$userId();
                if (realmGet$userId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.userIdIndex, rowIndex, realmGet$userId, false);
                }
                String realmGet$groupId = ((SecretChatRealmProxyInterface)object).realmGet$groupId();
                if (realmGet$groupId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.groupIdIndex, rowIndex, realmGet$groupId, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.timeUpdatedIndex, rowIndex, ((SecretChatRealmProxyInterface)object).realmGet$timeUpdated(), false);

                com.verbosetech.yoohoo.models.User userObj = ((SecretChatRealmProxyInterface) object).realmGet$user();
                if (userObj != null) {
                    Long cacheuser = cache.get(userObj);
                    if (cacheuser == null) {
                        cacheuser = UserRealmProxy.insert(realm, userObj, cache);
                    }
                    table.setLink(columnInfo.userIndex, rowIndex, cacheuser, false);
                }

                com.verbosetech.yoohoo.models.Group groupObj = ((SecretChatRealmProxyInterface) object).realmGet$group();
                if (groupObj != null) {
                    Long cachegroup = cache.get(groupObj);
                    if (cachegroup == null) {
                        cachegroup = GroupRealmProxy.insert(realm, groupObj, cache);
                    }
                    table.setLink(columnInfo.groupIndex, rowIndex, cachegroup, false);
                }
                Table.nativeSetBoolean(tableNativePtr, columnInfo.readIndex, rowIndex, ((SecretChatRealmProxyInterface)object).realmGet$read(), false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.verbosetech.yoohoo.models.SecretChat object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.verbosetech.yoohoo.models.SecretChat.class);
        long tableNativePtr = table.getNativeTablePointer();
        SecretChatColumnInfo columnInfo = (SecretChatColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.SecretChat.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);

        long messagesNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.messagesIndex, rowIndex);
        LinkView.nativeClear(messagesNativeLinkViewPtr);
        RealmList<com.verbosetech.yoohoo.models.Message> messagesList = ((SecretChatRealmProxyInterface) object).realmGet$messages();
        if (messagesList != null) {
            for (com.verbosetech.yoohoo.models.Message messagesItem : messagesList) {
                Long cacheItemIndexmessages = cache.get(messagesItem);
                if (cacheItemIndexmessages == null) {
                    cacheItemIndexmessages = MessageRealmProxy.insertOrUpdate(realm, messagesItem, cache);
                }
                LinkView.nativeAdd(messagesNativeLinkViewPtr, cacheItemIndexmessages);
            }
        }

        String realmGet$lastMessage = ((SecretChatRealmProxyInterface)object).realmGet$lastMessage();
        if (realmGet$lastMessage != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.lastMessageIndex, rowIndex, realmGet$lastMessage, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.lastMessageIndex, rowIndex, false);
        }
        String realmGet$myId = ((SecretChatRealmProxyInterface)object).realmGet$myId();
        if (realmGet$myId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.myIdIndex, rowIndex, realmGet$myId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.myIdIndex, rowIndex, false);
        }
        String realmGet$userId = ((SecretChatRealmProxyInterface)object).realmGet$userId();
        if (realmGet$userId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.userIdIndex, rowIndex, realmGet$userId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.userIdIndex, rowIndex, false);
        }
        String realmGet$groupId = ((SecretChatRealmProxyInterface)object).realmGet$groupId();
        if (realmGet$groupId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.groupIdIndex, rowIndex, realmGet$groupId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.groupIdIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.timeUpdatedIndex, rowIndex, ((SecretChatRealmProxyInterface)object).realmGet$timeUpdated(), false);

        com.verbosetech.yoohoo.models.User userObj = ((SecretChatRealmProxyInterface) object).realmGet$user();
        if (userObj != null) {
            Long cacheuser = cache.get(userObj);
            if (cacheuser == null) {
                cacheuser = UserRealmProxy.insertOrUpdate(realm, userObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.userIndex, rowIndex);
        }

        com.verbosetech.yoohoo.models.Group groupObj = ((SecretChatRealmProxyInterface) object).realmGet$group();
        if (groupObj != null) {
            Long cachegroup = cache.get(groupObj);
            if (cachegroup == null) {
                cachegroup = GroupRealmProxy.insertOrUpdate(realm, groupObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.groupIndex, rowIndex, cachegroup, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.groupIndex, rowIndex);
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.readIndex, rowIndex, ((SecretChatRealmProxyInterface)object).realmGet$read(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.verbosetech.yoohoo.models.SecretChat.class);
        long tableNativePtr = table.getNativeTablePointer();
        SecretChatColumnInfo columnInfo = (SecretChatColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.SecretChat.class);
        com.verbosetech.yoohoo.models.SecretChat object = null;
        while (objects.hasNext()) {
            object = (com.verbosetech.yoohoo.models.SecretChat) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);

                long messagesNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.messagesIndex, rowIndex);
                LinkView.nativeClear(messagesNativeLinkViewPtr);
                RealmList<com.verbosetech.yoohoo.models.Message> messagesList = ((SecretChatRealmProxyInterface) object).realmGet$messages();
                if (messagesList != null) {
                    for (com.verbosetech.yoohoo.models.Message messagesItem : messagesList) {
                        Long cacheItemIndexmessages = cache.get(messagesItem);
                        if (cacheItemIndexmessages == null) {
                            cacheItemIndexmessages = MessageRealmProxy.insertOrUpdate(realm, messagesItem, cache);
                        }
                        LinkView.nativeAdd(messagesNativeLinkViewPtr, cacheItemIndexmessages);
                    }
                }

                String realmGet$lastMessage = ((SecretChatRealmProxyInterface)object).realmGet$lastMessage();
                if (realmGet$lastMessage != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.lastMessageIndex, rowIndex, realmGet$lastMessage, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.lastMessageIndex, rowIndex, false);
                }
                String realmGet$myId = ((SecretChatRealmProxyInterface)object).realmGet$myId();
                if (realmGet$myId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.myIdIndex, rowIndex, realmGet$myId, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.myIdIndex, rowIndex, false);
                }
                String realmGet$userId = ((SecretChatRealmProxyInterface)object).realmGet$userId();
                if (realmGet$userId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.userIdIndex, rowIndex, realmGet$userId, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.userIdIndex, rowIndex, false);
                }
                String realmGet$groupId = ((SecretChatRealmProxyInterface)object).realmGet$groupId();
                if (realmGet$groupId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.groupIdIndex, rowIndex, realmGet$groupId, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.groupIdIndex, rowIndex, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.timeUpdatedIndex, rowIndex, ((SecretChatRealmProxyInterface)object).realmGet$timeUpdated(), false);

                com.verbosetech.yoohoo.models.User userObj = ((SecretChatRealmProxyInterface) object).realmGet$user();
                if (userObj != null) {
                    Long cacheuser = cache.get(userObj);
                    if (cacheuser == null) {
                        cacheuser = UserRealmProxy.insertOrUpdate(realm, userObj, cache);
                    }
                    Table.nativeSetLink(tableNativePtr, columnInfo.userIndex, rowIndex, cacheuser, false);
                } else {
                    Table.nativeNullifyLink(tableNativePtr, columnInfo.userIndex, rowIndex);
                }

                com.verbosetech.yoohoo.models.Group groupObj = ((SecretChatRealmProxyInterface) object).realmGet$group();
                if (groupObj != null) {
                    Long cachegroup = cache.get(groupObj);
                    if (cachegroup == null) {
                        cachegroup = GroupRealmProxy.insertOrUpdate(realm, groupObj, cache);
                    }
                    Table.nativeSetLink(tableNativePtr, columnInfo.groupIndex, rowIndex, cachegroup, false);
                } else {
                    Table.nativeNullifyLink(tableNativePtr, columnInfo.groupIndex, rowIndex);
                }
                Table.nativeSetBoolean(tableNativePtr, columnInfo.readIndex, rowIndex, ((SecretChatRealmProxyInterface)object).realmGet$read(), false);
            }
        }
    }

    public static com.verbosetech.yoohoo.models.SecretChat createDetachedCopy(com.verbosetech.yoohoo.models.SecretChat realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.verbosetech.yoohoo.models.SecretChat unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.verbosetech.yoohoo.models.SecretChat)cachedObject.object;
            } else {
                unmanagedObject = (com.verbosetech.yoohoo.models.SecretChat)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.verbosetech.yoohoo.models.SecretChat();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        }

        // Deep copy of messages
        if (currentDepth == maxDepth) {
            ((SecretChatRealmProxyInterface) unmanagedObject).realmSet$messages(null);
        } else {
            RealmList<com.verbosetech.yoohoo.models.Message> managedmessagesList = ((SecretChatRealmProxyInterface) realmObject).realmGet$messages();
            RealmList<com.verbosetech.yoohoo.models.Message> unmanagedmessagesList = new RealmList<com.verbosetech.yoohoo.models.Message>();
            ((SecretChatRealmProxyInterface) unmanagedObject).realmSet$messages(unmanagedmessagesList);
            int nextDepth = currentDepth + 1;
            int size = managedmessagesList.size();
            for (int i = 0; i < size; i++) {
                com.verbosetech.yoohoo.models.Message item = MessageRealmProxy.createDetachedCopy(managedmessagesList.get(i), nextDepth, maxDepth, cache);
                unmanagedmessagesList.add(item);
            }
        }
        ((SecretChatRealmProxyInterface) unmanagedObject).realmSet$lastMessage(((SecretChatRealmProxyInterface) realmObject).realmGet$lastMessage());
        ((SecretChatRealmProxyInterface) unmanagedObject).realmSet$myId(((SecretChatRealmProxyInterface) realmObject).realmGet$myId());
        ((SecretChatRealmProxyInterface) unmanagedObject).realmSet$userId(((SecretChatRealmProxyInterface) realmObject).realmGet$userId());
        ((SecretChatRealmProxyInterface) unmanagedObject).realmSet$groupId(((SecretChatRealmProxyInterface) realmObject).realmGet$groupId());
        ((SecretChatRealmProxyInterface) unmanagedObject).realmSet$timeUpdated(((SecretChatRealmProxyInterface) realmObject).realmGet$timeUpdated());

        // Deep copy of user
        ((SecretChatRealmProxyInterface) unmanagedObject).realmSet$user(UserRealmProxy.createDetachedCopy(((SecretChatRealmProxyInterface) realmObject).realmGet$user(), currentDepth + 1, maxDepth, cache));

        // Deep copy of group
        ((SecretChatRealmProxyInterface) unmanagedObject).realmSet$group(GroupRealmProxy.createDetachedCopy(((SecretChatRealmProxyInterface) realmObject).realmGet$group(), currentDepth + 1, maxDepth, cache));
        ((SecretChatRealmProxyInterface) unmanagedObject).realmSet$read(((SecretChatRealmProxyInterface) realmObject).realmGet$read());
        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("SecretChat = [");
        stringBuilder.append("{messages:");
        stringBuilder.append("RealmList<Message>[").append(realmGet$messages().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{lastMessage:");
        stringBuilder.append(realmGet$lastMessage() != null ? realmGet$lastMessage() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{myId:");
        stringBuilder.append(realmGet$myId() != null ? realmGet$myId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{userId:");
        stringBuilder.append(realmGet$userId() != null ? realmGet$userId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{groupId:");
        stringBuilder.append(realmGet$groupId() != null ? realmGet$groupId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{timeUpdated:");
        stringBuilder.append(realmGet$timeUpdated());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{user:");
        stringBuilder.append(realmGet$user() != null ? "User" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{group:");
        stringBuilder.append(realmGet$group() != null ? "Group" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{read:");
        stringBuilder.append(realmGet$read());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecretChatRealmProxy aSecretChat = (SecretChatRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aSecretChat.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aSecretChat.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aSecretChat.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
