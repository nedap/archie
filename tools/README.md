# OpenEHR tools

Tools to use the OpenEHR AOM and RMs, such as the flattener, rule evaluation and rm object creation.

Should not depend on a specific RM implementation, but currently still depends on Locatable and Pathable. If this needs to be changed, we need a solution for that. That requires for example pluggable code in the RMObjectCreator and RuleEvaluation. That's necessary anyway for full support for different possibly external reference models.

We could further split this up to separate for example RM Object creation, rule evaluation and the flattener.