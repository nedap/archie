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

import org.openehr.utils.message.MessageCode;

public enum BmmMessageIds implements MessageCode {
    ec_bmm_documentation_text("bmm_documentation_text"),
    ec_bmm_schemas_config_not_valid("bmm_schemas_config_not_valid"),
    ec_bmm_schema_file_not_valid("bmm_schema_file_not_valid"),
    ec_bmm_schema_load_failure("bmm_schema_load_failure"),
    ec_model_access_e3("model_access_e3"),
    ec_bmm_schema_load_failure_exception("bmm_schema_load_failure_exception"),
    ec_bmm_schema_dir_not_valid("bmm_schema_dir_not_valid"),
    ec_bmm_schema_dir_contains_no_schemas("bmm_schema_dir_contains_no_schemas"),
    ec_bmm_schema_dir_contains_no_valid_schemas("bmm_schema_dir_contains_no_valid_schemas"),
    ec_model_access_e7("model_access_e7"),
    ec_bmm_schema_post_merge_validate_fail("bmm_schema_post_merge_validate_fail"),
    ec_bmm_schema_included_schema_not_found("bmm_schema_included_schema_not_found"),
    ec_bmm_schema_including_schema_not_valid("bmm_schema_including_schema_not_valid"),
    ec_bmm_schema_include_failed_to_load("bmm_schema_include_failed_to_load"),
    ec_bmm_schema_basic_validation_failed("bmm_schema_basic_validation_failed"),
    ec_bmm_schema_unknown_exception("bmm_schema_unknown_exception"),
    ec_bmm_schema_assertion_violation("bmm_schema_assertion_violation"),
    ec_bmm_schema_includes_valiidation_failed("bmm_schema_includes_valiidation_failed"),
    ec_bmm_schema_version_incompatible_with_tool("bmm_schema_version_incompatible_with_tool"),
    ec_bmm_schema_conv_fail_err("bmm_schema_conv_fail_err"),
    ec_bmm_schema_info_loaded("bmm_schema_info_loaded"),
    ec_bmm_schema_merged_schema("bmm_schema_merged_schema"),
    ec_model_access_w1("model_access_w1"),
    ec_bmm_schema_duplicate_schema_found("bmm_schema_duplicate_schema_found"),
    ec_bmm_schema_duplicate_found("bmm_schema_duplicate_found"),
    ec_bmm_schema_rm_missing("bmm_schema_rm_missing"),
    ec_model_access_w5("model_access_w5"),
    ec_bmm_schemas_no_load_list_found("bmm_schemas_no_load_list_found"),
    ec_bmm_schema_invalid_load_list("bmm_schema_invalid_load_list"),
    ec_bmm_schema_passed_with_warnings("bmm_schema_passed_with_warnings"),
    ec_BMM_INC("BMM_INC"),
    ec_BMM_VER("BMM_VER"),
    ec_BMM_VERASS("BMM_VERASS"),
    ec_BMM_PTV("BMM_PTV"),
    ec_BMM_ANC("BMM_ANC"),
    ec_BMM_ANCE("BMM_ANCE"),
    ec_BMM_GPCT("BMM_GPCT"),
    ec_BMM_CPT("BMM_CPT"),
    ec_BMM_CPTV("BMM_CPTV"),
    ec_BMM_CPTNC("BMM_CPTNC"),
    ec_BMM_CPCT("BMM_CPCT"),
    ec_BMM_GPT("BMM_GPT"),
    ec_BMM_GPRT("BMM_GPRT"),
    ec_BMM_GPGPT("BMM_GPGPT"),
    ec_BMM_GPGPM("BMM_GPGPM"),
    ec_BMM_GPGPU("BMM_GPGPU"),
    ec_BMM_SPT("BMM_SPT"),
    ec_BMM_SPOT("BMM_SPOT"),
    ec_BMM_PKGCL("BMM_PKGCL"),
    ec_BMM_PKGID("BMM_PKGID"),
    ec_BMM_PKGTL("BMM_PKGTL"),
    ec_BMM_PKGQN("BMM_PKGQN"),
    ec_BMM_PKGCE("BMM_PKGCE"),
    ec_BMM_CLPKDP("BMM_CLPKDP"),
    ec_BMM_CLDUP("BMM_CLDUP"),
    ec_BMM_MDLPK("BMM_MDLPK"),
    ec_BMM_PRDUP("BMM_PRDUP"),
    ec_BMM_PRNCF("BMM_PRNCF"),
    ec_BMM_INCERR("BMM_INCERR"),
    ec_BMM_INCWARN("BMM_INCWARN"),
    ec_BMM_ARPAR("BMM_ARPAR"),
    ec_BMM_RMREL("BMM_RMREL"),
    //Added for java-model-stack
    ec_object_file_not_valid("object_file_not_valid"),
    ec_bmm_schema_load_error("bmm_schema_load_failed");

    BmmMessageIds(String code) {

    }

    @Override
    public String getCode() {
        return name();
    }

    public String getMessage() {
        return "";
    }
}
