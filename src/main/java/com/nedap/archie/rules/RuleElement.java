package com.nedap.archie.rules;

import com.nedap.archie.aom.ArchetypeModelObject;
import com.nedap.archie.base.OpenEHRBase;

/**
 * Created by pieter.bos on 27/10/15.
 */
public class RuleElement extends ArchetypeModelObject {

    private ExpressionType type;

    public ExpressionType getType() {
        return type;
    }

    public void setType(ExpressionType type) {
        this.type = type;
    }
}
