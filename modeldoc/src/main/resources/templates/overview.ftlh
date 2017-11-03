<html>
    <head>
        <title>UML Overview of CIMI Reference Model</title>
        <meta name="date" content="2017-03-13">
        <meta name="keywords" content="Clinical Information Model Initiative (CIMI)">
        <link rel="stylesheet" type="text/css" href="${stylesheet}" title="Style">
    </head>
    <body>
    <script type="text/javascript"><!--
    if (location.href.indexOf('is-external=true') == -1) {
        parent.document.title="Overview (CIMI Reference Model)";
    }
    //-->
    </script>
    <noscript>
        <div>JavaScript is disabled on your browser.</div>
    </noscript>
    <!-- ========= START OF TOP NAVBAR ======= -->
    <div class="topNav"><a name="navbar_top">
    </a><a href="#skip-navbar_top" title="Skip navigation links"></a><a name="navbar_top_firstrow">

    </a>
        <ul class="navList" title="Navigation">
            <li class="navBarCell1Rev"><a href="${overview}">Back to overview</a></li>
            <!--
            <li><a href="package-summary.html">Package</a></li>
            <li class="navBarCell1Rev">Class</li>
            <li><a href="class-use/AbstractBorder.html">Use</a></li>
            <li><a href="package-tree.html">Tree</a></li>
            <li><a href="../../../deprecated-list.html">Deprecated</a></li>
            <li><a href="../../../index-files/index-1.html">Index</a></li>
            <li><a href="../../../help-doc.html">Help</a></li>
            -->
        </ul>
        <div class="aboutLanguage"><em><strong>HL7 CIMI Reference Model Version 0.0.2</strong></em></div>
    </div>
    <div class="subNav">
    <!-- ========= END OF TOP NAVBAR ========= -->
        <h1>A Fast Overview of the CIMI Model</h1>
        <h2>The CIMI Reference Model Modules</h2>
        <p>The CIMI Reference Model consists of the following three modules:
            <ol>
                <li><a href="./CIMI_Reference_Model/CIMI_Reference_Model_info.html">: The CIMI Core Reference Model</a></li>
                <li><a href="./CIMI_Foundation_RM/CIMI_Foundation_RM_info.html">: The CIMI Foundation Reference Model</a></li>
                <li><a href="./CIMI_Clinical/CIMI_Clinical_info.html">: The CIMI Clinical Reference Model</a></li>
            </ol>
        </p>
        <p>
            The Core Reference Model contains the following packages:
            <ol>
                <li><a href="./CIMI_Reference_Model/Core/Core_info.html">Core</a>: Core introduces the LOCATABLE class, the
                parent type for most CIMI classes. It also introduces ASSOCIATION_CLASS the abstract parent for all CIMI Association classes.</li>
                <li><a href="./CIMI_Reference_Model/Primitive_Types/Primitive_Types_info.html">Primitive Types</a>: Primitive_Types introduces
                the CIMI primitive types and two generic container types - Array (which may eventually be deprecated in CIMI) and List. All primitive
                types derive from the type 'Any'</li>
                <li><a href="./CIMI_Reference_Model/Data_Value_Types/Data_Value_Types_info.html">Data Value Types</a>: Data_Value_Types
                introduces the CIMI complex types. Note that CIMI complex types do not exactly coincide with FHIR types as some CIMI complex
                types are defined further down in the CIMI inheritance hierarchy under the CLUSTER type (e.g., Address). All data value types derive from the
                type DATA_VALUE. The CIMI Data Value type hierarchy is important when used for both type inferencing and within constraints intended to be
                tighted in children archetypes (equivalent to a UML redefine on a type). For instance, if a class attribute in the reference model is
                of type TEXT, archetypes may further constrain TEXT to either CODED_TEXT or PLAIN_TEXT depending on model requirements.</li>
            </ol>
        </p>
        <p>
            The Foundation Reference Model contains the following packages:
            <ol>
                <li><a href="./CIMI_Foundation_RM/Foundation/Foundation_info.html">Foundation</a>: The Foundation package introduces three very important
                specializations of the LOCATABLE class, which form the top three CIMI Clinical Reference Model hierarchies:
                    <ul>
                        <li>COMPOSITION - a class representing a document such as a patient record or a knowledge document. Compositions are
                        composed of document content entries.</li>
                        <li>CONTENT - a class representing the content of a document. Content may either be a SECTION which supports nested
                        content or an ENTRY. CIMI Clinical Statements are types of ENTRYs and they are thus first level document content items.</li>
                        <li>CLUSTER - a class representing a data structure which is not a first level entry in a composition. What this means is that
                        document entries can reference cluster specializations but CLUSTER types cannot be entered into a record as a first level document
                        entry. For instance, a document may contain an address but the address must first be referenced by an entity (e.g., a person) associated
                        with a clinical statement - a type of ENTRY and specialization of CONTENT - (e.g., subject of information).</li>
                    </ul>
                </li>
                <li><a href="./CIMI_Foundation_RM/Party/Party_info.html">Party</a>: The Party package introduces the PARTICIPATION class used in CIMI to
                represent the participation of actors (ACTOR) acting in a specific capacity (ROLE) in the performance of an activity. PARTY_RELATIONSHIP relates
                one PARTY (ACTOR/ROLE) to another.</li>
            </ol>
        </p>
        <p>
            The Clinical Reference Model contains a number of packages and classes that make up the CIMI Reference Model. The packages are not listed
            in this overview but are accessible from this tool under the package viewer (top-left frame of this documentation).
        </p>
        <h2>The CIMI Clinical Statement Pattern</h2>
        <p>The CIMI Clinical Statement is the main unit of clinical information in the CIMI model and the only specialization of ENTRY defined in the CIMI
        Clinical Reference Model at this time. Most of the other classes in CIMI are data structures that descend from CLUSTER and are referenced either directly or indirectly by
        the CIMI Clinical Statement classes</p>
        <img src="./img/class__ClinicalStatement__ClinicalStatementOverview.jpg" width="800"></img>
        <p>The Clinical Statement Pattern is a compositional pattern whereby the information that makes up a clinical statement is composed through the assembly of
        several classes. They are described below:
        <ul>
            <li>The statement topic class contains attributes relevant to the topic of the clinical statement. Statement topics may be acts such as clinical
            procedures, findings such as patient conditions, or events such as natural disaster events. Note that the topic hierarchies follow the SNOMED hierarchies of
            Finding, Procedure, and Event.</li>
            <li>The statement context class contains attributes relevant to 'mood' of the topic. For instance, a 'Procedure' topic, when combined with a 'Performed' context
            indicates that the statement is about a procedure that has been performed. If the 'Proposal' context were selected instead, the statement would be about a proposed
            procedure such as a procedure proposed by a clinical decision support system. Similarly, a finding such as Diabetes may be associated with a context of 'Absent' or
            'Known Present'. </li>
            <li>The Attribution class provides detailed information about activities relevant to the Clinical Statement class such as the authoring, verification, signing,
            and recording of a clinical statement.</li>
            <li>The SubjectOfInformation attribute is a type of participation and represents the subject of the statement which may or may not be the subject of the
            record (i.e., the subject of the composition).</li>
        </ul>
        The clinical statement pattern aligns with the SNOMED-CT Situation with Explicit Context Concept Model. Thus, clinical statements are examples of SNOMED-CT situations
        with explicit context.
        It is important to note that the association of a statement topic with a statement context in the Clinical Statement Pattern occurs at the level of the Archetype in the
        form of typing constraints. For instance, a ProcedureProposalStatement is a Clinical Statement where the topic is equal to Procedure (a specialization of StatementTopic)
        and the context is equal to Proposal (a specialization of StatementContext). Such
        constraints are specified in archetypes using the Archetype Definition Language (ADL).
        </p>
        <h2>Adding Constraints to Reference Model Patterns to Form Detailed Clinical Models</h2>
        <p>A single reference model class or related classes (often referred to as a reference model pattern) can be used to define a family of detailed clinical models
        through the progressive application of constraints. For instance, the Clinical Statement Pattern, the Statement Topic pattern, and the Statement Context Pattern
        can be constrained in ADL to form a large number of detailed clinical models representing laboratory test results. A simplified version of the process is described:
            <ol>
                <li>The LaboratoryTestResult class, a specialization of StatementTopic in the CIMI Reference model is used as a starting point. It specifies an attribute
                called 'result' of type 'DATA_VALUE' (the supertype of all CIMI Complex Types)</li>
                <li>An archetype is developed to restrict the 'result' attribute type to 'QUANTITY'. It is called QuantitativeLaboratoryTestResult. This archetype is the
                    parent archetype for a large number of quantitative laboratory test results. Other archetypes may be developed for other result types such as
                    CODED_TEXT or COUNT if needed.</li>
                <li>Another archetype is then developed from the QuantitativeLaboratoryTestResult archetype which constraints the value set bindings for all relevant coded
                fields in the archetype to values consistent with a hematocrit measurement. Such constraints may be applied directly to the QuantitativeLaboratoryTestResult
                or progressively. The progressive application of constraints allows the definition of subsets or families of detailed clinical models all sharing a base set
                of common constraints.</li>
                <li>Starting from the SuspectedOrKnownPresent StatementContext specialization, an archetype is developed that constrains the 'context' attribute to the SNOMED-CT
                context value code of 'Known Present'. The archetype is then named KnownPresentContext. This archetype may be reused for any laboratory test result.</li>
                <li>An archetype is developed on the IndivisibleClinicalStatement pattern called HematocritLaboratoryTestResultStmt which constrains the topic to the Hematocrit
                archetype defined above for the StatementTopic class and the statement context to the KnownPresentContext archetype.</li>
            </ol>
        The archetype development and application process is illustrated below:
        </p>
        <img src="img/ArchetypeApplication.jpg" width="800"></img>
        <p>Archetype hierarchies define families of detailed clinical models with higher level archetypes defining sets that subsume the sets defined by lower level
        archetypes.</p>
        <img src="img/DCM Families.jpg" width="800"></img>
    </body>
</html>