package org.openehr.docgen.model;

/**
 * Copyright 2017 Cognitive Medical Systems, Inc (http://www.cognitivemedicine.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Created by cnanjo on 3/7/17.
 */

public class ClassListItem {

    private String classDetailsUrl;
    private String classPackageString;
    private String className;
    private boolean flagClass;

    public ClassListItem() {
    }

    public ClassListItem(String classDetailsUrl, String classPackageString, String className) {
        this.classDetailsUrl = classDetailsUrl;
        this.classPackageString = classPackageString;
        this.className = className;
    }

    public String getClassDetailsUrl() {
        return classDetailsUrl;
    }

    public void setClassDetailsUrl(String classDetailsUrl) {
        this.classDetailsUrl = classDetailsUrl;
    }

    public String getClassPackageString() {
        return classPackageString;
    }

    public void setClassPackageString(String classPackageString) {
        this.classPackageString = classPackageString;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isFlagClass() {
        return flagClass;
    }

    public void setFlagClass(boolean flagClass) {
        this.flagClass = flagClass;
    }
}
