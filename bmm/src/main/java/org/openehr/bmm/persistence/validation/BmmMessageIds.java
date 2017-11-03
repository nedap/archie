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

import org.openehr.utils.message.I18n;
import org.openehr.utils.message.MessageCode;

public enum BmmMessageIds implements MessageCode {
    ec_bmm_documentation_text(I18n.register("Documentation")),
    ec_bmm_schemas_config_not_valid(I18n.register("Reference model schema(s) {0} specified in options not valid or not found in schema directories")),
    ec_bmm_schema_file_not_valid(I18n.register("Reference Model schema file {0} does not exist or not readable")),
    ec_bmm_schema_load_failure(I18n.register("Reference Model schema {0} load failure; reason: {1}")),
    ec_model_access_e3(I18n.register("Reference Model schema contains unknown type {0} (object add failed)")),
    ec_bmm_schema_load_failure_exception(I18n.register("Reference Model schema {0} load failure due to exception during processing")),
    ec_bmm_schema_dir_not_valid(I18n.register("Reference Model schema directory {0} does not exist or not readable")),
    ec_bmm_schema_dir_contains_no_schemas(I18n.register("Reference Model schema directory {0} does not contain any schemas")),
    ec_bmm_schema_dir_contains_no_valid_schemas(I18n.register("Reference Model schema directory {0} does not contain any valid schemas")),
    ec_model_access_e7(I18n.register("No Reference Model schema found for package ''{0}''")),
    ec_bmm_schema_post_merge_validate_fail(I18n.register("Reference Model schema {0} failed post-merge validation; errors:%N{1}")),
    ec_bmm_schema_included_schema_not_found(I18n.register("Reference Model included schema {0} not found or failed to load")),
    ec_bmm_schema_including_schema_not_valid(I18n.register("Reference Model including schema {0} not valid")),
    ec_bmm_schema_include_failed_to_load(I18n.register("Reference Model schema {0} includes a schema that failed to load")),
    ec_bmm_schema_basic_validation_failed(I18n.register("Reference Model schema {0} failed basic validation; errors:%N{1}")),
    ec_bmm_schema_unknown_exception(I18n.register("Unknown exception processing RM schemas")),
    ec_bmm_schema_assertion_violation(I18n.register("Assertion violation processing RM schemas; original recipient: {0}")),
    ec_bmm_schema_includes_valiidation_failed(I18n.register("Reference Model schema {0} includes validation failed: %N{1}")),
    ec_bmm_schema_version_incompatible_with_tool(I18n.register("Reference Model BMM schema {0} incompatible with current release {1} of the tool; obtain up to date schemas")),
    ec_bmm_schema_conv_fail_err(I18n.register("Reference Model schema {0} load data conversion failure; reason: {1}")),
    ec_bmm_schema_info_loaded(I18n.register("Reference Model schema {0} loaded: {1} primitive types, {2} class definitions")),
    ec_bmm_schema_merged_schema(I18n.register("Merged schema {0} into schema {1}")),
    ec_model_access_w1(I18n.register("Reference Model checking is OFF")),
    ec_bmm_schema_duplicate_schema_found(I18n.register("Duplicate Reference Model schema found for model ''{0}'' in file {1}, ignoring latter")),
    ec_bmm_schema_duplicate_found(I18n.register("Duplicate instance of Reference Model model {0} found; original schema {1}; ignoring instance from schema {2}")),
    ec_bmm_schema_rm_missing(I18n.register("Reference Model for {0} meta-data missing/invalid: {1}")),
    ec_model_access_w5(I18n.register("Unknown Reference Model ''{0}'' mentioned in ''rm_schemas_load_list'' config setting (ignored)")),
    ec_bmm_schemas_no_load_list_found(I18n.register("No ''rm_schemas_load_list'' config setting found; attempting to load all schemas (change via Tools>Options)")),
    ec_bmm_schema_invalid_load_list(I18n.register("''rm_schemas_load_list'' config setting mentions non-existent schema {0}")),
    ec_bmm_schema_passed_with_warnings(I18n.register("Reference Model schema {0} passed basic validation with warnings:%N{1}")),
    ec_BMM_INC(I18n.register("Reference Model schema {0} includes schema {1} that does not exist")),
    ec_BMM_VER(I18n.register("Schema {0} BMM version {1} incompatible with software version {2}")),
    ec_BMM_VERASS(I18n.register("Schema {0} BMM version {1} (assumed) incompatible with software version {2}")),
    ec_BMM_PTV(I18n.register("Schema {0} class definition {1} property {2} type {3} not defined in schema")),
    ec_BMM_ANC(I18n.register("Schema {0} class definition {1} ancestor {2} does not exist in schema")),
    ec_BMM_ANCE(I18n.register("Schema {0} class definition {1} includes empty ancestor class name")),
    ec_BMM_GPCT(I18n.register("Schema {0} class definition {1} generic parameter {2} constraint type {3} does not exist in schema")),
    ec_BMM_CPT(I18n.register("Schema {0} class definition {1} container property {2} target type not defined")),
    ec_BMM_CPTV(I18n.register("Schema {0} class definition {1} container property {2} target type {3} not found in schema")),
    ec_BMM_CPTNC(I18n.register("Schema {0} class definition {1} container property {2} cardinality not defined (assuming {0..*})")),
    ec_BMM_CPCT(I18n.register("Schema {0} class definition {1} container property {2} container type {3} not found in schema")),
    ec_BMM_GPT(I18n.register("Schema {0} class definition {1} generic property {2} not defined")),
    ec_BMM_GPRT(I18n.register("Schema {0} class definition {1} generic property {2} root type {3} not found in schema")),
    ec_BMM_GPGPT(I18n.register("Schema {0} class definition {1} generic property {2} generic parameter {3} not found in schema or in containing class declarations (if open)")),
    ec_BMM_GPGPM(I18n.register("Schema {0} class definition {1} marked ''is_generic'' but has no generic parameter declarations")),
    ec_BMM_GPGPU(I18n.register("Schema {0} class definition {1} generic property {2} type {3} parameter {4} not found in class definitions or {3} formal declaration")),
    ec_BMM_SPT(I18n.register("Schema {0} class definition {1} single-valued property {2} type {3} not found in schema")),
    ec_BMM_SPOT(I18n.register("Schema {0} class definition {1} single-valued property {2} open generic parameter {3} not found in containing class declarations")),
    ec_BMM_PKGCL(I18n.register("Schema {0} class {1} mentioned in package {2} but not in schema, or relevant child schema")),
    ec_BMM_PKGID(I18n.register("Schema {0} class definition {1} not declared in any package")),
    ec_BMM_PKGTL(I18n.register("Schema {0} top-level sibling package definitions cannot include a package which is the child of another")),
    ec_BMM_PKGQN(I18n.register("Schema {0} packages with qualified name found in package {1} (qualified names not allowed except at top-level)")),
    ec_BMM_PKGCE(I18n.register("Schema {0} package {1} contains class with empty name")),
    ec_BMM_CLPKDP(I18n.register("Schema {0} has duplicate class name {1} in package {2} and also package {3}")),
    ec_BMM_CLDUP(I18n.register("Schema {0} has duplicate class name {1} in class definitions")),
    ec_BMM_MDLPK(I18n.register("Schema {0} archetype_closure_package {1} does not exist")),
    ec_BMM_PRDUP(I18n.register("Schema {0} class {1} duplicate property within class {2}")),
    ec_BMM_PRNCF(I18n.register("Schema {0} class {1} property {2} does not conform to same property in ancestor {3} (duplicate?)")),
    ec_BMM_INCERR(I18n.register("Schema {0} included schema {1} validity failure")),
    ec_BMM_INCWARN(I18n.register("Schema {0} included schema {1} validity warning")),
    ec_BMM_ARPAR(I18n.register("Schema {0} archetype parent class {1} not defined in schema")),
    ec_BMM_RMREL(I18n.register("Schema {0} RM release {1} not valid; should be 3-part numeric version")),
    //Added for java-model-stack
    ec_object_file_not_valid(I18n.register("EC Object file not valid")),
    ec_bmm_schema_load_error(I18n.register("BMM Schema load error"));

    private final String template;

    BmmMessageIds(String template) {
        this.template = template;
    }

    @Override
    public String getCode() {
        String code = name();
        if(code.startsWith("ec_")) {//it''s possible to get rid of the ec_ prefix altogether
            return code.substring(3);
        }
        return code;
    }

    public String getMessageTemplate() {
        return template;
    }

}
