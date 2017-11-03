package org.openehr.bmm.persistence.validation;

/*
 * #%L
 * OpenEHR - Java Model Stack
 * %%
 * Copyright (C) 2016 - 2017  Cognitive Medical Systems, Inc (http://www.cognitivemedicine.com).
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 * Author: Claude Nanjo
 */

import org.openehr.utils.message.MessageDatabase;

import java.util.HashMap;
import java.util.Map;

public class BmmMessageDatabase extends MessageDatabase{

    public BmmMessageDatabase() {
        super();
        setMessageTable(buildMessageTable());
    }

    /**
     * Builds method map for the BMM Message Template Database.
     * @return
     */
    private static Map<String, String> buildMessageTable() {
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put(BmmMessageIds.ec_bmm_documentation_text.getCode(), "Documentation");
        messageMap.put(BmmMessageIds.ec_bmm_schemas_config_not_valid.getCode(), "Reference model schema(s) {0} specified in options not valid or not found in schema directories");
        messageMap.put(BmmMessageIds.ec_bmm_schema_file_not_valid.getCode(), "Reference Model schema file {0} does not exist or not readable");
        messageMap.put(BmmMessageIds.ec_bmm_schema_load_failure.getCode(), "Reference Model schema {0} load failure; reason: {1}");
        messageMap.put(BmmMessageIds.ec_model_access_e3.getCode(), "Reference Model schema contains unknown type {0} (object add failed)");
        messageMap.put(BmmMessageIds.ec_bmm_schema_load_failure_exception.getCode(), "Reference Model schema {0} load failure due to exception during processing");
        messageMap.put(BmmMessageIds.ec_bmm_schema_dir_not_valid.getCode(), "Reference Model schema directory {0} does not exist or not readable");
        messageMap.put(BmmMessageIds.ec_bmm_schema_dir_contains_no_schemas.getCode(), "Reference Model schema directory {0} does not contain any schemas");
        messageMap.put(BmmMessageIds.ec_bmm_schema_dir_contains_no_valid_schemas.getCode(), "Reference Model schema directory {0} does not contain any valid schemas");
        messageMap.put(BmmMessageIds.ec_model_access_e7.getCode(), "No Reference Model schema found for package '{0}'");
        messageMap.put(BmmMessageIds.ec_bmm_schema_post_merge_validate_fail.getCode(), "Reference Model schema {0} failed post-merge validation; errors:%N{1}");
        messageMap.put(BmmMessageIds.ec_bmm_schema_included_schema_not_found.getCode(), "Reference Model included schema {0} not found or failed to load");
        messageMap.put(BmmMessageIds.ec_bmm_schema_including_schema_not_valid.getCode(), "Reference Model including schema {0} not valid");
        messageMap.put(BmmMessageIds.ec_bmm_schema_include_failed_to_load.getCode(), "Reference Model schema {0} includes a schema that failed to load");
        messageMap.put(BmmMessageIds.ec_bmm_schema_basic_validation_failed.getCode(), "Reference Model schema {0} failed basic validation; errors:%N{1}");
        messageMap.put(BmmMessageIds.ec_bmm_schema_unknown_exception.getCode(), "Unknown exception processing RM schemas");
        messageMap.put(BmmMessageIds.ec_bmm_schema_assertion_violation.getCode(), "Assertion violation processing RM schemas; original recipient: {0}");
        messageMap.put(BmmMessageIds.ec_bmm_schema_includes_valiidation_failed.getCode(), "Reference Model schema {0} includes validation failed: %N{1}");
        messageMap.put(BmmMessageIds.ec_bmm_schema_version_incompatible_with_tool.getCode(), "Reference Model BMM schema {0} incompatible with current release {1} of the tool; obtain up to date schemas");
        messageMap.put(BmmMessageIds.ec_bmm_schema_conv_fail_err.getCode(), "Reference Model schema {0} load data conversion failure; reason: {1}");
        messageMap.put(BmmMessageIds.ec_bmm_schema_info_loaded.getCode(), "Reference Model schema {0} loaded: {1} primitive types, {2} class definitions");
        messageMap.put(BmmMessageIds.ec_bmm_schema_merged_schema.getCode(), "Merged schema {0} into schema {1}");
        messageMap.put(BmmMessageIds.ec_model_access_w1.getCode(), "Reference Model checking is OFF");
        messageMap.put(BmmMessageIds.ec_bmm_schema_duplicate_schema_found.getCode(), "Duplicate Reference Model schema found for model '{0}' in file {1}, ignoring latter");
        messageMap.put(BmmMessageIds.ec_bmm_schema_duplicate_found.getCode(), "Duplicate instance of Reference Model model {0} found; original schema {1}; ignoring instance from schema {2}");
        messageMap.put(BmmMessageIds.ec_bmm_schema_rm_missing.getCode(), "Reference Model for {0} meta-data missing/invalid: {1}");
        messageMap.put(BmmMessageIds.ec_model_access_w5.getCode(), "Unknown Reference Model '{0}' mentioned in 'rm_schemas_load_list' config setting (ignored)");
        messageMap.put(BmmMessageIds.ec_bmm_schemas_no_load_list_found.getCode(), "No 'rm_schemas_load_list' config setting found; attempting to load all schemas (change via Tools>Options)");
        messageMap.put(BmmMessageIds.ec_bmm_schema_invalid_load_list.getCode(), "'rm_schemas_load_list' config setting mentions non-existent schema {0}");
        messageMap.put(BmmMessageIds.ec_bmm_schema_passed_with_warnings.getCode(), "Reference Model schema {0} passed basic validation with warnings:%N{1}");
        messageMap.put(BmmMessageIds.ec_BMM_INC.getCode(), "Reference Model schema {0} includes schema {1} that does not exist");
        messageMap.put(BmmMessageIds.ec_BMM_VER.getCode(), "Schema {0} BMM version {1} incompatible with software version {2}");
        messageMap.put(BmmMessageIds.ec_BMM_VERASS.getCode(), "Schema {0} BMM version {1} (assumed) incompatible with software version {2}");
        messageMap.put(BmmMessageIds.ec_BMM_PTV.getCode(), "Schema {0} class definition {1} property {2} type {3} not defined in schema");
        messageMap.put(BmmMessageIds.ec_BMM_ANC.getCode(), "Schema {0} class definition {1} ancestor {2} does not exist in schema");
        messageMap.put(BmmMessageIds.ec_BMM_ANCE.getCode(), "Schema {0} class definition {1} includes empty ancestor class name");
        messageMap.put(BmmMessageIds.ec_BMM_GPCT.getCode(), "Schema {0} class definition {1} generic parameter {2} constraint type {3} does not exist in schema");
        messageMap.put(BmmMessageIds.ec_BMM_CPT.getCode(), "Schema {0} class definition {1} container property {2} target type not defined");
        messageMap.put(BmmMessageIds.ec_BMM_CPTV.getCode(), "Schema {0} class definition {1} container property {2} target type {3} not found in schema");
        messageMap.put(BmmMessageIds.ec_BMM_CPTNC.getCode(), "Schema {0} class definition {1} container property {2} cardinality not defined (assuming {0..*})");
        messageMap.put(BmmMessageIds.ec_BMM_CPCT.getCode(), "Schema {0} class definition {1} container property {2} container type {3} not found in schema");
        messageMap.put(BmmMessageIds.ec_BMM_GPT.getCode(), "Schema {0} class definition {1} generic property {2} not defined");
        messageMap.put(BmmMessageIds.ec_BMM_GPRT.getCode(), "Schema {0} class definition {1} generic property {2} root type {3} not found in schema");
        messageMap.put(BmmMessageIds.ec_BMM_GPGPT.getCode(), "Schema {0} class definition {1} generic property {2} generic parameter {3} not found in schema or in containing class declarations (if open)");
        messageMap.put(BmmMessageIds.ec_BMM_GPGPM.getCode(), "Schema {0} class definition {1} marked 'is_generic' but has no generic parameter declarations");
        messageMap.put(BmmMessageIds.ec_BMM_GPGPU.getCode(), "Schema {0} class definition {1} generic property {2} type {3} parameter {4} not found in class definitions or {3} formal declaration");
        messageMap.put(BmmMessageIds.ec_BMM_SPT.getCode(), "Schema {0} class definition {1} single-valued property {2} type {3} not found in schema");
        messageMap.put(BmmMessageIds.ec_BMM_SPOT.getCode(), "Schema {0} class definition {1} single-valued property {2} open generic parameter {3} not found in containing class declarations");
        messageMap.put(BmmMessageIds.ec_BMM_PKGCL.getCode(), "Schema {0} class {1} mentioned in package {2} but not in schema, or relevant child schema");
        messageMap.put(BmmMessageIds.ec_BMM_PKGID.getCode(), "Schema {0} class definition {1} not declared in any package");
        messageMap.put(BmmMessageIds.ec_BMM_PKGTL.getCode(), "Schema {0} top-level sibling package definitions cannot include a package which is the child of another");
        messageMap.put(BmmMessageIds.ec_BMM_PKGQN.getCode(), "Schema {0} packages with qualified name found in package {1} (qualified names not allowed except at top-level)");
        messageMap.put(BmmMessageIds.ec_BMM_PKGCE.getCode(), "Schema {0} package {1} contains class with empty name");
        messageMap.put(BmmMessageIds.ec_BMM_CLPKDP.getCode(), "Schema {0} has duplicate class name {1} in package {2} and also package {3}");
        messageMap.put(BmmMessageIds.ec_BMM_CLDUP.getCode(), "Schema {0} has duplicate class name {1} in class definitions");
        messageMap.put(BmmMessageIds.ec_BMM_MDLPK.getCode(), "Schema {0} archetype_closure_package {1} does not exist");
        messageMap.put(BmmMessageIds.ec_BMM_PRDUP.getCode(), "Schema {0} class {1} duplicate property within class {2}");
        messageMap.put(BmmMessageIds.ec_BMM_PRNCF.getCode(), "Schema {0} class {1} property {2} does not conform to same property in ancestor {3} (duplicate?)");
        messageMap.put(BmmMessageIds.ec_BMM_INCERR.getCode(), "Schema {0} included schema {1} validity failure");
        messageMap.put(BmmMessageIds.ec_BMM_INCWARN.getCode(), "Schema {0} included schema {1} validity warning");
        messageMap.put(BmmMessageIds.ec_BMM_ARPAR.getCode(), "Schema {0} archetype parent class {1} not defined in schema");
        messageMap.put(BmmMessageIds.ec_BMM_RMREL.getCode(), "Schema {0} RM release {1} not valid; should be 3-part numeric version");
        return messageMap;
    }
}
