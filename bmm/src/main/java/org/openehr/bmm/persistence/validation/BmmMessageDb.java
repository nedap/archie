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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BmmMessageDb {

    public static final Map<String, String> MessageTable = buildMessageTable();

    private static Map<String, String> buildMessageTable() {
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put(BmmMessageIds.ec_bmm_documentation_text, "Documentation");
        messageMap.put(BmmMessageIds.ec_bmm_schemas_config_not_valid,"Reference model schema(s) $1 specified in options not valid or not found in schema directories");
        messageMap.put(BmmMessageIds.ec_bmm_schema_file_not_valid,"Reference Model schema file $1 does not exist or not readable");
        messageMap.put(BmmMessageIds.ec_bmm_schema_load_failure,"Reference Model schema $1 load failure; reason: $2");
        messageMap.put(BmmMessageIds.ec_model_access_e3,"Reference Model schema contains unknown type $1 (object add failed)");
        messageMap.put(BmmMessageIds.ec_bmm_schema_load_failure_exception,"Reference Model schema $1 load failure due to exception during processing");
        messageMap.put(BmmMessageIds.ec_bmm_schema_dir_not_valid,"Reference Model schema directory $1 does not exist or not readable");
        messageMap.put(BmmMessageIds.ec_bmm_schema_dir_contains_no_schemas,"Reference Model schema directory $1 does not contain any schemas");
        messageMap.put(BmmMessageIds.ec_bmm_schema_dir_contains_no_valid_schemas,"Reference Model schema directory $1 does not contain any valid schemas");
        messageMap.put(BmmMessageIds.ec_model_access_e7,"No Reference Model schema found for package '$1'");
        messageMap.put(BmmMessageIds.ec_bmm_schema_post_merge_validate_fail,"Reference Model schema $1 failed post-merge validation; errors:%N$2");
        messageMap.put(BmmMessageIds.ec_bmm_schema_included_schema_not_found,"Reference Model included schema $1 not found or failed to load");
        messageMap.put(BmmMessageIds.ec_bmm_schema_including_schema_not_valid,"Reference Model including schema $1 not valid");
        messageMap.put(BmmMessageIds.ec_bmm_schema_include_failed_to_load,"Reference Model schema $1 includes a schema that failed to load");
        messageMap.put(BmmMessageIds.ec_bmm_schema_basic_validation_failed,"Reference Model schema $1 failed basic validation; errors:%N$2");
        messageMap.put(BmmMessageIds.ec_bmm_schema_unknown_exception,"Unknown exception processing RM schemas");
        messageMap.put(BmmMessageIds.ec_bmm_schema_assertion_violation,"Assertion violation processing RM schemas; original recipient: $1");
        messageMap.put(BmmMessageIds.ec_bmm_schema_includes_valiidation_failed,"Reference Model schema $1 includes validation failed: %N$2");
        messageMap.put(BmmMessageIds.ec_bmm_schema_version_incompatible_with_tool,"Reference Model BMM schema $1 incompatible with current release $2 of the tool; obtain up to date schemas");
        messageMap.put(BmmMessageIds.ec_bmm_schema_conv_fail_err,"Reference Model schema $1 load data conversion failure; reason: $2");
        messageMap.put(BmmMessageIds.ec_bmm_schema_info_loaded,"Reference Model schema $1 loaded: $2 primitive types, $3 class definitions");
        messageMap.put(BmmMessageIds.ec_bmm_schema_merged_schema,"Merged schema $1 into schema $2");
        messageMap.put(BmmMessageIds.ec_model_access_w1,"Reference Model checking is OFF");
        messageMap.put(BmmMessageIds.ec_bmm_schema_duplicate_schema_found,"Duplicate Reference Model schema found for model '$1' in file $2, ignoring latter");
        messageMap.put(BmmMessageIds.ec_bmm_schema_duplicate_found,"Duplicate instance of Reference Model model $1 found; original schema $2; ignoring instance from schema $3");
        messageMap.put(BmmMessageIds.ec_bmm_schema_rm_missing,"Reference Model for $1 meta-data missing/invalid: $2");
        messageMap.put(BmmMessageIds.ec_model_access_w5,"Unknown Reference Model '$1' mentioned in 'rm_schemas_load_list' config setting (ignored)");
        messageMap.put(BmmMessageIds.ec_bmm_schemas_no_load_list_found,"No 'rm_schemas_load_list' config setting found; attempting to load all schemas (change via Tools>Options)");
        messageMap.put(BmmMessageIds.ec_bmm_schema_invalid_load_list,"'rm_schemas_load_list' config setting mentions non-existent schema $1");
        messageMap.put(BmmMessageIds.ec_bmm_schema_passed_with_warnings,"Reference Model schema $1 passed basic validation with warnings:%N$2");
        messageMap.put(BmmMessageIds.ec_BMM_INC,"Reference Model schema $1 includes schema $2 that does not exist");
        messageMap.put(BmmMessageIds.ec_BMM_VER,"Schema $1 BMM version $2 incompatible with software version $3");
        messageMap.put(BmmMessageIds.ec_BMM_VERASS,"Schema $1 BMM version $2 (assumed) incompatible with software version $3");
        messageMap.put(BmmMessageIds.ec_BMM_PTV,"Schema $1 class definition $2 property $3 type $4 not defined in schema");
        messageMap.put(BmmMessageIds.ec_BMM_ANC,"Schema $1 class definition $2 ancestor $3 does not exist in schema");
        messageMap.put(BmmMessageIds.ec_BMM_ANCE,"Schema $1 class definition $2 includes empty ancestor class name");
        messageMap.put(BmmMessageIds.ec_BMM_GPCT,"Schema $1 class definition $2 generic parameter $3 constraint type $4 does not exist in schema");
        messageMap.put(BmmMessageIds.ec_BMM_CPT,"Schema $1 class definition $2 container property $3 target type not defined");
        messageMap.put(BmmMessageIds.ec_BMM_CPTV,"Schema $1 class definition $2 container property $3 target type $4 not found in schema");
        messageMap.put(BmmMessageIds.ec_BMM_CPTNC,"Schema $1 class definition $2 container property $3 cardinality not defined (assuming {0..*})");
        messageMap.put(BmmMessageIds.ec_BMM_CPCT,"Schema $1 class definition $2 container property $3 container type $4 not found in schema");
        messageMap.put(BmmMessageIds.ec_BMM_GPT,"Schema $1 class definition $2 generic property $3 not defined");
        messageMap.put(BmmMessageIds.ec_BMM_GPRT,"Schema $1 class definition $2 generic property $3 root type $4 not found in schema");
        messageMap.put(BmmMessageIds.ec_BMM_GPGPT,"Schema $1 class definition $2 generic property $3 generic parameter $4 not found in schema or in containing class declarations (if open)");
        messageMap.put(BmmMessageIds.ec_BMM_GPGPM,"Schema $1 class definition $2 marked 'is_generic' but has no generic parameter declarations");
        messageMap.put(BmmMessageIds.ec_BMM_GPGPU,"Schema $1 class definition $2 generic property $3 type $4 parameter $5 not found in class definitions or $4 formal declaration");
        messageMap.put(BmmMessageIds.ec_BMM_SPT,"Schema $1 class definition $2 single-valued property $3 type $4 not found in schema");
        messageMap.put(BmmMessageIds.ec_BMM_SPOT,"Schema $1 class definition $2 single-valued property $3 open generic parameter $4 not found in containing class declarations");
        messageMap.put(BmmMessageIds.ec_BMM_PKGCL,"Schema $1 class $2 mentioned in package $3 but not in schema, or relevant child schema");
        messageMap.put(BmmMessageIds.ec_BMM_PKGID,"Schema $1 class definition $2 not declared in any package");
        messageMap.put(BmmMessageIds.ec_BMM_PKGTL,"Schema $1 top-level sibling package definitions cannot include a package which is the child of another");
        messageMap.put(BmmMessageIds.ec_BMM_PKGQN,"Schema $1 packages with qualified name found in package $2 (qualified names not allowed except at top-level)");
        messageMap.put(BmmMessageIds.ec_BMM_PKGCE,"Schema $1 package $2 contains class with empty name");
        messageMap.put(BmmMessageIds.ec_BMM_CLPKDP,"Schema $1 has duplicate class name $2 in package $3 and also package $4");
        messageMap.put(BmmMessageIds.ec_BMM_CLDUP,"Schema $1 has duplicate class name $2 in class definitions");
        messageMap.put(BmmMessageIds.ec_BMM_MDLPK,"Schema $1 archetype_closure_package $2 does not exist");
        messageMap.put(BmmMessageIds.ec_BMM_PRDUP,"Schema $1 class $2 duplicate property within class $3");
        messageMap.put(BmmMessageIds.ec_BMM_PRNCF,"Schema $1 class $2 property $3 does not conform to same property in ancestor $4 (duplicate?)");
        messageMap.put(BmmMessageIds.ec_BMM_INCERR,"Schema $1 included schema $2 validity failure");
        messageMap.put(BmmMessageIds.ec_BMM_INCWARN,"Schema $1 included schema $2 validity warning");
        messageMap.put(BmmMessageIds.ec_BMM_ARPAR,"Schema $1 archetype parent class $2 not defined in schema");
        messageMap.put(BmmMessageIds.ec_BMM_RMREL,"Schema $1 RM release $2 not valid; should be 3-part numeric version");
        return Collections.unmodifiableMap(messageMap);
    }
}
