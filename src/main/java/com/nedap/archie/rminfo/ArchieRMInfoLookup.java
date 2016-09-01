package com.nedap.archie.rminfo;

import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.aom.primitives.CTerminologyCode;
import com.nedap.archie.base.Interval;
import com.nedap.archie.base.terminology.TerminologyCode;
import com.nedap.archie.rm.RMObject;
import com.nedap.archie.rm.datastructures.PointEvent;
import com.nedap.archie.rm.datatypes.CodePhrase;
import com.nedap.archie.rm.datavalues.DvCodedText;

/**
 * Created by pieter.bos on 02/02/16.
 */
public class ArchieRMInfoLookup extends ModelInfoLookup {

    private static ArchieRMInfoLookup instance;

    public ArchieRMInfoLookup() {
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
        result.setTerminologyId(codePhrase.getTerminologyId());
        return result;
    }

    public Object convertConstrainedPrimitiveToRMObject(Object object) {
        if(object instanceof TerminologyCode) {
            return convertTerminologyCode((TerminologyCode) object);
        }
        return object;
    }

    private CodePhrase convertTerminologyCode(TerminologyCode terminologyCode) {
        CodePhrase result = new CodePhrase();
        result.setCodeString(terminologyCode.getCodeString());
        result.setTerminologyId(terminologyCode.getTerminologyId());
        return result;
    }
}

