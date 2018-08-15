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

public class MessageRealmProxy extends com.verbosetech.yoohoo.models.Message
    implements RealmObjectProxy, MessageRealmProxyInterface {

    static final class MessageColumnInfo extends ColumnInfo
        implements Cloneable {

        public long bodyIndex;
        public long senderNameIndex;
        public long senderIdIndex;
        public long recipientIdIndex;
        public long idIndex;
        public long dateIndex;
        public long durationIndex;
        public long deliveredIndex;
        public long sentIndex;
        public long attachmentTypeIndex;
        public long attachmentIndex;

        MessageColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(11);
            this.bodyIndex = getValidColumnIndex(path, table, "Message", "body");
            indicesMap.put("body", this.bodyIndex);
            this.senderNameIndex = getValidColumnIndex(path, table, "Message", "senderName");
            indicesMap.put("senderName", this.senderNameIndex);
            this.senderIdIndex = getValidColumnIndex(path, table, "Message", "senderId");
            indicesMap.put("senderId", this.senderIdIndex);
            this.recipientIdIndex = getValidColumnIndex(path, table, "Message", "recipientId");
            indicesMap.put("recipientId", this.recipientIdIndex);
            this.idIndex = getValidColumnIndex(path, table, "Message", "id");
            indicesMap.put("id", this.idIndex);
            this.dateIndex = getValidColumnIndex(path, table, "Message", "date");
            indicesMap.put("date", this.dateIndex);
            this.durationIndex = getValidColumnIndex(path, table, "Message", "duration");
            indicesMap.put("duration", this.durationIndex);
            this.deliveredIndex = getValidColumnIndex(path, table, "Message", "delivered");
            indicesMap.put("delivered", this.deliveredIndex);
            this.sentIndex = getValidColumnIndex(path, table, "Message", "sent");
            indicesMap.put("sent", this.sentIndex);
            this.attachmentTypeIndex = getValidColumnIndex(path, table, "Message", "attachmentType");
            indicesMap.put("attachmentType", this.attachmentTypeIndex);
            this.attachmentIndex = getValidColumnIndex(path, table, "Message", "attachment");
            indicesMap.put("attachment", this.attachmentIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final MessageColumnInfo otherInfo = (MessageColumnInfo) other;
            this.bodyIndex = otherInfo.bodyIndex;
            this.senderNameIndex = otherInfo.senderNameIndex;
            this.senderIdIndex = otherInfo.senderIdIndex;
            this.recipientIdIndex = otherInfo.recipientIdIndex;
            this.idIndex = otherInfo.idIndex;
            this.dateIndex = otherInfo.dateIndex;
            this.durationIndex = otherInfo.durationIndex;
            this.deliveredIndex = otherInfo.deliveredIndex;
            this.sentIndex = otherInfo.sentIndex;
            this.attachmentTypeIndex = otherInfo.attachmentTypeIndex;
            this.attachmentIndex = otherInfo.attachmentIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final MessageColumnInfo clone() {
            return (MessageColumnInfo) super.clone();
        }

    }
    private MessageColumnInfo columnInfo;
    private ProxyState<com.verbosetech.yoohoo.models.Message> proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("body");
        fieldNames.add("senderName");
        fieldNames.add("senderId");
        fieldNames.add("recipientId");
        fieldNames.add("id");
        fieldNames.add("date");
        fieldNames.add("duration");
        fieldNames.add("delivered");
        fieldNames.add("sent");
        fieldNames.add("attachmentType");
        fieldNames.add("attachment");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    MessageRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (MessageColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.verbosetech.yoohoo.models.Message>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$body() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.bodyIndex);
    }

    @Override
    public void realmSet$body(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.bodyIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.bodyIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.bodyIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.bodyIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$senderName() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.senderNameIndex);
    }

    @Override
    public void realmSet$senderName(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.senderNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.senderNameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.senderNameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.senderNameIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$senderId() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.senderIdIndex);
    }

    @Override
    public void realmSet$senderId(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.senderIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.senderIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.senderIdIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.senderIdIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$recipientId() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.recipientIdIndex);
    }

    @Override
    public void realmSet$recipientId(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.recipientIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.recipientIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.recipientIdIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.recipientIdIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.idIndex);
    }

    @Override
    public void realmSet$id(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.idIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.idIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.idIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.idIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$date() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.dateIndex);
    }

    @Override
    public void realmSet$date(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.dateIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.dateIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$duration() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.durationIndex);
    }

    @Override
    public void realmSet$duration(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.durationIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.durationIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$delivered() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.deliveredIndex);
    }

    @Override
    public void realmSet$delivered(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.deliveredIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.deliveredIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$sent() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.sentIndex);
    }

    @Override
    public void realmSet$sent(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.sentIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.sentIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$attachmentType() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.attachmentTypeIndex);
    }

    @Override
    public void realmSet$attachmentType(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.attachmentTypeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.attachmentTypeIndex, value);
    }

    @Override
    public com.verbosetech.yoohoo.models.Attachment realmGet$attachment() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.attachmentIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.verbosetech.yoohoo.models.Attachment.class, proxyState.getRow$realm().getLink(columnInfo.attachmentIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$attachment(com.verbosetech.yoohoo.models.Attachment value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("attachment")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.attachmentIndex);
                return;
            }
            if (!RealmObject.isValid(value)) {
                throw new IllegalArgumentException("'value' is not a valid managed object.");
            }
            if (((RealmObjectProxy) value).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("'value' belongs to a different Realm.");
            }
            row.getTable().setLink(columnInfo.attachmentIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.attachmentIndex);
            return;
        }
        if (!(RealmObject.isManaged(value) && RealmObject.isValid(value))) {
            throw new IllegalArgumentException("'value' is not a valid managed object.");
        }
        if (((RealmObjectProxy)value).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
            throw new IllegalArgumentException("'value' belongs to a different Realm.");
        }
        proxyState.getRow$realm().setLink(columnInfo.attachmentIndex, ((RealmObjectProxy)value).realmGet$proxyState().getRow$realm().getIndex());
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("Message")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("Message");
            realmObjectSchema.add("body", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("senderName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("senderId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("recipientId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("date", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            realmObjectSchema.add("duration", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            realmObjectSchema.add("delivered", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            realmObjectSchema.add("sent", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            realmObjectSchema.add("attachmentType", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            if (!realmSchema.contains("Attachment")) {
                AttachmentRealmProxy.createRealmObjectSchema(realmSchema);
            }
            realmObjectSchema.add("attachment", RealmFieldType.OBJECT, realmSchema.get("Attachment"));
            return realmObjectSchema;
        }
        return realmSchema.get("Message");
    }

    public static MessageColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (!sharedRealm.hasTable("class_Message")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'Message' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_Message");
        final long columnCount = table.getColumnCount();
        if (columnCount != 11) {
            if (columnCount < 11) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 11 but was " + columnCount);
            }
            if (allowExtraColumns) {
                RealmLog.debug("Field count is more than expected - expected 11 but was %1$d", columnCount);
            } else {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 11 but was " + columnCount);
            }
        }
        Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
        for (long i = 0; i < columnCount; i++) {
            columnTypes.put(table.getColumnName(i), table.getColumnType(i));
        }

        final MessageColumnInfo columnInfo = new MessageColumnInfo(sharedRealm.getPath(), table);

        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }

        if (!columnTypes.containsKey("body")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'body' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("body") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'body' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.bodyIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'body' is required. Either set @Required to field 'body' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("senderName")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'senderName' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("senderName") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'senderName' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.senderNameIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'senderName' is required. Either set @Required to field 'senderName' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("senderId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'senderId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("senderId") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'senderId' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.senderIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'senderId' is required. Either set @Required to field 'senderId' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("recipientId")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'recipientId' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("recipientId") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'recipientId' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.recipientIdIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'recipientId' is required. Either set @Required to field 'recipientId' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("id")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'id' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("id") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'id' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.idIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'id' is required. Either set @Required to field 'id' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("date")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'date' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("date") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'date' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.dateIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'date' does support null values in the existing Realm file. Use corresponding boxed type for field 'date' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("duration")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'duration' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("duration") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'duration' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.durationIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'duration' does support null values in the existing Realm file. Use corresponding boxed type for field 'duration' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("delivered")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'delivered' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("delivered") != RealmFieldType.BOOLEAN) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'delivered' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.deliveredIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'delivered' does support null values in the existing Realm file. Use corresponding boxed type for field 'delivered' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("sent")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'sent' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("sent") != RealmFieldType.BOOLEAN) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'boolean' for field 'sent' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.sentIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'sent' does support null values in the existing Realm file. Use corresponding boxed type for field 'sent' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("attachmentType")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'attachmentType' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("attachmentType") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'attachmentType' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.attachmentTypeIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'attachmentType' does support null values in the existing Realm file. Use corresponding boxed type for field 'attachmentType' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("attachment")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'attachment' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("attachment") != RealmFieldType.OBJECT) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Attachment' for field 'attachment'");
        }
        if (!sharedRealm.hasTable("class_Attachment")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_Attachment' for field 'attachment'");
        }
        Table table_10 = sharedRealm.getTable("class_Attachment");
        if (!table.getLinkTarget(columnInfo.attachmentIndex).hasSameSchema(table_10)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmObject for field 'attachment': '" + table.getLinkTarget(columnInfo.attachmentIndex).getName() + "' expected - was '" + table_10.getName() + "'");
        }

        return columnInfo;
    }

    public static String getTableName() {
        return "class_Message";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.verbosetech.yoohoo.models.Message createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        if (json.has("attachment")) {
            excludeFields.add("attachment");
        }
        com.verbosetech.yoohoo.models.Message obj = realm.createObjectInternal(com.verbosetech.yoohoo.models.Message.class, true, excludeFields);
        if (json.has("body")) {
            if (json.isNull("body")) {
                ((MessageRealmProxyInterface) obj).realmSet$body(null);
            } else {
                ((MessageRealmProxyInterface) obj).realmSet$body((String) json.getString("body"));
            }
        }
        if (json.has("senderName")) {
            if (json.isNull("senderName")) {
                ((MessageRealmProxyInterface) obj).realmSet$senderName(null);
            } else {
                ((MessageRealmProxyInterface) obj).realmSet$senderName((String) json.getString("senderName"));
            }
        }
        if (json.has("senderId")) {
            if (json.isNull("senderId")) {
                ((MessageRealmProxyInterface) obj).realmSet$senderId(null);
            } else {
                ((MessageRealmProxyInterface) obj).realmSet$senderId((String) json.getString("senderId"));
            }
        }
        if (json.has("recipientId")) {
            if (json.isNull("recipientId")) {
                ((MessageRealmProxyInterface) obj).realmSet$recipientId(null);
            } else {
                ((MessageRealmProxyInterface) obj).realmSet$recipientId((String) json.getString("recipientId"));
            }
        }
        if (json.has("id")) {
            if (json.isNull("id")) {
                ((MessageRealmProxyInterface) obj).realmSet$id(null);
            } else {
                ((MessageRealmProxyInterface) obj).realmSet$id((String) json.getString("id"));
            }
        }
        if (json.has("date")) {
            if (json.isNull("date")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'date' to null.");
            } else {
                ((MessageRealmProxyInterface) obj).realmSet$date((long) json.getLong("date"));
            }
        }
        if (json.has("duration")) {
            if (json.isNull("duration")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'duration' to null.");
            } else {
                ((MessageRealmProxyInterface) obj).realmSet$duration((long) json.getLong("duration"));
            }
        }
        if (json.has("delivered")) {
            if (json.isNull("delivered")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'delivered' to null.");
            } else {
                ((MessageRealmProxyInterface) obj).realmSet$delivered((boolean) json.getBoolean("delivered"));
            }
        }
        if (json.has("sent")) {
            if (json.isNull("sent")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'sent' to null.");
            } else {
                ((MessageRealmProxyInterface) obj).realmSet$sent((boolean) json.getBoolean("sent"));
            }
        }
        if (json.has("attachmentType")) {
            if (json.isNull("attachmentType")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'attachmentType' to null.");
            } else {
                ((MessageRealmProxyInterface) obj).realmSet$attachmentType((int) json.getInt("attachmentType"));
            }
        }
        if (json.has("attachment")) {
            if (json.isNull("attachment")) {
                ((MessageRealmProxyInterface) obj).realmSet$attachment(null);
            } else {
                com.verbosetech.yoohoo.models.Attachment attachmentObj = AttachmentRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("attachment"), update);
                ((MessageRealmProxyInterface) obj).realmSet$attachment(attachmentObj);
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.verbosetech.yoohoo.models.Message createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        com.verbosetech.yoohoo.models.Message obj = new com.verbosetech.yoohoo.models.Message();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("body")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmProxyInterface) obj).realmSet$body(null);
                } else {
                    ((MessageRealmProxyInterface) obj).realmSet$body((String) reader.nextString());
                }
            } else if (name.equals("senderName")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmProxyInterface) obj).realmSet$senderName(null);
                } else {
                    ((MessageRealmProxyInterface) obj).realmSet$senderName((String) reader.nextString());
                }
            } else if (name.equals("senderId")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmProxyInterface) obj).realmSet$senderId(null);
                } else {
                    ((MessageRealmProxyInterface) obj).realmSet$senderId((String) reader.nextString());
                }
            } else if (name.equals("recipientId")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmProxyInterface) obj).realmSet$recipientId(null);
                } else {
                    ((MessageRealmProxyInterface) obj).realmSet$recipientId((String) reader.nextString());
                }
            } else if (name.equals("id")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmProxyInterface) obj).realmSet$id(null);
                } else {
                    ((MessageRealmProxyInterface) obj).realmSet$id((String) reader.nextString());
                }
            } else if (name.equals("date")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'date' to null.");
                } else {
                    ((MessageRealmProxyInterface) obj).realmSet$date((long) reader.nextLong());
                }
            } else if (name.equals("duration")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'duration' to null.");
                } else {
                    ((MessageRealmProxyInterface) obj).realmSet$duration((long) reader.nextLong());
                }
            } else if (name.equals("delivered")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'delivered' to null.");
                } else {
                    ((MessageRealmProxyInterface) obj).realmSet$delivered((boolean) reader.nextBoolean());
                }
            } else if (name.equals("sent")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'sent' to null.");
                } else {
                    ((MessageRealmProxyInterface) obj).realmSet$sent((boolean) reader.nextBoolean());
                }
            } else if (name.equals("attachmentType")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'attachmentType' to null.");
                } else {
                    ((MessageRealmProxyInterface) obj).realmSet$attachmentType((int) reader.nextInt());
                }
            } else if (name.equals("attachment")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MessageRealmProxyInterface) obj).realmSet$attachment(null);
                } else {
                    com.verbosetech.yoohoo.models.Attachment attachmentObj = AttachmentRealmProxy.createUsingJsonStream(realm, reader);
                    ((MessageRealmProxyInterface) obj).realmSet$attachment(attachmentObj);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static com.verbosetech.yoohoo.models.Message copyOrUpdate(Realm realm, com.verbosetech.yoohoo.models.Message object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.verbosetech.yoohoo.models.Message) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static com.verbosetech.yoohoo.models.Message copy(Realm realm, com.verbosetech.yoohoo.models.Message newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.verbosetech.yoohoo.models.Message) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            com.verbosetech.yoohoo.models.Message realmObject = realm.createObjectInternal(com.verbosetech.yoohoo.models.Message.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((MessageRealmProxyInterface) realmObject).realmSet$body(((MessageRealmProxyInterface) newObject).realmGet$body());
            ((MessageRealmProxyInterface) realmObject).realmSet$senderName(((MessageRealmProxyInterface) newObject).realmGet$senderName());
            ((MessageRealmProxyInterface) realmObject).realmSet$senderId(((MessageRealmProxyInterface) newObject).realmGet$senderId());
            ((MessageRealmProxyInterface) realmObject).realmSet$recipientId(((MessageRealmProxyInterface) newObject).realmGet$recipientId());
            ((MessageRealmProxyInterface) realmObject).realmSet$id(((MessageRealmProxyInterface) newObject).realmGet$id());
            ((MessageRealmProxyInterface) realmObject).realmSet$date(((MessageRealmProxyInterface) newObject).realmGet$date());
            ((MessageRealmProxyInterface) realmObject).realmSet$duration(((MessageRealmProxyInterface) newObject).realmGet$duration());
            ((MessageRealmProxyInterface) realmObject).realmSet$delivered(((MessageRealmProxyInterface) newObject).realmGet$delivered());
            ((MessageRealmProxyInterface) realmObject).realmSet$sent(((MessageRealmProxyInterface) newObject).realmGet$sent());
            ((MessageRealmProxyInterface) realmObject).realmSet$attachmentType(((MessageRealmProxyInterface) newObject).realmGet$attachmentType());

            com.verbosetech.yoohoo.models.Attachment attachmentObj = ((MessageRealmProxyInterface) newObject).realmGet$attachment();
            if (attachmentObj != null) {
                com.verbosetech.yoohoo.models.Attachment cacheattachment = (com.verbosetech.yoohoo.models.Attachment) cache.get(attachmentObj);
                if (cacheattachment != null) {
                    ((MessageRealmProxyInterface) realmObject).realmSet$attachment(cacheattachment);
                } else {
                    ((MessageRealmProxyInterface) realmObject).realmSet$attachment(AttachmentRealmProxy.copyOrUpdate(realm, attachmentObj, update, cache));
                }
            } else {
                ((MessageRealmProxyInterface) realmObject).realmSet$attachment(null);
            }
            return realmObject;
        }
    }

    public static long insert(Realm realm, com.verbosetech.yoohoo.models.Message object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.verbosetech.yoohoo.models.Message.class);
        long tableNativePtr = table.getNativeTablePointer();
        MessageColumnInfo columnInfo = (MessageColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.Message.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$body = ((MessageRealmProxyInterface)object).realmGet$body();
        if (realmGet$body != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.bodyIndex, rowIndex, realmGet$body, false);
        }
        String realmGet$senderName = ((MessageRealmProxyInterface)object).realmGet$senderName();
        if (realmGet$senderName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.senderNameIndex, rowIndex, realmGet$senderName, false);
        }
        String realmGet$senderId = ((MessageRealmProxyInterface)object).realmGet$senderId();
        if (realmGet$senderId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.senderIdIndex, rowIndex, realmGet$senderId, false);
        }
        String realmGet$recipientId = ((MessageRealmProxyInterface)object).realmGet$recipientId();
        if (realmGet$recipientId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.recipientIdIndex, rowIndex, realmGet$recipientId, false);
        }
        String realmGet$id = ((MessageRealmProxyInterface)object).realmGet$id();
        if (realmGet$id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.dateIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$date(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.durationIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$duration(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.deliveredIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$delivered(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.sentIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$sent(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.attachmentTypeIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$attachmentType(), false);

        com.verbosetech.yoohoo.models.Attachment attachmentObj = ((MessageRealmProxyInterface) object).realmGet$attachment();
        if (attachmentObj != null) {
            Long cacheattachment = cache.get(attachmentObj);
            if (cacheattachment == null) {
                cacheattachment = AttachmentRealmProxy.insert(realm, attachmentObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.attachmentIndex, rowIndex, cacheattachment, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.verbosetech.yoohoo.models.Message.class);
        long tableNativePtr = table.getNativeTablePointer();
        MessageColumnInfo columnInfo = (MessageColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.Message.class);
        com.verbosetech.yoohoo.models.Message object = null;
        while (objects.hasNext()) {
            object = (com.verbosetech.yoohoo.models.Message) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$body = ((MessageRealmProxyInterface)object).realmGet$body();
                if (realmGet$body != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.bodyIndex, rowIndex, realmGet$body, false);
                }
                String realmGet$senderName = ((MessageRealmProxyInterface)object).realmGet$senderName();
                if (realmGet$senderName != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.senderNameIndex, rowIndex, realmGet$senderName, false);
                }
                String realmGet$senderId = ((MessageRealmProxyInterface)object).realmGet$senderId();
                if (realmGet$senderId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.senderIdIndex, rowIndex, realmGet$senderId, false);
                }
                String realmGet$recipientId = ((MessageRealmProxyInterface)object).realmGet$recipientId();
                if (realmGet$recipientId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.recipientIdIndex, rowIndex, realmGet$recipientId, false);
                }
                String realmGet$id = ((MessageRealmProxyInterface)object).realmGet$id();
                if (realmGet$id != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.dateIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$date(), false);
                Table.nativeSetLong(tableNativePtr, columnInfo.durationIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$duration(), false);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.deliveredIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$delivered(), false);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.sentIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$sent(), false);
                Table.nativeSetLong(tableNativePtr, columnInfo.attachmentTypeIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$attachmentType(), false);

                com.verbosetech.yoohoo.models.Attachment attachmentObj = ((MessageRealmProxyInterface) object).realmGet$attachment();
                if (attachmentObj != null) {
                    Long cacheattachment = cache.get(attachmentObj);
                    if (cacheattachment == null) {
                        cacheattachment = AttachmentRealmProxy.insert(realm, attachmentObj, cache);
                    }
                    table.setLink(columnInfo.attachmentIndex, rowIndex, cacheattachment, false);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.verbosetech.yoohoo.models.Message object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.verbosetech.yoohoo.models.Message.class);
        long tableNativePtr = table.getNativeTablePointer();
        MessageColumnInfo columnInfo = (MessageColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.Message.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$body = ((MessageRealmProxyInterface)object).realmGet$body();
        if (realmGet$body != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.bodyIndex, rowIndex, realmGet$body, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.bodyIndex, rowIndex, false);
        }
        String realmGet$senderName = ((MessageRealmProxyInterface)object).realmGet$senderName();
        if (realmGet$senderName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.senderNameIndex, rowIndex, realmGet$senderName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.senderNameIndex, rowIndex, false);
        }
        String realmGet$senderId = ((MessageRealmProxyInterface)object).realmGet$senderId();
        if (realmGet$senderId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.senderIdIndex, rowIndex, realmGet$senderId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.senderIdIndex, rowIndex, false);
        }
        String realmGet$recipientId = ((MessageRealmProxyInterface)object).realmGet$recipientId();
        if (realmGet$recipientId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.recipientIdIndex, rowIndex, realmGet$recipientId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.recipientIdIndex, rowIndex, false);
        }
        String realmGet$id = ((MessageRealmProxyInterface)object).realmGet$id();
        if (realmGet$id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.idIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.dateIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$date(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.durationIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$duration(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.deliveredIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$delivered(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.sentIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$sent(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.attachmentTypeIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$attachmentType(), false);

        com.verbosetech.yoohoo.models.Attachment attachmentObj = ((MessageRealmProxyInterface) object).realmGet$attachment();
        if (attachmentObj != null) {
            Long cacheattachment = cache.get(attachmentObj);
            if (cacheattachment == null) {
                cacheattachment = AttachmentRealmProxy.insertOrUpdate(realm, attachmentObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.attachmentIndex, rowIndex, cacheattachment, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.attachmentIndex, rowIndex);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.verbosetech.yoohoo.models.Message.class);
        long tableNativePtr = table.getNativeTablePointer();
        MessageColumnInfo columnInfo = (MessageColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.Message.class);
        com.verbosetech.yoohoo.models.Message object = null;
        while (objects.hasNext()) {
            object = (com.verbosetech.yoohoo.models.Message) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$body = ((MessageRealmProxyInterface)object).realmGet$body();
                if (realmGet$body != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.bodyIndex, rowIndex, realmGet$body, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.bodyIndex, rowIndex, false);
                }
                String realmGet$senderName = ((MessageRealmProxyInterface)object).realmGet$senderName();
                if (realmGet$senderName != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.senderNameIndex, rowIndex, realmGet$senderName, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.senderNameIndex, rowIndex, false);
                }
                String realmGet$senderId = ((MessageRealmProxyInterface)object).realmGet$senderId();
                if (realmGet$senderId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.senderIdIndex, rowIndex, realmGet$senderId, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.senderIdIndex, rowIndex, false);
                }
                String realmGet$recipientId = ((MessageRealmProxyInterface)object).realmGet$recipientId();
                if (realmGet$recipientId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.recipientIdIndex, rowIndex, realmGet$recipientId, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.recipientIdIndex, rowIndex, false);
                }
                String realmGet$id = ((MessageRealmProxyInterface)object).realmGet$id();
                if (realmGet$id != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.idIndex, rowIndex, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.dateIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$date(), false);
                Table.nativeSetLong(tableNativePtr, columnInfo.durationIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$duration(), false);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.deliveredIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$delivered(), false);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.sentIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$sent(), false);
                Table.nativeSetLong(tableNativePtr, columnInfo.attachmentTypeIndex, rowIndex, ((MessageRealmProxyInterface)object).realmGet$attachmentType(), false);

                com.verbosetech.yoohoo.models.Attachment attachmentObj = ((MessageRealmProxyInterface) object).realmGet$attachment();
                if (attachmentObj != null) {
                    Long cacheattachment = cache.get(attachmentObj);
                    if (cacheattachment == null) {
                        cacheattachment = AttachmentRealmProxy.insertOrUpdate(realm, attachmentObj, cache);
                    }
                    Table.nativeSetLink(tableNativePtr, columnInfo.attachmentIndex, rowIndex, cacheattachment, false);
                } else {
                    Table.nativeNullifyLink(tableNativePtr, columnInfo.attachmentIndex, rowIndex);
                }
            }
        }
    }

    public static com.verbosetech.yoohoo.models.Message createDetachedCopy(com.verbosetech.yoohoo.models.Message realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.verbosetech.yoohoo.models.Message unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.verbosetech.yoohoo.models.Message)cachedObject.object;
            } else {
                unmanagedObject = (com.verbosetech.yoohoo.models.Message)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.verbosetech.yoohoo.models.Message();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        }
        ((MessageRealmProxyInterface) unmanagedObject).realmSet$body(((MessageRealmProxyInterface) realmObject).realmGet$body());
        ((MessageRealmProxyInterface) unmanagedObject).realmSet$senderName(((MessageRealmProxyInterface) realmObject).realmGet$senderName());
        ((MessageRealmProxyInterface) unmanagedObject).realmSet$senderId(((MessageRealmProxyInterface) realmObject).realmGet$senderId());
        ((MessageRealmProxyInterface) unmanagedObject).realmSet$recipientId(((MessageRealmProxyInterface) realmObject).realmGet$recipientId());
        ((MessageRealmProxyInterface) unmanagedObject).realmSet$id(((MessageRealmProxyInterface) realmObject).realmGet$id());
        ((MessageRealmProxyInterface) unmanagedObject).realmSet$date(((MessageRealmProxyInterface) realmObject).realmGet$date());
        ((MessageRealmProxyInterface) unmanagedObject).realmSet$duration(((MessageRealmProxyInterface) realmObject).realmGet$duration());
        ((MessageRealmProxyInterface) unmanagedObject).realmSet$delivered(((MessageRealmProxyInterface) realmObject).realmGet$delivered());
        ((MessageRealmProxyInterface) unmanagedObject).realmSet$sent(((MessageRealmProxyInterface) realmObject).realmGet$sent());
        ((MessageRealmProxyInterface) unmanagedObject).realmSet$attachmentType(((MessageRealmProxyInterface) realmObject).realmGet$attachmentType());

        // Deep copy of attachment
        ((MessageRealmProxyInterface) unmanagedObject).realmSet$attachment(AttachmentRealmProxy.createDetachedCopy(((MessageRealmProxyInterface) realmObject).realmGet$attachment(), currentDepth + 1, maxDepth, cache));
        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Message = [");
        stringBuilder.append("{body:");
        stringBuilder.append(realmGet$body() != null ? realmGet$body() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{senderName:");
        stringBuilder.append(realmGet$senderName() != null ? realmGet$senderName() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{senderId:");
        stringBuilder.append(realmGet$senderId() != null ? realmGet$senderId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{recipientId:");
        stringBuilder.append(realmGet$recipientId() != null ? realmGet$recipientId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id() != null ? realmGet$id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{date:");
        stringBuilder.append(realmGet$date());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{duration:");
        stringBuilder.append(realmGet$duration());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{delivered:");
        stringBuilder.append(realmGet$delivered());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{sent:");
        stringBuilder.append(realmGet$sent());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{attachmentType:");
        stringBuilder.append(realmGet$attachmentType());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{attachment:");
        stringBuilder.append(realmGet$attachment() != null ? "Attachment" : "null");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

}
