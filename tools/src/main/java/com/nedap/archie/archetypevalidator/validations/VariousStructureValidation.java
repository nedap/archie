package com.nedap.archie.archetypevalidator.validations;

import com.nedap.archie.aom.Archetype;
import com.nedap.archie.aom.ArchetypeHRID;
import com.nedap.archie.aom.ArchetypeSlot;
import com.nedap.archie.aom.CArchetypeRoot;
import com.nedap.archie.aom.CComplexObject;
import com.nedap.archie.archetypevalidator.ErrorType;
import com.nedap.archie.archetypevalidator.ValidatingVisitor;
import com.nedap.archie.archetypevalidator.ValidationMessage;
import com.nedap.archie.flattener.ArchetypeRepository;
import com.nedap.archie.rminfo.ModelInfoLookup;
import com.nedap.archie.rminfo.RMTypeInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VariousStructureValidation extends ValidatingVisitor {

    private final ModelInfoLookup lookup;
    private Archetype archetype;
    private Archetype flatParent;
    private ArchetypeRepository repository;

    public VariousStructureValidation(ModelInfoLookup lookup) {
        this.lookup = lookup;
    }
    
    protected void beginValidation(Archetype archetype, Archetype flatParent, ArchetypeRepository repository) {
        this.archetype = archetype;
        this.flatParent = flatParent;
        this.repository = repository;
    }

    @Override
    protected List<ValidationMessage> validate(ArchetypeSlot slot) {
        //TODO: implement this construct, but first we need something that checks if
        //the assertions match something like 'archetype_id matches {*}', eg, assertion.matches_any() from eiffel

        /*if includes not empty and = any then
                not (excludes empty or /= any) ==> VDSEV Error
            elseif includes not empty and /= any then
                not (excludes empty or = any) ==> VDSEV Error
            elseif excludes not empty and = any then
                not (includes empty or /= any) ==> VDSIV Error
            elseif excludes not empty and /= any then
                not (includes empty or = any) ==> VDSIV Error
            end*/
        return Collections.emptyList();

    }

    @Override
    protected List<ValidationMessage> validate(CComplexObject cComplexObject) {
        List<ValidationMessage> result = new ArrayList<>();
        if(cComplexObject instanceof CArchetypeRoot) {
            CArchetypeRoot archetypeRoot = (CArchetypeRoot) cComplexObject;
            if(repository.getArchetype(archetypeRoot.getArchetypeRef()) == null) {
                result.add(new ValidationMessage(ErrorType.VARXRA, cComplexObject.path(), String.format("archetype with id %s not found", archetypeRoot.getArchetypeRef())));
            }
            ArchetypeHRID hrId = new ArchetypeHRID(archetypeRoot.getArchetypeRef());
            String archetypeRootTypeName = cComplexObject.getRmTypeName();
            String archetypeReferenceTypeName = hrId.getRmClass();

            RMTypeInfo parentTypeInfo = lookup.getTypeInfo(archetypeRootTypeName);
            RMTypeInfo specializedTypeInfo = lookup.getTypeInfo(archetypeReferenceTypeName);
            if(parentTypeInfo != null) {
                //if parent type info not found will be checked later in phase 2
                if(specializedTypeInfo == null) {
                    result.add(new ValidationMessage(ErrorType.VCORM, cComplexObject.getPath(), cComplexObject.getRmTypeName()));
                } else if(!parentTypeInfo.isDescendantOrEqual(specializedTypeInfo)){
                    result.add(new ValidationMessage(ErrorType.VARXTV, cComplexObject.getPath(), cComplexObject.getRmTypeName()));
                }
            }
        }
        return result;
    }



}
