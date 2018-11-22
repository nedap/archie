package org.openehr.bmm.v2.validation.converters;

import org.openehr.bmm.core.BmmModel;
import org.openehr.bmm.v2.persistence.PBmmSchema;

import java.util.ArrayList;

public class BmmModelCreator {

    public BmmModel create(PBmmSchema schema) {
        BmmModel model = new BmmModel();
        model.setRmPublisher(schema.getRmPublisher());
        model.setRmRelease(schema.getRmRelease());
        //TODO: model.setBmmVersion(schema.getBmmVersion());
        model.setModelName(schema.getModelName());
        model.setSchemaName(schema.getSchemaName());
        model.setSchemaRevision(schema.getSchemaRevision());
        model.setSchemaAuthor(schema.getSchemaAuthor());
        model.setSchemaDescription(schema.getSchemaDescription());
        //model.setDocumentation(schema.getDo);TODO: possible?
        model.setSchemaContributors(new ArrayList<>(schema.getSchemaContributors()));
        return model;
    }
}
