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

public class GroupRealmProxy extends com.verbosetech.yoohoo.models.Group
    implements RealmObjectProxy, GroupRealmProxyInterface {

    static final class GroupColumnInfo extends ColumnInfo
        implements Cloneable {

        public long idIndex;
        public long nameIndex;
        public long statusIndex;
        public long imageIndex;
        public long userIdsIndex;

        GroupColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(5);
            this.idIndex = getValidColumnIndex(path, table, "Group", "id");
            indicesMap.put("id", this.idIndex);
            this.nameIndex = getValidColumnIndex(path, table, "Group", "name");
            indicesMap.put("name", this.nameIndex);
            this.statusIndex = getValidColumnIndex(path, table, "Group", "status");
            indicesMap.put("status", this.statusIndex);
            this.imageIndex = getValidColumnIndex(path, table, "Group", "image");
            indicesMap.put("image", this.imageIndex);
            this.userIdsIndex = getValidColumnIndex(path, table, "Group", "userIds");
            indicesMap.put("userIds", this.userIdsIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final GroupColumnInfo otherInfo = (GroupColumnInfo) other;
            this.idIndex = otherInfo.idIndex;
            this.nameIndex = otherInfo.nameIndex;
            this.statusIndex = otherInfo.statusIndex;
            this.imageIndex = otherInfo.imageIndex;
            this.userIdsIndex = otherInfo.userIdsIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final GroupColumnInfo clone() {
            return (GroupColumnInfo) super.clone();
        }

    }
    private GroupColumnInfo columnInfo;
    private ProxyState<com.verbosetech.yoohoo.models.Group> proxyState;
    private RealmList<com.verbosetech.yoohoo.models.MyString> userIdsRealmList;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("id");
        fieldNames.add("name");
        fieldNames.add("status");
        fieldNames.add("image");
        fieldNames.add("userIds");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    GroupRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (GroupColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.verbosetech.yoohoo.models.Group>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
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
    public String realmGet$status() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.statusIndex);
    }

    @Override
    public void realmSet$status(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.statusIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.statusIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.statusIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.statusIndex, value);
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
    public RealmList<com.verbosetech.yoohoo.models.MyString> realmGet$userIds() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (userIdsRealmList != null) {
            return userIdsRealmList;
        } else {
            LinkView linkView = proxyState.getRow$realm().getLinkList(columnInfo.userIdsIndex);
            userIdsRealmList = new RealmList<com.verbosetech.yoohoo.models.MyString>(com.verbosetech.yoohoo.models.MyString.class, linkView, proxyState.getRealm$realm());
            return userIdsRealmList;
        }
    }

    @Override
    public void realmSet$userIds(RealmList<com.verbosetech.yoohoo.models.MyString> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("userIds")) {
                return;
            }
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.verbosetech.yoohoo.models.MyString> original = value;
                value = new RealmList<com.verbosetech.yoohoo.models.MyString>();
                for (com.verbosetech.yoohoo.models.MyString item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        LinkView links = proxyState.getRow$realm().getLinkList(columnInfo.userIdsIndex);
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

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("Group")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("Group");
            realmObjectSchema.add("id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("status", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("image", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            if (!realmSchema.contains("MyString")) {
                MyStringRealmProxy.createRealmObjectSchema(realmSchema);
            }
            realmObjectSchema.add("userIds", RealmFieldType.LIST, realmSchema.get("MyString"));
            return realmObjectSchema;
        }
        return realmSchema.get("Group");
    }

    public static GroupColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (!sharedRealm.hasTable("class_Group")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'Group' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_Group");
        final long columnCount = table.getColumnCount();
        if (columnCount != 5) {
            if (columnCount < 5) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 5 but was " + columnCount);
            }
            if (allowExtraColumns) {
                RealmLog.debug("Field count is more than expected - expected 5 but was %1$d", columnCount);
            } else {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 5 but was " + columnCount);
            }
        }
        Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
        for (long i = 0; i < columnCount; i++) {
            columnTypes.put(table.getColumnName(i), table.getColumnType(i));
        }

        final GroupColumnInfo columnInfo = new GroupColumnInfo(sharedRealm.getPath(), table);

        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
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
        if (!columnTypes.containsKey("name")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'name' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("name") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'name' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.nameIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'name' is required. Either set @Required to field 'name' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("status")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'status' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("status") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'status' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.statusIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'status' is required. Either set @Required to field 'status' or migrate using RealmObjectSchema.setNullable().");
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
        if (!columnTypes.containsKey("userIds")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'userIds'");
        }
        if (columnTypes.get("userIds") != RealmFieldType.LIST) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'MyString' for field 'userIds'");
        }
        if (!sharedRealm.hasTable("class_MyString")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_MyString' for field 'userIds'");
        }
        Table table_4 = sharedRealm.getTable("class_MyString");
        if (!table.getLinkTarget(columnInfo.userIdsIndex).hasSameSchema(table_4)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmList type for field 'userIds': '" + table.getLinkTarget(columnInfo.userIdsIndex).getName() + "' expected - was '" + table_4.getName() + "'");
        }

        return columnInfo;
    }

    public static String getTableName() {
        return "class_Group";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.verbosetech.yoohoo.models.Group createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        if (json.has("userIds")) {
            excludeFields.add("userIds");
        }
        com.verbosetech.yoohoo.models.Group obj = realm.createObjectInternal(com.verbosetech.yoohoo.models.Group.class, true, excludeFields);
        if (json.has("id")) {
            if (json.isNull("id")) {
                ((GroupRealmProxyInterface) obj).realmSet$id(null);
            } else {
                ((GroupRealmProxyInterface) obj).realmSet$id((String) json.getString("id"));
            }
        }
        if (json.has("name")) {
            if (json.isNull("name")) {
                ((GroupRealmProxyInterface) obj).realmSet$name(null);
            } else {
                ((GroupRealmProxyInterface) obj).realmSet$name((String) json.getString("name"));
            }
        }
        if (json.has("status")) {
            if (json.isNull("status")) {
                ((GroupRealmProxyInterface) obj).realmSet$status(null);
            } else {
                ((GroupRealmProxyInterface) obj).realmSet$status((String) json.getString("status"));
            }
        }
        if (json.has("image")) {
            if (json.isNull("image")) {
                ((GroupRealmProxyInterface) obj).realmSet$image(null);
            } else {
                ((GroupRealmProxyInterface) obj).realmSet$image((String) json.getString("image"));
            }
        }
        if (json.has("userIds")) {
            if (json.isNull("userIds")) {
                ((GroupRealmProxyInterface) obj).realmSet$userIds(null);
            } else {
                ((GroupRealmProxyInterface) obj).realmGet$userIds().clear();
                JSONArray array = json.getJSONArray("userIds");
                for (int i = 0; i < array.length(); i++) {
                    com.verbosetech.yoohoo.models.MyString item = MyStringRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    ((GroupRealmProxyInterface) obj).realmGet$userIds().add(item);
                }
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.verbosetech.yoohoo.models.Group createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        com.verbosetech.yoohoo.models.Group obj = new com.verbosetech.yoohoo.models.Group();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("id")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((GroupRealmProxyInterface) obj).realmSet$id(null);
                } else {
                    ((GroupRealmProxyInterface) obj).realmSet$id((String) reader.nextString());
                }
            } else if (name.equals("name")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((GroupRealmProxyInterface) obj).realmSet$name(null);
                } else {
                    ((GroupRealmProxyInterface) obj).realmSet$name((String) reader.nextString());
                }
            } else if (name.equals("status")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((GroupRealmProxyInterface) obj).realmSet$status(null);
                } else {
                    ((GroupRealmProxyInterface) obj).realmSet$status((String) reader.nextString());
                }
            } else if (name.equals("image")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((GroupRealmProxyInterface) obj).realmSet$image(null);
                } else {
                    ((GroupRealmProxyInterface) obj).realmSet$image((String) reader.nextString());
                }
            } else if (name.equals("userIds")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((GroupRealmProxyInterface) obj).realmSet$userIds(null);
                } else {
                    ((GroupRealmProxyInterface) obj).realmSet$userIds(new RealmList<com.verbosetech.yoohoo.models.MyString>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.verbosetech.yoohoo.models.MyString item = MyStringRealmProxy.createUsingJsonStream(realm, reader);
                        ((GroupRealmProxyInterface) obj).realmGet$userIds().add(item);
                    }
                    reader.endArray();
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static com.verbosetech.yoohoo.models.Group copyOrUpdate(Realm realm, com.verbosetech.yoohoo.models.Group object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.verbosetech.yoohoo.models.Group) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static com.verbosetech.yoohoo.models.Group copy(Realm realm, com.verbosetech.yoohoo.models.Group newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.verbosetech.yoohoo.models.Group) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            com.verbosetech.yoohoo.models.Group realmObject = realm.createObjectInternal(com.verbosetech.yoohoo.models.Group.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((GroupRealmProxyInterface) realmObject).realmSet$id(((GroupRealmProxyInterface) newObject).realmGet$id());
            ((GroupRealmProxyInterface) realmObject).realmSet$name(((GroupRealmProxyInterface) newObject).realmGet$name());
            ((GroupRealmProxyInterface) realmObject).realmSet$status(((GroupRealmProxyInterface) newObject).realmGet$status());
            ((GroupRealmProxyInterface) realmObject).realmSet$image(((GroupRealmProxyInterface) newObject).realmGet$image());

            RealmList<com.verbosetech.yoohoo.models.MyString> userIdsList = ((GroupRealmProxyInterface) newObject).realmGet$userIds();
            if (userIdsList != null) {
                RealmList<com.verbosetech.yoohoo.models.MyString> userIdsRealmList = ((GroupRealmProxyInterface) realmObject).realmGet$userIds();
                for (int i = 0; i < userIdsList.size(); i++) {
                    com.verbosetech.yoohoo.models.MyString userIdsItem = userIdsList.get(i);
                    com.verbosetech.yoohoo.models.MyString cacheuserIds = (com.verbosetech.yoohoo.models.MyString) cache.get(userIdsItem);
                    if (cacheuserIds != null) {
                        userIdsRealmList.add(cacheuserIds);
                    } else {
                        userIdsRealmList.add(MyStringRealmProxy.copyOrUpdate(realm, userIdsList.get(i), update, cache));
                    }
                }
            }

            return realmObject;
        }
    }

    public static long insert(Realm realm, com.verbosetech.yoohoo.models.Group object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.verbosetech.yoohoo.models.Group.class);
        long tableNativePtr = table.getNativeTablePointer();
        GroupColumnInfo columnInfo = (GroupColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.Group.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$id = ((GroupRealmProxyInterface)object).realmGet$id();
        if (realmGet$id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id, false);
        }
        String realmGet$name = ((GroupRealmProxyInterface)object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        }
        String realmGet$status = ((GroupRealmProxyInterface)object).realmGet$status();
        if (realmGet$status != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.statusIndex, rowIndex, realmGet$status, false);
        }
        String realmGet$image = ((GroupRealmProxyInterface)object).realmGet$image();
        if (realmGet$image != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageIndex, rowIndex, realmGet$image, false);
        }

        RealmList<com.verbosetech.yoohoo.models.MyString> userIdsList = ((GroupRealmProxyInterface) object).realmGet$userIds();
        if (userIdsList != null) {
            long userIdsNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.userIdsIndex, rowIndex);
            for (com.verbosetech.yoohoo.models.MyString userIdsItem : userIdsList) {
                Long cacheItemIndexuserIds = cache.get(userIdsItem);
                if (cacheItemIndexuserIds == null) {
                    cacheItemIndexuserIds = MyStringRealmProxy.insert(realm, userIdsItem, cache);
                }
                LinkView.nativeAdd(userIdsNativeLinkViewPtr, cacheItemIndexuserIds);
            }
        }

        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.verbosetech.yoohoo.models.Group.class);
        long tableNativePtr = table.getNativeTablePointer();
        GroupColumnInfo columnInfo = (GroupColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.Group.class);
        com.verbosetech.yoohoo.models.Group object = null;
        while (objects.hasNext()) {
            object = (com.verbosetech.yoohoo.models.Group) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$id = ((GroupRealmProxyInterface)object).realmGet$id();
                if (realmGet$id != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id, false);
                }
                String realmGet$name = ((GroupRealmProxyInterface)object).realmGet$name();
                if (realmGet$name != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
                }
                String realmGet$status = ((GroupRealmProxyInterface)object).realmGet$status();
                if (realmGet$status != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.statusIndex, rowIndex, realmGet$status, false);
                }
                String realmGet$image = ((GroupRealmProxyInterface)object).realmGet$image();
                if (realmGet$image != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.imageIndex, rowIndex, realmGet$image, false);
                }

                RealmList<com.verbosetech.yoohoo.models.MyString> userIdsList = ((GroupRealmProxyInterface) object).realmGet$userIds();
                if (userIdsList != null) {
                    long userIdsNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.userIdsIndex, rowIndex);
                    for (com.verbosetech.yoohoo.models.MyString userIdsItem : userIdsList) {
                        Long cacheItemIndexuserIds = cache.get(userIdsItem);
                        if (cacheItemIndexuserIds == null) {
                            cacheItemIndexuserIds = MyStringRealmProxy.insert(realm, userIdsItem, cache);
                        }
                        LinkView.nativeAdd(userIdsNativeLinkViewPtr, cacheItemIndexuserIds);
                    }
                }

            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.verbosetech.yoohoo.models.Group object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.verbosetech.yoohoo.models.Group.class);
        long tableNativePtr = table.getNativeTablePointer();
        GroupColumnInfo columnInfo = (GroupColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.Group.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$id = ((GroupRealmProxyInterface)object).realmGet$id();
        if (realmGet$id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.idIndex, rowIndex, false);
        }
        String realmGet$name = ((GroupRealmProxyInterface)object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
        }
        String realmGet$status = ((GroupRealmProxyInterface)object).realmGet$status();
        if (realmGet$status != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.statusIndex, rowIndex, realmGet$status, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.statusIndex, rowIndex, false);
        }
        String realmGet$image = ((GroupRealmProxyInterface)object).realmGet$image();
        if (realmGet$image != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.imageIndex, rowIndex, realmGet$image, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.imageIndex, rowIndex, false);
        }

        long userIdsNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.userIdsIndex, rowIndex);
        LinkView.nativeClear(userIdsNativeLinkViewPtr);
        RealmList<com.verbosetech.yoohoo.models.MyString> userIdsList = ((GroupRealmProxyInterface) object).realmGet$userIds();
        if (userIdsList != null) {
            for (com.verbosetech.yoohoo.models.MyString userIdsItem : userIdsList) {
                Long cacheItemIndexuserIds = cache.get(userIdsItem);
                if (cacheItemIndexuserIds == null) {
                    cacheItemIndexuserIds = MyStringRealmProxy.insertOrUpdate(realm, userIdsItem, cache);
                }
                LinkView.nativeAdd(userIdsNativeLinkViewPtr, cacheItemIndexuserIds);
            }
        }

        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.verbosetech.yoohoo.models.Group.class);
        long tableNativePtr = table.getNativeTablePointer();
        GroupColumnInfo columnInfo = (GroupColumnInfo) realm.schema.getColumnInfo(com.verbosetech.yoohoo.models.Group.class);
        com.verbosetech.yoohoo.models.Group object = null;
        while (objects.hasNext()) {
            object = (com.verbosetech.yoohoo.models.Group) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$id = ((GroupRealmProxyInterface)object).realmGet$id();
                if (realmGet$id != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.idIndex, rowIndex, realmGet$id, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.idIndex, rowIndex, false);
                }
                String realmGet$name = ((GroupRealmProxyInterface)object).realmGet$name();
                if (realmGet$name != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
                }
                String realmGet$status = ((GroupRealmProxyInterface)object).realmGet$status();
                if (realmGet$status != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.statusIndex, rowIndex, realmGet$status, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.statusIndex, rowIndex, false);
                }
                String realmGet$image = ((GroupRealmProxyInterface)object).realmGet$image();
                if (realmGet$image != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.imageIndex, rowIndex, realmGet$image, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.imageIndex, rowIndex, false);
                }

                long userIdsNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.userIdsIndex, rowIndex);
                LinkView.nativeClear(userIdsNativeLinkViewPtr);
                RealmList<com.verbosetech.yoohoo.models.MyString> userIdsList = ((GroupRealmProxyInterface) object).realmGet$userIds();
                if (userIdsList != null) {
                    for (com.verbosetech.yoohoo.models.MyString userIdsItem : userIdsList) {
                        Long cacheItemIndexuserIds = cache.get(userIdsItem);
                        if (cacheItemIndexuserIds == null) {
                            cacheItemIndexuserIds = MyStringRealmProxy.insertOrUpdate(realm, userIdsItem, cache);
                        }
                        LinkView.nativeAdd(userIdsNativeLinkViewPtr, cacheItemIndexuserIds);
                    }
                }

            }
        }
    }

    public static com.verbosetech.yoohoo.models.Group createDetachedCopy(com.verbosetech.yoohoo.models.Group realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.verbosetech.yoohoo.models.Group unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.verbosetech.yoohoo.models.Group)cachedObject.object;
            } else {
                unmanagedObject = (com.verbosetech.yoohoo.models.Group)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.verbosetech.yoohoo.models.Group();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        }
        ((GroupRealmProxyInterface) unmanagedObject).realmSet$id(((GroupRealmProxyInterface) realmObject).realmGet$id());
        ((GroupRealmProxyInterface) unmanagedObject).realmSet$name(((GroupRealmProxyInterface) realmObject).realmGet$name());
        ((GroupRealmProxyInterface) unmanagedObject).realmSet$status(((GroupRealmProxyInterface) realmObject).realmGet$status());
        ((GroupRealmProxyInterface) unmanagedObject).realmSet$image(((GroupRealmProxyInterface) realmObject).realmGet$image());

        // Deep copy of userIds
        if (currentDepth == maxDepth) {
            ((GroupRealmProxyInterface) unmanagedObject).realmSet$userIds(null);
        } else {
            RealmList<com.verbosetech.yoohoo.models.MyString> manageduserIdsList = ((GroupRealmProxyInterface) realmObject).realmGet$userIds();
            RealmList<com.verbosetech.yoohoo.models.MyString> unmanageduserIdsList = new RealmList<com.verbosetech.yoohoo.models.MyString>();
            ((GroupRealmProxyInterface) unmanagedObject).realmSet$userIds(unmanageduserIdsList);
            int nextDepth = currentDepth + 1;
            int size = manageduserIdsList.size();
            for (int i = 0; i < size; i++) {
                com.verbosetech.yoohoo.models.MyString item = MyStringRealmProxy.createDetachedCopy(manageduserIdsList.get(i), nextDepth, maxDepth, cache);
                unmanageduserIdsList.add(item);
            }
        }
        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Group = [");
        stringBuilder.append("{id:");
        stringBuilder.append(realmGet$id() != null ? realmGet$id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{name:");
        stringBuilder.append(realmGet$name() != null ? realmGet$name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{status:");
        stringBuilder.append(realmGet$status() != null ? realmGet$status() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{image:");
        stringBuilder.append(realmGet$image() != null ? realmGet$image() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{userIds:");
        stringBuilder.append("RealmList<MyString>[").append(realmGet$userIds().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

}
