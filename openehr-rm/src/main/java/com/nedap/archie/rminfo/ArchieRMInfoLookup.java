package com.nedap.archie.rminfo;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.CObject;
import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.aom.primitives.CBoolean;
import com.nedap.archie.aom.primitives.CDate;
import com.nedap.archie.aom.primitives.CDateTime;
import com.nedap.archie.aom.primitives.CDuration;
import com.nedap.archie.aom.primitives.CInteger;
import com.nedap.archie.aom.primitives.CReal;
import com.nedap.archie.aom.primitives.CString;
import com.nedap.archie.aom.primitives.CTerminologyCode;
import com.nedap.archie.aom.primitives.CTime;
import com.nedap.archie.base.Interval;
import com.nedap.archie.base.terminology.TerminologyCode;
import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.datavalues.DvBoolean;
import com.nedap.archie.rm.support.identification.TerminologyId;
import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.datastructures.PointEvent;
import com.nedap.archie.rm.datatypes.CodePhrase;
import com.nedap.archie.rm.datavalues.DvCodedText;

import java.lang.reflect.Method;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by pieter.bos on 02/02/16.
 */
public class ArchieRMInfoLookup extends ReflectionModelInfoLookup {

    private static ArchieRMInfoLookup instance;

    private ArchieRMInfoLookup() {
        super(new ArchieModelNamingStrategy(), RMObject.class);
        addSubtypesOf(Interval.class); //extra class from the base package. No RMObject because it is also used in the AOM
    }

    public static ArchieRMInfoLookup getInstance() {
        if(instance == null) {
            instance = new ArchieRMInfoLookup();
        }
        return instance;
    }

    @Override
    public Class getClassToBeCreated(String rmTypename) {
        if(rmTypename.equals("EVENT")) {
            //this is an abstract class and cannot be created. Create point event instead
            return PointEvent.class;
        }
        return getClass(rmTypename);
    }

    @Override
    public Object convertToConstraintObject(Object object, CPrimitiveObject cPrimitiveObject) {
        if(cPrimitiveObject instanceof CTerminologyCode) {
            if(object instanceof DvCodedText) {
                return convertCodePhrase(((DvCodedText) object).getDefiningCode());
            } else if (object instanceof CodePhrase) {
                return convertCodePhrase((CodePhrase) object);
            }
        }
        return object;
    }

    private TerminologyCode convertCodePhrase(CodePhrase codePhrase) {
        TerminologyCode result = new TerminologyCode();
        result.setCodeString(codePhrase.getCodeString());
        result.setTerminologyId(codePhrase.getTerminologyId() == null ? null : codePhrase.getTerminologyId().getValue());
        return result;
    }

    public Object convertConstrainedPrimitiveToRMObject(Object object) {
        if(object instanceof TerminologyCode) {
            return convertTerminologyCode((TerminologyCode) object);
        }
        return object;
    }

    @Override
    protected boolean shouldAdd(Method method) {
        if(method.getName().equals("getPathSegments")) {
            return false;
        }
        return super.shouldAdd(method);
    }

    private CodePhrase convertTerminologyCode(TerminologyCode terminologyCode) {
        CodePhrase result = new CodePhrase();
        result.setCodeString(terminologyCode.getCodeString());
        result.setTerminologyId(terminologyCode == null ? null : new TerminologyId(terminologyCode.getTerminologyId()));
        return result;
    }

    @Override
    public void processCreatedObject(Object createdObject, CObject constraint) {
        if (createdObject instanceof Locatable) { //and most often, it will be
            Locatable locatable = (Locatable) createdObject;
            locatable.setArchetypeNodeId(constraint.getNodeId());
            locatable.setNameAsString(constraint.getMeaning());
        }
    }

    @Override
    public String getArchetypeNodeIdFromRMObject(Object rmObject) {
        if(rmObject == null) {
            return null;
        }
        if(rmObject instanceof Locatable) {
            Locatable locatable = (Locatable) rmObject;
            return locatable.getArchetypeNodeId();
        }
        return null;
    }

    @Override
    public String getNameFromRMObject(Object rmObject) {
        if(rmObject == null) {
            return null;
        }
        if(rmObject instanceof Locatable) {
            Locatable locatable = (Locatable) rmObject;
            return locatable.getNameAsString();
        }
        return null;
    }

    @Override
    public Object clone(Object rmObject) {
        if(rmObject instanceof RMObject) {
            return ((RMObject) rmObject).clone();
        }
        throw new IllegalArgumentException("The ArchieRMInfoLookup can only clone openehr reference model objects");
    }

    /**
     * Notification that a value at a given path has been updated in the given archetype. Perform tasks here to make sure
     * every other paths are updated as well.
     * @param rmObject
     * @param archetype
     * @param pathOfParent
     * @param parent
     */
    @Override
    public void pathHasBeenUpdated(Object rmObject, Archetype archetype, String pathOfParent, Object parent) {
        UpdatedValueHandler.pathHasBeenUpdated(rmObject, archetype, pathOfParent, parent);
    }

    @Override
    public boolean validatePrimitiveType(String rmTypeName, String rmAttributeName, CPrimitiveObject cObject) {
        RMAttributeInfo attributeInfo = this.getAttributeInfo(rmTypeName, rmAttributeName);
        if(attributeInfo == null) {
            return true;//cannot validate
        }
        Class typeInCollection = attributeInfo.getTypeInCollection();
        if(cObject instanceof CInteger) {
            return typeInCollection.equals(Long.class) || typeInCollection.getName().equals("long");
        } else if(cObject instanceof CReal) {
            return typeInCollection.equals(Double.class) || typeInCollection.getName().equals("double");
        } else if(cObject instanceof CString) {
            return typeInCollection.equals(String.class);
        } else if(cObject instanceof CDate) {
            return typeInCollection.equals(String.class) ||
                    typeInCollection.isAssignableFrom(Temporal.class);
        } else if(cObject instanceof CDateTime) {
            return typeInCollection.equals(String.class) ||
                    typeInCollection.isAssignableFrom(Temporal.class);
        } else if(cObject instanceof CDuration) {
            return typeInCollection.equals(String.class) ||
                    typeInCollection.isAssignableFrom(TemporalAccessor.class) ||
                    typeInCollection.isAssignableFrom(TemporalAmount.class);
        } else if(cObject instanceof CTime) {
            return typeInCollection.equals(String.class) ||
                    typeInCollection.isAssignableFrom(TemporalAccessor.class);
        } else if(cObject instanceof CTerminologyCode) {
            return typeInCollection.equals(CodePhrase.class) ||
                    typeInCollection.equals(DvCodedText.class);
        } else if(cObject instanceof CBoolean) {
            return typeInCollection.equals(Boolean.class) || typeInCollection.getName().equals("boolean");
        }
        return false;

    }

    @Override
    public Collection<RMPackageId> getId() {
        List<RMPackageId> result = new ArrayList<>();
        result.add(new RMPackageId("openEHR", "EHR"));
        result.add(new RMPackageId("openEHR", "DEMOGRAPHIC"));
        return result;
    }

}

