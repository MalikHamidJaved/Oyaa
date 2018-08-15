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

public class MyStringRealmProxy extends com.verbosetech.yoohoo.models.MyString
    implements RealmObjectProxy, MyStringRealmProxyInterface {

    static final class MyStringColumnInfo extends ColumnInfo
        implements Cloneable {

        public long stringIndex;

        MyStringColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(1);
            this.stringIndex = getValidColumnIndex(path, table, "MyString", "string");
            indicesMap.put("string", this.stringIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final MyStringColumnInfo otherInfo = (MyStringColumnInfo) other;
            this.stringIndex = otherInfo.stringIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final MyStringColumnInfo clone() {
            return (MyStringColumnInfo) super.clone();
        }

    }
    private MyStringColumnInfo columnInfo;
    private ProxyState<com.verbosetech.yoohoo.models.MyString> proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("string");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    MyStringRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (MyStringColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.verbosetech.yoohoo.models.MyString>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$string() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.stringIndex);
    }

    @Override
    public void realmSet$string(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.stringIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.stringIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.stringIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.stringIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("MyString")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("MyString");
            realmObjectSchema.add("string", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            return realmObjectSchema;
        }
        return realmSchema.get("MyString");
    }

    public static MyStringColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (!sharedRealm.hasTable("class_MyString")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'MyString' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_MyString");
        final long columnCount = table.getColumnCount();
        if (columnCount != 1) {
            if (columnCount < 1) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 1 but was " + columnCount);
            }
            if (allowExtraColumns) {
                RealmLog.debug("Field count is more than expected - expected 1 but was %1$d", columnCount);
            } else {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 1 but was " + columnCount);
            }
        }
        Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
        for (long i = 0; i < columnCount; i++) {
            columnTypes.put(table.getColumnName(i), table.getColumnType(i));
        }

        final MyStringColumnInfo columnInfo = new MyStringColumnInfo(sharedRealm.getPath(), table);

        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }

        if (!columnTypes.containsKey("string")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'string' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("string") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'string' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.stringIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'string' is required. Either set @Required to field 'string' or migrate using RealmObjectSchema.setNullable().");
        }

        return columnInfo;
    }

    public static String getTableName() {
        return "class_MyString";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.verbosetech.yoohoo.models.MyString createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.verbosetech.yoohoo.models.MyString obj = realm.createObjectInternal(com.verbosetech.yoohoo.models.MyString.class, true, excludeFields);
        if (json.has("string")) {
            if (json.isNull("string")) {
                ((MyStringRealmProxyInterface) obj).realmSet$string(null);
            } else {
                ((MyStringRealmProxyInterface) obj).realmSet$string((String) json.getString("string"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.verbosetech.yoohoo.models.MyString createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        com.verbosetech.yoohoo.models.MyString obj = new com.verbosetech.yoohoo.models.MyString();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("string")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MyStringRealmProxyInterface) obj).realmSet$string(null);
                } else {
                    ((MyStringRealmProxyInterface) obj).realmSet$string((String) reader.nextString());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static com.verbosetech.yoohoo.models.MyString copyOrUpdate(Realm realm, com.verbosetech.yoohoo.models.MyString object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.verbosetech.yoohoo.models.MyString) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static com.verbosetech.yoohoo.models.MyString copy(Realm realm, com.verbosetech.yoohoo.models.MyString newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.verbosetech.yoohoo.models.MyString) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            com.verbosetech.yoohoo.models.MyString realmObject = realm.createObjectInternal(com.verbosetech.yoohoo.models.MyString.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((MyStringRealmProxyInterface) realmObject).realmSet$string(((MyStringRealmProxyInterface) newObject).realmGet$string());
            return realmObject;
        }
    }

    public static long insert(Realm realm, com.verbosetech.yoohoo.models.MyString object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.verbosetech.yoohoo.models.MyString.class);
        long tableNativePtr = table.getNativeTablePointer();
        MyStringColumnInfo columnInfo = (MyStringColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.MyString.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$string = ((MyStringRealmProxyInterface)object).realmGet$string();
        if (realmGet$string != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.stringIndex, rowIndex, realmGet$string, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.verbosetech.yoohoo.models.MyString.class);
        long tableNativePtr = table.getNativeTablePointer();
        MyStringColumnInfo columnInfo = (MyStringColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.MyString.class);
        com.verbosetech.yoohoo.models.MyString object = null;
        while (objects.hasNext()) {
            object = (com.verbosetech.yoohoo.models.MyString) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$string = ((MyStringRealmProxyInterface)object).realmGet$string();
                if (realmGet$string != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.stringIndex, rowIndex, realmGet$string, false);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.verbosetech.yoohoo.models.MyString object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.verbosetech.yoohoo.models.MyString.class);
        long tableNativePtr = table.getNativeTablePointer();
        MyStringColumnInfo columnInfo = (MyStringColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.MyString.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$string = ((MyStringRealmProxyInterface)object).realmGet$string();
        if (realmGet$string != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.stringIndex, rowIndex, realmGet$string, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.stringIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.verbosetech.yoohoo.models.MyString.class);
        long tableNativePtr = table.getNativeTablePointer();
        MyStringColumnInfo columnInfo = (MyStringColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.MyString.class);
        com.verbosetech.yoohoo.models.MyString object = null;
        while (objects.hasNext()) {
            object = (com.verbosetech.yoohoo.models.MyString) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$string = ((MyStringRealmProxyInterface)object).realmGet$string();
                if (realmGet$string != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.stringIndex, rowIndex, realmGet$string, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.stringIndex, rowIndex, false);
                }
            }
        }
    }

    public static com.verbosetech.yoohoo.models.MyString createDetachedCopy(com.verbosetech.yoohoo.models.MyString realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.verbosetech.yoohoo.models.MyString unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.verbosetech.yoohoo.models.MyString)cachedObject.object;
            } else {
                unmanagedObject = (com.verbosetech.yoohoo.models.MyString)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.verbosetech.yoohoo.models.MyString();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        }
        ((MyStringRealmProxyInterface) unmanagedObject).realmSet$string(((MyStringRealmProxyInterface) realmObject).realmGet$string());
        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("MyString = [");
        stringBuilder.append("{string:");
        stringBuilder.append(realmGet$string() != null ? realmGet$string() : "null");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

}
