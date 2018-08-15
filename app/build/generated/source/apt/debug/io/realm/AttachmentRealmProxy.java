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

public class AttachmentRealmProxy extends com.verbosetech.yoohoo.models.Attachment
    implements RealmObjectProxy, AttachmentRealmProxyInterface {

    static final class AttachmentColumnInfo extends ColumnInfo
        implements Cloneable {

        public long nameIndex;
        public long dataIndex;
        public long urlIndex;
        public long bytesCountIndex;

        AttachmentColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(4);
            this.nameIndex = getValidColumnIndex(path, table, "Attachment", "name");
            indicesMap.put("name", this.nameIndex);
            this.dataIndex = getValidColumnIndex(path, table, "Attachment", "data");
            indicesMap.put("data", this.dataIndex);
            this.urlIndex = getValidColumnIndex(path, table, "Attachment", "url");
            indicesMap.put("url", this.urlIndex);
            this.bytesCountIndex = getValidColumnIndex(path, table, "Attachment", "bytesCount");
            indicesMap.put("bytesCount", this.bytesCountIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final AttachmentColumnInfo otherInfo = (AttachmentColumnInfo) other;
            this.nameIndex = otherInfo.nameIndex;
            this.dataIndex = otherInfo.dataIndex;
            this.urlIndex = otherInfo.urlIndex;
            this.bytesCountIndex = otherInfo.bytesCountIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final AttachmentColumnInfo clone() {
            return (AttachmentColumnInfo) super.clone();
        }

    }
    private AttachmentColumnInfo columnInfo;
    private ProxyState<com.verbosetech.yoohoo.models.Attachment> proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("name");
        fieldNames.add("data");
        fieldNames.add("url");
        fieldNames.add("bytesCount");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    AttachmentRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (AttachmentColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.verbosetech.yoohoo.models.Attachment>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.nameIndex);
    }

    @Override
    public void realmSet$name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.nameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.nameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.nameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.nameIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$data() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.dataIndex);
    }

    @Override
    public void realmSet$data(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dataIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.dataIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dataIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.dataIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$url() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.urlIndex);
    }

    @Override
    public void realmSet$url(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.urlIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.urlIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.urlIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.urlIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$bytesCount() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.bytesCountIndex);
    }

    @Override
    public void realmSet$bytesCount(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.bytesCountIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.bytesCountIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("Attachment")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("Attachment");
            realmObjectSchema.add("name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("data", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("url", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("bytesCount", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
            return realmObjectSchema;
        }
        return realmSchema.get("Attachment");
    }

    public static AttachmentColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (!sharedRealm.hasTable("class_Attachment")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'Attachment' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_Attachment");
        final long columnCount = table.getColumnCount();
        if (columnCount != 4) {
            if (columnCount < 4) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 4 but was " + columnCount);
            }
            if (allowExtraColumns) {
                RealmLog.debug("Field count is more than expected - expected 4 but was %1$d", columnCount);
            } else {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 4 but was " + columnCount);
            }
        }
        Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
        for (long i = 0; i < columnCount; i++) {
            columnTypes.put(table.getColumnName(i), table.getColumnType(i));
        }

        final AttachmentColumnInfo columnInfo = new AttachmentColumnInfo(sharedRealm.getPath(), table);

        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }

        if (!columnTypes.containsKey("name")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'name' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("name") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'name' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.nameIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'name' is required. Either set @Required to field 'name' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("data")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'data' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("data") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'data' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.dataIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'data' is required. Either set @Required to field 'data' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("url")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'url' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("url") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'url' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.urlIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'url' is required. Either set @Required to field 'url' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("bytesCount")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'bytesCount' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("bytesCount") != RealmFieldType.INTEGER) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'long' for field 'bytesCount' in existing Realm file.");
        }
        if (table.isColumnNullable(columnInfo.bytesCountIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'bytesCount' does support null values in the existing Realm file. Use corresponding boxed type for field 'bytesCount' or migrate using RealmObjectSchema.setNullable().");
        }

        return columnInfo;
    }

    public static String getTableName() {
        return "class_Attachment";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.verbosetech.yoohoo.models.Attachment createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.verbosetech.yoohoo.models.Attachment obj = realm.createObjectInternal(com.verbosetech.yoohoo.models.Attachment.class, true, excludeFields);
        if (json.has("name")) {
            if (json.isNull("name")) {
                ((AttachmentRealmProxyInterface) obj).realmSet$name(null);
            } else {
                ((AttachmentRealmProxyInterface) obj).realmSet$name((String) json.getString("name"));
            }
        }
        if (json.has("data")) {
            if (json.isNull("data")) {
                ((AttachmentRealmProxyInterface) obj).realmSet$data(null);
            } else {
                ((AttachmentRealmProxyInterface) obj).realmSet$data((String) json.getString("data"));
            }
        }
        if (json.has("url")) {
            if (json.isNull("url")) {
                ((AttachmentRealmProxyInterface) obj).realmSet$url(null);
            } else {
                ((AttachmentRealmProxyInterface) obj).realmSet$url((String) json.getString("url"));
            }
        }
        if (json.has("bytesCount")) {
            if (json.isNull("bytesCount")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'bytesCount' to null.");
            } else {
                ((AttachmentRealmProxyInterface) obj).realmSet$bytesCount((long) json.getLong("bytesCount"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.verbosetech.yoohoo.models.Attachment createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        com.verbosetech.yoohoo.models.Attachment obj = new com.verbosetech.yoohoo.models.Attachment();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("name")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((AttachmentRealmProxyInterface) obj).realmSet$name(null);
                } else {
                    ((AttachmentRealmProxyInterface) obj).realmSet$name((String) reader.nextString());
                }
            } else if (name.equals("data")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((AttachmentRealmProxyInterface) obj).realmSet$data(null);
                } else {
                    ((AttachmentRealmProxyInterface) obj).realmSet$data((String) reader.nextString());
                }
            } else if (name.equals("url")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((AttachmentRealmProxyInterface) obj).realmSet$url(null);
                } else {
                    ((AttachmentRealmProxyInterface) obj).realmSet$url((String) reader.nextString());
                }
            } else if (name.equals("bytesCount")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'bytesCount' to null.");
                } else {
                    ((AttachmentRealmProxyInterface) obj).realmSet$bytesCount((long) reader.nextLong());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static com.verbosetech.yoohoo.models.Attachment copyOrUpdate(Realm realm, com.verbosetech.yoohoo.models.Attachment object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.verbosetech.yoohoo.models.Attachment) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static com.verbosetech.yoohoo.models.Attachment copy(Realm realm, com.verbosetech.yoohoo.models.Attachment newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.verbosetech.yoohoo.models.Attachment) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            com.verbosetech.yoohoo.models.Attachment realmObject = realm.createObjectInternal(com.verbosetech.yoohoo.models.Attachment.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((AttachmentRealmProxyInterface) realmObject).realmSet$name(((AttachmentRealmProxyInterface) newObject).realmGet$name());
            ((AttachmentRealmProxyInterface) realmObject).realmSet$data(((AttachmentRealmProxyInterface) newObject).realmGet$data());
            ((AttachmentRealmProxyInterface) realmObject).realmSet$url(((AttachmentRealmProxyInterface) newObject).realmGet$url());
            ((AttachmentRealmProxyInterface) realmObject).realmSet$bytesCount(((AttachmentRealmProxyInterface) newObject).realmGet$bytesCount());
            return realmObject;
        }
    }

    public static long insert(Realm realm, com.verbosetech.yoohoo.models.Attachment object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.verbosetech.yoohoo.models.Attachment.class);
        long tableNativePtr = table.getNativeTablePointer();
        AttachmentColumnInfo columnInfo = (AttachmentColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.Attachment.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$name = ((AttachmentRealmProxyInterface)object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        }
        String realmGet$data = ((AttachmentRealmProxyInterface)object).realmGet$data();
        if (realmGet$data != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dataIndex, rowIndex, realmGet$data, false);
        }
        String realmGet$url = ((AttachmentRealmProxyInterface)object).realmGet$url();
        if (realmGet$url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.bytesCountIndex, rowIndex, ((AttachmentRealmProxyInterface)object).realmGet$bytesCount(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.verbosetech.yoohoo.models.Attachment.class);
        long tableNativePtr = table.getNativeTablePointer();
        AttachmentColumnInfo columnInfo = (AttachmentColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.Attachment.class);
        com.verbosetech.yoohoo.models.Attachment object = null;
        while (objects.hasNext()) {
            object = (com.verbosetech.yoohoo.models.Attachment) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$name = ((AttachmentRealmProxyInterface)object).realmGet$name();
                if (realmGet$name != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
                }
                String realmGet$data = ((AttachmentRealmProxyInterface)object).realmGet$data();
                if (realmGet$data != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.dataIndex, rowIndex, realmGet$data, false);
                }
                String realmGet$url = ((AttachmentRealmProxyInterface)object).realmGet$url();
                if (realmGet$url != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.bytesCountIndex, rowIndex, ((AttachmentRealmProxyInterface)object).realmGet$bytesCount(), false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.verbosetech.yoohoo.models.Attachment object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.verbosetech.yoohoo.models.Attachment.class);
        long tableNativePtr = table.getNativeTablePointer();
        AttachmentColumnInfo columnInfo = (AttachmentColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.Attachment.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$name = ((AttachmentRealmProxyInterface)object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
        }
        String realmGet$data = ((AttachmentRealmProxyInterface)object).realmGet$data();
        if (realmGet$data != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.dataIndex, rowIndex, realmGet$data, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dataIndex, rowIndex, false);
        }
        String realmGet$url = ((AttachmentRealmProxyInterface)object).realmGet$url();
        if (realmGet$url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.urlIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.bytesCountIndex, rowIndex, ((AttachmentRealmProxyInterface)object).realmGet$bytesCount(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.verbosetech.yoohoo.models.Attachment.class);
        long tableNativePtr = table.getNativeTablePointer();
        AttachmentColumnInfo columnInfo = (AttachmentColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.Attachment.class);
        com.verbosetech.yoohoo.models.Attachment object = null;
        while (objects.hasNext()) {
            object = (com.verbosetech.yoohoo.models.Attachment) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$name = ((AttachmentRealmProxyInterface)object).realmGet$name();
                if (realmGet$name != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
                }
                String realmGet$data = ((AttachmentRealmProxyInterface)object).realmGet$data();
                if (realmGet$data != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.dataIndex, rowIndex, realmGet$data, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.dataIndex, rowIndex, false);
                }
                String realmGet$url = ((AttachmentRealmProxyInterface)object).realmGet$url();
                if (realmGet$url != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.urlIndex, rowIndex, realmGet$url, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.urlIndex, rowIndex, false);
                }
                Table.nativeSetLong(tableNativePtr, columnInfo.bytesCountIndex, rowIndex, ((AttachmentRealmProxyInterface)object).realmGet$bytesCount(), false);
            }
        }
    }

    public static com.verbosetech.yoohoo.models.Attachment createDetachedCopy(com.verbosetech.yoohoo.models.Attachment realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.verbosetech.yoohoo.models.Attachment unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.verbosetech.yoohoo.models.Attachment)cachedObject.object;
            } else {
                unmanagedObject = (com.verbosetech.yoohoo.models.Attachment)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.verbosetech.yoohoo.models.Attachment();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        }
        ((AttachmentRealmProxyInterface) unmanagedObject).realmSet$name(((AttachmentRealmProxyInterface) realmObject).realmGet$name());
        ((AttachmentRealmProxyInterface) unmanagedObject).realmSet$data(((AttachmentRealmProxyInterface) realmObject).realmGet$data());
        ((AttachmentRealmProxyInterface) unmanagedObject).realmSet$url(((AttachmentRealmProxyInterface) realmObject).realmGet$url());
        ((AttachmentRealmProxyInterface) unmanagedObject).realmSet$bytesCount(((AttachmentRealmProxyInterface) realmObject).realmGet$bytesCount());
        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Attachment = [");
        stringBuilder.append("{name:");
        stringBuilder.append(realmGet$name() != null ? realmGet$name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{data:");
        stringBuilder.append(realmGet$data() != null ? realmGet$data() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{url:");
        stringBuilder.append(realmGet$url() != null ? realmGet$url() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{bytesCount:");
        stringBuilder.append(realmGet$bytesCount());
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
        AttachmentRealmProxy aAttachment = (AttachmentRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aAttachment.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aAttachment.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aAttachment.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
