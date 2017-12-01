package com.nedap.archie.json;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.nedap.archie.base.OpenEHRBase;
import com.nedap.archie.rminfo.ArchieAOMInfoLookup;
import com.nedap.archie.rminfo.ArchieRMInfoLookup;
import com.nedap.archie.rminfo.ModelInfoLookup;
import com.nedap.archie.rminfo.ReflectionModelInfoLookup;
import com.nedap.archie.rminfo.RMTypeInfo;

/**
 * Class that handles naming of Archie RM and AOM objects for use in Jackson.
 *
 * The AOM class CComplexObject will get the type name "C_COMPLEX_OBJECT"
 * The RM class DvDateTime will get the type name "DV_DATE_TIME"
 */
public class OpenEHRTypeNaming extends ClassNameIdResolver {

    private ModelInfoLookup rmInfoLookup = ArchieRMInfoLookup.getInstance();
    private ModelInfoLookup aomInfoLookup = ArchieAOMInfoLookup.getInstance();

    protected OpenEHRTypeNaming() {
        super(TypeFactory.defaultInstance().constructType(OpenEHRBase.class), TypeFactory.defaultInstance());
    }

    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.NAME;
    }

    @Override
    public String idFromValue(Object value) {

        RMTypeInfo typeInfo = rmInfoLookup.getTypeInfo(value.getClass());
        if(typeInfo == null) {
            return rmInfoLookup.getNamingStrategy().getTypeName(value.getClass());
        } else {
            //this case is faster and should always work. If for some reason it does not, the above case works fine instead.
            return typeInfo.getRmName();
        }
// This should work in all cases for openEHR-classes and this should not be used for other classes
// Additional code for making this work on non-ehr-types:
//        } else {
//            return super.idFromValue(value);
//        }
    }


    @Deprecated // since 2.3
    @Override
    public JavaType typeFromId(String id) {
        return _typeFromId(id, null);
    }

    @Override
    public JavaType typeFromId(DatabindContext context, String id) {
        return _typeFromId(id, context);
    }

    @Override
    protected JavaType _typeFromId(String typeName, DatabindContext ctxt) {
        Class result = rmInfoLookup.getClass(typeName);
        if(result == null) {
            //AOM class?
            result = aomInfoLookup.getClass(typeName);
        }
        if(result != null) {
            TypeFactory typeFactory = (ctxt == null) ? _typeFactory : ctxt.getTypeFactory();
            return typeFactory.constructSpecializedType(_baseType, result);
        }
        return super._typeFromId(typeName, ctxt);
    }
}
