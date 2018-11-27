package org.openehr.bmm.v2.persistence;

import java.util.List;

public abstract class PBmmType extends PBmmBase {

    /**
     * Formal name of the type for display.
     *
     * @return
     */
    public abstract String asTypeString();

    /**
     * Flattened list of type names making up full type.
     *
     * @return
     */
    public abstract List<String> flattenedTypeList();

}
