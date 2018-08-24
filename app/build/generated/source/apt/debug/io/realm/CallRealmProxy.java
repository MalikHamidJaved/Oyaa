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

public class CallRealmProxy extends com.verbosetech.yoohoo.models.Call
    implements RealmObjectProxy, CallRealmProxyInterface {

    static final class CallColumnInfo extends ColumnInfo
        implements Cloneable {

        public long imageIndex;
        public long idIndex;
        public long fromIndex;
        public long durationIndex;
        public long bodyIndex;
        public long senderNameIndex;
        public long senderIdIndex;
        public long recipientIdIndex;
        public long recipientNameIndex;
        public long dateIndex;
        public long deliveredIndex;
        public long sentIndex;
        public long attachmentTypeIndex;
        public long attachmentIndex;

        CallColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(14);
            this.imageIndex = getValidColumnIndex(path, table, "Call", "image");
            indicesMap.put("image", this.imageIndex);
            this.idIndex = getValidColumnIndex(path, table, "Call", "id");
            indicesMap.put("id", this.idIndex);
            this.fromIndex = getValidColumnIndex(path, table, "Call", "from");
            indicesMap.put("from", this.fromIndex);
            this.durationIndex = getValidColumnIndex(path, table, "Call", "duration");
            indicesMap.put("duration", this.durationIndex);
            this.bodyIndex = getValidColumnIndex(path, table, "Call", "body");
            indicesMap.put("body", this.bodyIndex);
            this.senderNameIndex = getValidColumnIndex(path, table, "Call", "senderName");
            indicesMap.put("senderName", this.senderNameIndex);
            this.senderIdIndex = getValidColumnIndex(path, table, "Call", "senderId");
            indicesMap.put("senderId", this.senderIdIndex);
            this.recipientIdIndex = getValidColumnIndex(path, table, "Call", "recipientId");
            indicesMap.put("recipientId", this.recipientIdIndex);
            this.recipientNameIndex = getValidColumnIndex(path, table, "Call", "recipientName");
            indicesMap.put("recipientName", this.recipientNameIndex);
            this.dateIndex = getValidColumnIndex(path, table, "Call", "date");
            indicesMap.put("date", this.dateIndex);
            this.deliveredIndex = getValidColumnIndex(path, table, "Call", "delivered");
            indicesMap.put("delivered", this.deliveredIndex);
            this.sentIndex = getValidColumnIndex(path, table, "Call", "sent");
            indicesMap.put("sent", this.sentIndex);
            this.attachmentTypeIndex = getValidColumnIndex(path, table, "Call", "attachmentType");
            indicesMap.put("attachmentType", this.attachmentTypeIndex);
            this.attachmentIndex = getValidColumnIndex(path, table, "Call", "attachment");
            indicesMap.put("attachment", this.attachmentIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final CallColumnInfo otherInfo = (CallColumnInfo) other;
            this.imageIndex = otherInfo.imageIndex;
            this.idIndex = otherInfo.idIndex;
            this.fromIndex = otherInfo.fromIndex;
            this.durationIndex = otherInfo.durationIndex;
            this.bodyIndex = otherInfo.bodyIndex;
            this.senderNameIndex = otherInfo.senderNameIndex;
            this.senderIdIndex = otherInfo.senderIdIndex;
            this.recipientIdIndex = otherInfo.recipientIdIndex;
            this.recipientNameIndex = otherInfo.recipientNameIndex;
            this.dateIndex = otherInfo.dateIndex;
            this.deliveredIndex = otherInfo.deliveredIndex;
            this.sentIndex = otherInfo.sentIndex;
            this.attachmentTypeIndex = otherInfo.attachmentTypeIndex;
            this.attachmentIndex = otherInfo.attachmentIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final CallColumnInfo clone() {
            return (CallColumnInfo) super.clone();
        }

    }
    private CallColumnInfo columnInfo;
    private ProxyState<com.verbosetech.yoohoo.models.Call> proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("image");
        fieldNames.add("id");
        fieldNames.add("from");
        fieldNames.add("duration");
        fieldNames.add("body");
        fieldNames.add("senderName");
        fieldNames.add("senderId");
        fieldNames.add("recipientId");
        fieldNames.add("recipientName");
        fieldNames.add("date");
        fieldNames.add("delivered");
        fieldNames.add("sent");
        fieldNames.add("attachmentType");
        fieldNames.add("attachment");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    CallRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (CallColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.verbosetech.yoohoo.models.Call>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$image() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.imageIndex);
    }

    @Override
    public void realmSet$image(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.imageIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.imageIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.imageIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.imageIndex, value);
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
    public String realmGet$from() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.fromIndex);
    }

    @Override
    public void realmSet$from(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.fromIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.fromIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.fromIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.fromIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$duration() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.durationIndex);
    }

    @Override
    public void realmSet$duration(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.durationIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.durationIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.durationIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.durationIndex, value);
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
    public String realmGet$recipientName() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.recipientNameIndex);
    }

    @Override
    public void realmSet$recipientName(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.recipientNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.recipientNameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.recipientNameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.recipientNameIndex, value);
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
        if (!realmSchema.contains("Call")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("Call");
            realmObjectSchema.add("image", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("from", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("duration", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("body", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("senderName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("senderId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("recipientId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("recipientName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("date", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            realmObjectSchema.add("delivered", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            realmObjectSchema.add("sent", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            realmObjectSchema.add("attachmentType", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            if (!realmSchema.contains("Attachment")) {
                AttachmentRealmProxy.createRealmObjectSchema(realmSchema);
            }
            realmObjectSchema.add("attachment", RealmFieldType.OBJECT, realmSchema.get("Attachment"));
            return realmObjectSchema;
        }
        return realmSchema.get("Call");
    }

    public static CallColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (!sharedRealm.hasTable("class_Call")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'Call' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_Call");
        final long columnCount = table.getColumnCount();
        if (columnCount != 14) {
            if (columnCount < 14) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 14 but was " + columnCount);
            }
            if (allowExtraColumns) {
                RealmLog.debug("Field count is more than expected - expected 14 but was %1$d", columnCount);
            } else {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 14 but was " + columnCount);
            }
        }
        Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
        for (long i = 0; i < columnCount; i++) {
            columnTypes.put(table.getColumnName(i), table.getColumnType(i));
        }

        final CallColumnInfo columnInfo = new CallColumnInfo(sharedRealm.getPath(), table);

        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }

        if (!columnTypes.containsKey("image")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'image' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("image") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'image' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.imageIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'image' is required. Either set @Required to field 'image' or migrate using RealmObjectSchema.setNullable().");
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
        if (!columnTypes.containsKey("from")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'from' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("from") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'from' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.fromIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'from' is required. Either set @Required to field 'from' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("duration")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'duration' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("duration") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'duration' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.durationIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'duration' is required. Either set @Required to field 'duration' or migrate using RealmObjectSchema.setNullable().");
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
        if (!columnTypes.containsKey("recipientName")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'recipientName' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("recipientName") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'recipientName' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.recipientNameIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'recipientName' is required. Either set @Required to field 'recipientName' or migrate using RealmObjectSchema.setNullable().");
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
        Table table_13 = sharedRealm.getTable("class_Attachment");
        if (!table.getLinkTarget(columnInfo.attachmentIndex).hasSameSchema(table_13)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmObject for field 'attachment': '" + table.getLinkTarget(columnInfo.attachmentIndex).getName() + "' expected - was '" + table_13.getName() + "'");
        }

        return columnInfo;
    }

    public static String getTableName() {
        return "class_Call";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.verbosetech.yoohoo.models.Call createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        if (json.has("attachment")) {
            excludeFields.add("attachment");
        }
        com.verbosetech.yoohoo.models.Call obj = realm.createObjectInternal(com.verbosetech.yoohoo.models.Call.class, true, excludeFields);
        if (json.has("image")) {
            if (json.isNull("image")) {
                ((CallRealmProxyInterface) obj).realmSet$image(null);
            } else {
                ((CallRealmProxyInterface) obj).realmSet$image((String) json.getString("image"));
            }
        }
        if (json.has("id")) {
            if (json.isNull("id")) {
                ((CallRealmProxyInterface) obj).realmSet$id(null);
            } else {
                ((CallRealmProxyInterface) obj).realmSet$id((String) json.getString("id"));
            }
        }
        if (json.has("from")) {
            if (json.isNull("from")) {
                ((CallRealmProxyInterface) obj).realmSet$from(null);
            } else {
                ((CallRealmProxyInterface) obj).realmSet$from((String) json.getString("from"));
            }
        }
        if (json.has("duration")) {
            if (json.isNull("duration")) {
                ((CallRealmProxyInterface) obj).realmSet$duration(null);
            } else {
                ((CallRealmProxyInterface) obj).realmSet$duration((String) json.getString("duration"));
            }
        }
        if (json.has("body")) {
            if (json.isNull("body")) {
                ((CallRealmProxyInterface) obj).realmSet$body(null);
            } else {
                ((CallRealmProxyInterface) obj).realmSet$body((String) json.getString("body"));
            }
        }
        if (json.has("senderName")) {
            if (json.isNull("senderName")) {
                ((CallRealmProxyInterface) obj).realmSet$senderName(null);
            } else {
                ((CallRealmProxyInterface) obj).realmSet$senderName((String) json.getString("senderName"));
            }
        }
        if (json.has("senderId")) {
            if (json.isNull("senderId")) {
                ((CallRealmProxyInterface) obj).realmSet$senderId(null);
            } else {
                ((CallRealmProxyInterface) obj).realmSet$senderId((String) json.getString("senderId"));
            }
        }
        if (json.has("recipientId")) {
            if (json.isNull("recipientId")) {
                ((CallRealmProxyInterface) obj).realmSet$recipientId(null);
            } else {
                ((CallRealmProxyInterface) obj).realmSet$recipientId((String) json.getString("recipientId"));
            }
        }
        if (json.has("recipientName")) {
            if (json.isNull("recipientName")) {
                ((CallRealmProxyInterface) obj).realmSet$recipientName(null);
            } else {
                ((CallRealmProxyInterface) obj).realmSet$recipientName((String) json.getString("recipientName"));
            }
        }
        if (json.has("date")) {
            if (json.isNull("date")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'date' to null.");
            } else {
                ((CallRealmProxyInterface) obj).realmSet$date((long) json.getLong("date"));
            }
        }
        if (json.has("delivered")) {
            if (json.isNull("delivered")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'delivered' to null.");
            } else {
                ((CallRealmProxyInterface) obj).realmSet$delivered((boolean) json.getBoolean("delivered"));
            }
        }
        if (json.has("sent")) {
            if (json.isNull("sent")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'sent' to null.");
            } else {
                ((CallRealmProxyInterface) obj).realmSet$sent((boolean) json.getBoolean("sent"));
            }
        }
        if (json.has("attachmentType")) {
            if (json.isNull("attachmentType")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'attachmentType' to null.");
            } else {
                ((CallRealmProxyInterface) obj).realmSet$attachmentType((int) json.getInt("attachmentType"));
            }
        }
        if (json.has("attachment")) {
            if (json.isNull("attachment")) {
                ((CallRealmProxyInterface) obj).realmSet$attachment(null);
            } else {
                com.verbosetech.yoohoo.models.Attachment attachmentObj = AttachmentRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("attachment"), update);
                ((CallRealmProxyInterface) obj).realmSet$attachment(attachmentObj);
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.verbosetech.yoohoo.models.Call createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        com.verbosetech.yoohoo.models.Call obj = new com.verbosetech.yoohoo.models.Call();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("image")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((CallRealmProxyInterface) obj).realmSet$image(null);
                } else {
                    ((CallRealmProxyInterface) obj).realmSet$image((String) reader.nextString());
                }
            } else if (name.equals("id")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((CallRealmProxyInterface) obj).realmSet$id(null);
                } else {
                    ((CallRealmProxyInterface) obj).realmSet$id((String) reader.nextString());
                }
            } else if (name.equals("from")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((CallRealmProxyInterface) obj).realmSet$from(null);
                } else {
                    ((CallRealmProxyInterface) obj).realmSet$from((String) reader.nextString());
                }
            } else if (name.equals("duration")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((CallRealmProxyInterface) obj).realmSet$duration(null);
                } else {
                    ((CallRealmProxyInterface) obj).realmSet$duration((String) reader.nextString());
                }
            } else if (name.equals("body")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((CallRealmProxyInterface) obj).realmSet$body(null);
                } else {
                    ((CallRealmProxyInterface) obj).realmSet$body((String) reader.nextString());
                }
            } else if (name.equals("senderName")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((CallRealmProxyInterface) obj).realmSet$senderName(null);
                } else {
                    ((CallRealmProxyInterface) obj).realmSet$senderName((String) reader.nextString());
                }
            } else if (name.equals("senderId")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((CallRealmProxyInterface) obj).realmSet$senderId(null);
                } else {
                    ((CallRealmProxyInterface) obj).realmSet$senderId((String) reader.nextString());
                }
            } else if (name.equals("recipientId")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((CallRealmProxyInterface) obj).realmSet$recipientId(null);
                } else {
                    ((CallRealmProxyInterface) obj).realmSet$recipientId((String) reader.nextString());
                }
            } else if (name.equals("recipientName")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((CallRealmProxyInterface) obj).realmSet$recipientName(null);
                } else {
                    ((CallRealmProxyInterface) obj).realmSet$recipientName((String) reader.nextString());
                }
            } else if (name.equals("date")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'date' to null.");
                } else {
                    ((CallRealmProxyInterface) obj).realmSet$date((long) reader.nextLong());
                }
            } else if (name.equals("delivered")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'delivered' to null.");
                } else {
                    ((CallRealmProxyInterface) obj).realmSet$delivered((boolean) reader.nextBoolean());
                }
            } else if (name.equals("sent")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'sent' to null.");
                } else {
                    ((CallRealmProxyInterface) obj).realmSet$sent((boolean) reader.nextBoolean());
                }
            } else if (name.equals("attachmentType")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'attachmentType' to null.");
                } else {
                    ((CallRealmProxyInterface) obj).realmSet$attachmentType((int) reader.nextInt());
                }
            } else if (name.equals("attachment")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((CallRealmProxyInterface) obj).realmSet$attachment(null);
                } else {
                    com.verbosetech.yoohoo.models.Attachment attachmentObj = AttachmentRealmProxy.createUsingJsonStream(realm, reader);
                    ((CallRealmProxyInterface) obj).realmSet$attachment(attachmentObj);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static com.verbosetech.yoohoo.models.Call copyOrUpdate(Realm realm, com.verbosetech.yoohoo.models.Call object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.verbosetech.yoohoo.models.Call) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static com.verbosetech.yoohoo.models.Call copy(Realm realm, com.verbosetech.yoohoo.models.Call newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.verbosetech.yoohoo.models.Call) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            com.verbosetech.yoohoo.models.Call realmObject = realm.createObjectInternal(com.verbosetech.yoohoo.models.Call.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((CallRealmProxyInterface) realmObject).realmSet$image(((CallRealmProxyInterface) newObject).realmGet$image());
            ((CallRealmProxyInterface) realmObject).realmSet$id(((CallRealmProxyInterface) newObject).realmGet$id());
            ((CallRealmProxyInterface) realmObject).realmSet$from(((CallRealmProxyInterface) newObject).realmGet$from());
            ((CallRealmProxyInterface) realmObject).realmSet$duration(((CallRealmProxyInterface) newObject).realmGet$duration());
            ((CallRealmProxyInterface) realmObject).realmSet$body(((CallRealmProxyInterface) newObject).realmGet$body());
            ((CallRealmProxyInterface) realmObject).realmSet$senderName(((CallRealmProxyInterface) newObject).realmGet$senderName());
            ((CallRealmProxyInterface) realmObject).realmSet$senderId(((CallRealmProxyInterface) newObject).realmGet$senderId());
            ((CallRealmProxyInterface) realmObject).realmSet$recipientId(((CallRealmProxyInterface) newObject).realmGet$recipientId());
            ((CallRealmProxyInterface) realmObject).realmSet$recipientName(((CallRealmProxyInterface) newObject).realmGet$recipientName());
            ((CallRealmProxyInterface) realmObject).realmSet$date(((CallRealmProxyInterface) newObject).realmGet$date());
            ((CallRealmProxyInterface) realmObject).realmSet$delivered(((CallRealmProxyInterface) newObject).realmGet$delivered());
            ((CallRealmProxyInterface) realmObject).realmSet$sent(((CallRealmProxyInterface) newObject).realmGet$sent());
            ((CallRealmProxyInterface) realmObject).realmSet$attachmentType(((CallRealmProxyInterface) newObject).realmGet$attachmentType());

            com.verbosetech.yoohoo.models.Attachment attachmentObj = ((CallRealmProxyInterface) newObject).realmGet$attachment();
            if (attachmentObj != null) {
                com.verbosetech.yoohoo.models.Attachment cacheattachment = (com.verbosetech.yoohoo.models.Attachment) cache.get(attachmentObj);
                if (cacheattachment != null) {
                    ((CallRealmProxyInterface) realmObject).realmSet$attachment(cacheattachment);
                } else {
                    ((CallRealmProxyInterface) realmObject).realmSet$attachment(AttachmentRealmProxy.copyOrUpdate(realm, attachmentObj, update, cache));
                }
            } else {
                ((CallRealmProxyInterface) realmObject).realmSet$attachment(null);
            }
            return realmObject;
        }
    }

    public static long insert(Realm realm, com.verbosetech.yoohoo.models.Call object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.verbosetech.yoohoo.models.Call.class);
        long tableNativePtr = table.getNativeTablePointer();
        CallColumnInfo columnInfo = (CallColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.Call.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$image = ((CallRealmProxyInterface)object).realmGet$image();
        if (realmGet$image != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageIndex, rowIndex, realmGet$image, false);
        }
        String realmGet$id = ((CallRealmProxyInterface)object).realmGet$id();
        if (realmGet$id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id, false);
        }
        String realmGet$from = ((CallRealmProxyInterface)object).realmGet$from();
        if (realmGet$from != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.fromIndex, rowIndex, realmGet$from, false);
        }
        String realmGet$duration = ((CallRealmProxyInterface)object).realmGet$duration();
        if (realmGet$duration != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.durationIndex, rowIndex, realmGet$duration, false);
        }
        String realmGet$body = ((CallRealmProxyInterface)object).realmGet$body();
        if (realmGet$body != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.bodyIndex, rowIndex, realmGet$body, false);
        }
        String realmGet$senderName = ((CallRealmProxyInterface)object).realmGet$senderName();
        if (realmGet$senderName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.senderNameIndex, rowIndex, realmGet$senderName, false);
        }
        String realmGet$senderId = ((CallRealmProxyInterface)object).realmGet$senderId();
        if (realmGet$senderId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.senderIdIndex, rowIndex, realmGet$senderId, false);
        }
        String realmGet$recipientId = ((CallRealmProxyInterface)object).realmGet$recipientId();
        if (realmGet$recipientId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.recipientIdIndex, rowIndex, realmGet$recipientId, false);
        }
        String realmGet$recipientName = ((CallRealmProxyInterface)object).realmGet$recipientName();
        if (realmGet$recipientName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.recipientNameIndex, rowIndex, realmGet$recipientName, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.dateIndex, rowIndex, ((CallRealmProxyInterface)object).realmGet$date(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.deliveredIndex, rowIndex, ((CallRealmProxyInterface)object).realmGet$delivered(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.sentIndex, rowIndex, ((CallRealmProxyInterface)object).realmGet$sent(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.attachmentTypeIndex, rowIndex, ((CallRealmProxyInterface)object).realmGet$attachmentType(), false);

        com.verbosetech.yoohoo.models.Attachment attachmentObj = ((CallRealmProxyInterface) object).realmGet$attachment();
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
        Table table = realm.getTable(com.verbosetech.yoohoo.models.Call.class);
        long tableNativePtr = table.getNativeTablePointer();
        CallColumnInfo columnInfo = (CallColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.Call.class);
        com.verbosetech.yoohoo.models.Call object = null;
        while (objects.hasNext()) {
            object = (com.verbosetech.yoohoo.models.Call) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$image = ((CallRealmProxyInterface)object).realmGet$image();
                if (realmGet$image != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.imageIndex, rowIndex, realmGet$image, false);
                }
                String realmGet$id = ((CallRealmProxyInterface)object).realmGet$id();
                if (realmGet$id != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id, false);
                }
                String realmGet$from = ((CallRealmProxyInterface)object).realmGet$from();
                if (realmGet$from != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.fromIndex, rowIndex, realmGet$from, false);
                }
                String realmGet$duration = ((CallRealmProxyInterface)object).realmGet$duration();
                if (realmGet$duration != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.durationIndex, rowIndex, realmGet$duration, false);
                }
                String realmGet$body = ((CallRealmProxyInterface)object).realmGet$body();
                if (realmGet$body != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.bodyIndex, rowIndex, realmGet$body, false);
                }
                String realmGet$senderName = ((CallRealmProxyInterface)object).realmGet$senderName();
                if (realmGet$senderName != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.senderNameIndex, rowIndex, realmGet$senderName, false);
                }
                String realmGet$senderId = ((CallRealmProxyInterface)object).realmGet$senderId();
                if (realmGet$senderId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.senderIdIndex, rowIndex, realmGet$senderId, false);
                }
                String realmGet$recipientId = ((CallRealmProxyInterface)object).realmGet$recipientId();
                if (realmGet$recipientId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.recipientIdIndex, rowIndex, realmGet$recipientId, false);
                }
                String realmGet$recipientName = ((CallRealmProxyInterface)object).realmGet$recipientName();
                if (realmGet$recipientName != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.recipientNameIndex, rowIndex, realmGet$recipientName, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.dateIndex, rowIndex, ((CallRealmProxyInterface)object).realmGet$date(), false);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.deliveredIndex, rowIndex, ((CallRealmProxyInterface)object).realmGet$delivered(), false);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.sentIndex, rowIndex, ((CallRealmProxyInterface)object).realmGet$sent(), false);
                Table.nativeSetLong(tableNativePtr, columnInfo.attachmentTypeIndex, rowIndex, ((CallRealmProxyInterface)object).realmGet$attachmentType(), false);

                com.verbosetech.yoohoo.models.Attachment attachmentObj = ((CallRealmProxyInterface) object).realmGet$attachment();
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

    public static long insertOrUpdate(Realm realm, com.verbosetech.yoohoo.models.Call object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.verbosetech.yoohoo.models.Call.class);
        long tableNativePtr = table.getNativeTablePointer();
        CallColumnInfo columnInfo = (CallColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.Call.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$image = ((CallRealmProxyInterface)object).realmGet$image();
        if (realmGet$image != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageIndex, rowIndex, realmGet$image, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.imageIndex, rowIndex, false);
        }
        String realmGet$id = ((CallRealmProxyInterface)object).realmGet$id();
        if (realmGet$id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.idIndex, rowIndex, false);
        }
        String realmGet$from = ((CallRealmProxyInterface)object).realmGet$from();
        if (realmGet$from != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.fromIndex, rowIndex, realmGet$from, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.fromIndex, rowIndex, false);
        }
        String realmGet$duration = ((CallRealmProxyInterface)object).realmGet$duration();
        if (realmGet$duration != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.durationIndex, rowIndex, realmGet$duration, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.durationIndex, rowIndex, false);
        }
        String realmGet$body = ((CallRealmProxyInterface)object).realmGet$body();
        if (realmGet$body != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.bodyIndex, rowIndex, realmGet$body, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.bodyIndex, rowIndex, false);
        }
        String realmGet$senderName = ((CallRealmProxyInterface)object).realmGet$senderName();
        if (realmGet$senderName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.senderNameIndex, rowIndex, realmGet$senderName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.senderNameIndex, rowIndex, false);
        }
        String realmGet$senderId = ((CallRealmProxyInterface)object).realmGet$senderId();
        if (realmGet$senderId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.senderIdIndex, rowIndex, realmGet$senderId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.senderIdIndex, rowIndex, false);
        }
        String realmGet$recipientId = ((CallRealmProxyInterface)object).realmGet$recipientId();
        if (realmGet$recipientId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.recipientIdIndex, rowIndex, realmGet$recipientId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.recipientIdIndex, rowIndex, false);
        }
        String realmGet$recipientName = ((CallRealmProxyInterface)object).realmGet$recipientName();
        if (realmGet$recipientName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.recipientNameIndex, rowIndex, realmGet$recipientName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.recipientNameIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.dateIndex, rowIndex, ((CallRealmProxyInterface)object).realmGet$date(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.deliveredIndex, rowIndex, ((CallRealmProxyInterface)object).realmGet$delivered(), false);
        Table.nativeSetBoolean(tableNativePtr, columnInfo.sentIndex, rowIndex, ((CallRealmProxyInterface)object).realmGet$sent(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.attachmentTypeIndex, rowIndex, ((CallRealmProxyInterface)object).realmGet$attachmentType(), false);

        com.verbosetech.yoohoo.models.Attachment attachmentObj = ((CallRealmProxyInterface) object).realmGet$attachment();
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
        Table table = realm.getTable(com.verbosetech.yoohoo.models.Call.class);
        long tableNativePtr = table.getNativeTablePointer();
        CallColumnInfo columnInfo = (CallColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.Call.class);
        com.verbosetech.yoohoo.models.Call object = null;
        while (objects.hasNext()) {
            object = (com.verbosetech.yoohoo.models.Call) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$image = ((CallRealmProxyInterface)object).realmGet$image();
                if (realmGet$image != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.imageIndex, rowIndex, realmGet$image, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.imageIndex, rowIndex, false);
                }
                String realmGet$id = ((CallRealmProxyInterface)object).realmGet$id();
                if (realmGet$id != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.idIndex, rowIndex, false);
                }
                String realmGet$from = ((CallRealmProxyInterface)object).realmGet$from();
                if (realmGet$from != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.fromIndex, rowIndex, realmGet$from, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.fromIndex, rowIndex, false);
                }
                String realmGet$duration = ((CallRealmProxyInterface)object).realmGet$duration();
                if (realmGet$duration != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.durationIndex, rowIndex, realmGet$duration, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.durationIndex, rowIndex, false);
                }
                String realmGet$body = ((CallRealmProxyInterface)object).realmGet$body();
                if (realmGet$body != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.bodyIndex, rowIndex, realmGet$body, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.bodyIndex, rowIndex, false);
                }
                String realmGet$senderName = ((CallRealmProxyInterface)object).realmGet$senderName();
                if (realmGet$senderName != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.senderNameIndex, rowIndex, realmGet$senderName, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.senderNameIndex, rowIndex, false);
                }
                String realmGet$senderId = ((CallRealmProxyInterface)object).realmGet$senderId();
                if (realmGet$senderId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.senderIdIndex, rowIndex, realmGet$senderId, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.senderIdIndex, rowIndex, false);
                }
                String realmGet$recipientId = ((CallRealmProxyInterface)object).realmGet$recipientId();
                if (realmGet$recipientId != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.recipientIdIndex, rowIndex, realmGet$recipientId, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.recipientIdIndex, rowIndex, false);
                }
                String realmGet$recipientName = ((CallRealmProxyInterface)object).realmGet$recipientName();
                if (realmGet$recipientName != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.recipientNameIndex, rowIndex, realmGet$recipientName, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.recipientNameIndex, rowIndex, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.dateIndex, rowIndex, ((CallRealmProxyInterface)object).realmGet$date(), false);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.deliveredIndex, rowIndex, ((CallRealmProxyInterface)object).realmGet$delivered(), false);
                Table.nativeSetBoolean(tableNativePtr, columnInfo.sentIndex, rowIndex, ((CallRealmProxyInterface)object).realmGet$sent(), false);
                Table.nativeSetLong(tableNativePtr, columnInfo.attachmentTypeIndex, rowIndex, ((CallRealmProxyInterface)object).realmGet$attachmentType(), false);

                com.verbosetech.yoohoo.models.Attachment attachmentObj = ((CallRealmProxyInterface) object).realmGet$attachment();
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

    public static com.verbosetech.yoohoo.models.Call createDetachedCopy(com.verbosetech.yoohoo.models.Call realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.verbosetech.yoohoo.models.Call unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.verbosetech.yoohoo.models.Call)cachedObject.object;
            } else {
                unmanagedObject = (com.verbosetech.yoohoo.models.Call)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.verbosetech.yoohoo.models.Call();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        }
        ((CallRealmProxyInterface) unmanagedObject).realmSet$image(((CallRealmProxyInterface) realmObject).realmGet$image());
        ((CallRealmProxyInterface) unmanagedObject).realmSet$id(((CallRealmProxyInterface) realmObject).realmGet$id());
        ((CallRealmProxyInterface) unmanagedObject).realmSet$from(((CallRealmProxyInterface) realmObject).realmGet$from());
        ((CallRealmProxyInterface) unmanagedObject).realmSet$duration(((CallRealmProxyInterface) realmObject).realmGet$duration());
        ((CallRealmProxyInterface) unmanagedObject).realmSet$body(((CallRealmProxyInterface) realmObject).realmGet$body());
        ((CallRealmProxyInterface) unmanagedObject).realmSet$senderName(((CallRealmProxyInterface) realmObject).realmGet$senderName());
        ((CallRealmProxyInterface) unmanagedObject).realmSet$senderId(((CallRealmProxyInterface) realmObject).realmGet$senderId());
        ((CallRealmProxyInterface) unmanagedObject).realmSet$recipientId(((CallRealmProxyInterface) realmObject).realmGet$recipientId());
        ((CallRealmProxyInterface) unmanagedObject).realmSet$recipientName(((CallRealmProxyInterface) realmObject).realmGet$recipientName());
        ((CallRealmProxyInterface) unmanagedObject).realmSet$date(((CallRealmProxyInterface) realmObject).realmGet$date());
        ((CallRealmProxyInterface) unmanagedObject).realmSet$delivered(((CallRealmProxyInterface) realmObject).realmGet$delivered());
        ((CallRealmProxyInterface) unmanagedObject).realmSet$sent(((CallRealmProxyInterface) realmObject).realmGet$sent());
        ((CallRealmProxyInterface) unmanagedObject).realmSet$attachmentType(((CallRealmProxyInterface) realmObject).realmGet$attachmentType());

        // Deep copy of attachment
        ((CallRealmProxyInterface) unmanagedObject).realmSet$attachment(AttachmentRealmProxy.createDetachedCopy(((CallRealmProxyInterface) realmObject).realmGet$attachment(), currentDepth + 1, maxDepth, cache));
        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Call = [");
        stringBuilder.append("{image:");
        stringBuilder.append(realmGet$image() != null ? realmGet$image() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id() != null ? realmGet$id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{from:");
        stringBuilder.append(realmGet$from() != null ? realmGet$from() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{duration:");
        stringBuilder.append(realmGet$duration() != null ? realmGet$duration() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
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
        stringBuilder.append("{recipientName:");
        stringBuilder.append(realmGet$recipientName() != null ? realmGet$recipientName() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{date:");
        stringBuilder.append(realmGet$date());
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
        CallRealmProxy aCall = (CallRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aCall.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aCall.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aCall.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
