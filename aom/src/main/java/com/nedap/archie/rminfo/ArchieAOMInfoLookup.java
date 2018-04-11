package com.nedap.archie.rminfo;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.base.Cardinality;
import com.nedap.archie.base.Interval;
import com.nedap.archie.base.terminology.TerminologyCode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by pieter.bos on 06/07/16.
 */
public class ArchieAOMInfoLookup extends ReflectionModelInfoLookup {

    private static ArchieAOMInfoLookup instance;

    public ArchieAOMInfoLookup() {
        super(new ArchieModelNamingStrategy(), ArchetypeModelObject.class, ArchieAOMInfoLookup.class.getClassLoader(), false /* no attributes without field */);
        addSubtypesOf(Interval.class); //extra class from the base package. No RMObject because it is also used in the AOM
        addSubtypesOf(Cardinality.class); //extra class from the base package. No RMObject because it is also used in the AOM
        addSubtypesOf(TerminologyCode.class); //extra class from the base package. No RMObject because it is also used in the AOM

    }

    public static ArchieAOMInfoLookup getInstance() {
        if(instance == null) {
            instance = new ArchieAOMInfoLookup();
        }
        return instance;
    }

    @Override
    public Object convertToConstraintObject(Object object, CPrimitiveObject cPrimitiveObject) {
        throw new UnsupportedOperationException("not supported");//TODO: split this to different classes
    }

    @Override
    public void processCreatedObject(Object createdObject, CObject constraint) {
        throw new UnsupportedOperationException("not supported");//TODO: split this to different classes
    }

    @Override
    public String getArchetypeNodeIdFromRMObject(Object rmObject) {
        //technically we could implement this :)
        if(rmObject instanceof CObject) {
            return ((CObject) rmObject).getNodeId();
        }
        throw new UnsupportedOperationException("not supported");//TODO: split this to different classes
    }

    @Override
    public String getNameFromRMObject(Object rmObject) {
        if(rmObject instanceof CObject) {
            return ((CObject) rmObject).getMeaning();
        }
        //This is a bit of a strange operation for the aom model.
        throw new UnsupportedOperationException("not supported");//TODO: split this to different classes
    }

    @Override
    public Object clone(Object rmObject) {
        if(rmObject instanceof ArchetypeModelObject) {
            return ((ArchetypeModelObject) rmObject).clone();
        }
        throw new IllegalArgumentException("The ArchieAOMInfoLookup can only clone archetype model objects");
    }

    @Override
    public Map<String, Object> pathHasBeenUpdated(Object rmObject, Archetype archetype, String pathOfParent, Object parent) {
        throw new UnsupportedOperationException("not supported");//TODO: split this to different classes
    }

    @Override
    public boolean validatePrimitiveType(String rmTypeName, String rmAttributeName, CPrimitiveObject cObject) {
        return true;
    }

    @Override
    public Collection<RMPackageId> getId() {
        List<RMPackageId> result = new ArrayList<>();
        result.add(new RMPackageId("OpenEHR", "AOM"));
        return result;
    }

}
