package com.nedap.archie.aom.primitives;

import com.nedap.archie.aom.CPrimitiveObject;
import com.nedap.archie.base.terminology.TerminologyCode;

/**
 * Terminology code constraint. Slight deviation from the model specs:
 * <br/>
 * The constraint is not a string
 ** <br/>
 *
 * Instead, you will now get a TerminologyCode with either only its terminology id set (ac code)
 * to constrain to a given terminology, or with both its id and value set (at-code)
 *
 * Created by pieter.bos on 15/10/15.
 */
public class CTerminologyCode extends CPrimitiveObject<TerminologyCode> {

}
