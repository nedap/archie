# Archie: OpenEHR Library 

Archie is an openEHR Library written in Java, which can be used to implement openEHR tools and systems. See http://www.openehr.org for information about openEHR.
Archie works with the most recent versions of openEHR. This includes ADL version 2.
It contains an ADL 2 parser and an archetype object model implementation, as well as the EHR part of the reference model implementation.

It uses the ANTLR adl-grammar written by Thomas Beale at https://github.com/openehr/adl-antlr.

It is licensed under the Apache license.

## dependency

In gradle, include this dependency in your build.gradle:

```gradle
dependencies {
    compile 'com.nedap.healthcare:archie:0.1.2'
}
```

or if you use maven, in your pom.xml

```xml
<dependency>
    <groupId>com.nedap.healthcare</groupId>
    <artifactId>archie</artifactId>
    <version>0.1.2</version>
</dependency>
```

## Build

[![Build Status](https://travis-ci.org/nedap/archie.svg?branch=master)](https://travis-ci.org/nedap/archie)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/dda8ce2b837a40b5b50cf52dae95764d)](https://www.codacy.com/app/pieter-bos/archie?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=nedap/archie&amp;utm_campaign=Badge_Grade)

You need Java 8 to build. Check out the source and type:

```sh
./gradlew build
```

To install to your local maven repository for use in other gradle or maven projects:

```sh
./gradlew install
```

## Usage

### Parsing and Archetype queries

```java
Archetype archetype = new ADLParser().parse(adlFile);

APathQuery query = new APathQuery("/context[id1]/items[id2]/value");
ArchetypeModelObject object = query.find(archetype.getDefinition());

ArchetypeModelObject sameObject = archetype.getDefinition().itemAtPath("/context[id1]/items[id2]/value")

CAttribute attribute = archetype.getDefinition()
    .getAttribute("context").getChild("id1").getAttribute("items");

```

Or, if you prefer your paths to be human readable, you could do:
```java
APathQuery query = new APathQuery("/context[systolic]/items");

CAttribute attribute = archetype.getDefinition()
    .getAttribute("context").getChildByMeaning("systolic").getAttribute("items");
    attribute.getLogicalPath(); // is 'context[systolic]/items'
```

### Serializing

Archetype models can be serialized to ADL thanks the the serializer written by @markopi64. To use:

```java
String serialized = ADLArchetypeSerializer.serialize(archetype);
```

### Flattener and Operational Template creation

First, create an ArchetypeRepository - create your own or use the supplied in memory SimpleArchetypeRespository. You need it to contain all Archetypes that are to be used, in parsed form. Then do:

```java
SimpleArchetypeRepository repository = new SimpleArchetypeRepository();
for(Archetype archetype:allArchetypes) {
    repository.addArchetype(archetype);
}


Archetype flattened = new Flattener(repository).createOperationalTemplate(true).flatten(archetypeToBeFlattened);
```

You will get a flattened copy of the original archetypes. This means all specalizations in your archetype will be merged with any parent archetypes.
If you opted to create an operational template, also the occurrences of use_archetype and use_node will have been replaced with a copy of the contents of the specified archetype and node, and the component terminologies will have been added to the operational template.

### Terminology: texts and descriptions for your archetypes

You can directly use archetype.terminology() to get the meaning in any desired language. Archie contains convenience methods to make this easier for you in several ways. Since you likely will want to use the same language for many calls in the same thread, you can specify the language you are using as a thread local variable and just directly get terms for the Archetype Constraints.

```java
ArchieLanguageConfig.setThreadLocalMeaningAndDescriptionLanguage("nl");

CObject cobject = archetype.getDefinition().getAttribute("context").getChild("id1");
ArchetypeTerm term = cobject.getTerm();
logger.info("and the archetype term text in Dutch is: " + term.getText());
```

This just works in all cases - with the normal terminology of an unflattened archetype and with component terminologies from operational templates.

Of couse, you can also specify the language yourself:

```java
CObject cobject = archetype.getDefinition().getAttribute("context").getChild("id1");
ArchetypeTerm term = archetype.getTerm(cobject, "en");
logger.info("and the archetype term text in English is: " + term.getText());
```

Or you could access the terminology itself:

```java
archetype.getTerminology().getValueSets();
operationalTemplate.getComponentTerminologies()
```

For terminology constraints with locally defined values:

```java
CTerminologyCode cTerminologyCode = object.itemAtPath("/somePath...");
List<TerminologyCodeWithArchetypeTerm> = cTerminologyCode.getTerms();
```

or

```java
archetype.getTerm(cTerminologyCode, "at15", "en");
```

### Default constraints from the reference model

An archetype constrains instances of the reference model. But a reference model also has some default constraints. For example, an observation has a data field, which is a single field - not a list or set. This has implications for default cardinality and existence constraints, and for example the ```isMultiple()``` and ```isSingle()``` methods. You can apply these constraints so you get them set explicitly in the resulting archetype-object. To do so:

```java
Archetype archetype = ADLParser.withRMConstraintsImposer().parse(adlFile);
```

Or use the RMConstraintsImposer class yourself instead of directly from the parser.


### Use a different reference model implementation

Archie uses its own reference model by default, but it can use any reference model you like. To do so, you can implement your own Constraints imposer, your own reference model info lookup and some other classes, and use those in the right places. To do so, create an implementation of each of the following abstract classes and interfaces:

- ModelNamingStrategy
- ModelInfoLookup
- ModelConstraintImposer or ReflectionConstraintImposer

### Intervals and primitive objects

The Interval has its ```has(value)``` method implemented. So you can do:

```java
new Interval<>(2,4).has(3); //returns true
Interval.upperUnbounded(2).has(1); //returns false
```

CPrimitiveObject has an ```isValidValue(value)``` method, so you can do:

```java
CString cString = new CString();
cString.addConstraint("test");
cString.addConstraint("/more te+st/");
cString.isValidValue("test"); //returns true
cString.isValidValue("more teeeeest"); //returns true - regexp matching
cString.isValidValue("mooooore test"); //returns false
```

ISO-8601 date constraint patterns are not yet implemented.

### Archetype tree walking for openEHR reference models

Because the getAttributes() has been implemented on the CObject-class instead of CComplexObject, creating a tree walker is just a few lines of code:

```java
public void walk(CObject cobject) {
	for(CAttribute attribute:cobject.getAttributes()) {
		for(CObject child:attributes.getChildren()) {
			walk(child);
		}
	}
}
```

### ODIN

ODIN is a JSON/YAML like notation used as part of ADL, for meta-data, terminologies and annotations. See https://github.com/openehr/odin for what more it can do. To our knowledge it's not used widely outside of ADL/openEHR. Archie can map ODIN data directly to Java-objects using Jackson. 

```
	YourType type = OdinObjectParser.convert(odinText, YourType.class);
```

It works by first converting to JSON, then binding that to objects with Jackson.
This means you can also convert ODIN to JSON. 

```
	String json = new OdinToJsonConverter().convert(odinText);
```

Converting to JSON is a great way to get ODIN object mapping with very little code and it parses enough to parse the ODIN used in ADL 2. Odin has a few advanced features not generally used in archetype metadata, which are object references and intervals. These are currently not yet supported, although it is very possible to implement them using a custom deserializer for the interval and JSON references for the object references.

## Reference Model

### Reference model object creation

The RMObjectCreator creates empty reference model objects based on constraints. It can also set values based on attribute names. You can use it to create a reference model based on an archetype and user input. To create an empty reference model based on an archetype, you could work further on this example:


```java
 public RMObject constructEmptyRMObject(CObject object) {
        RMObject result = creator.create(object);
        for(CAttribute attribute: object.getAttributes()) {
            List<Object> children = new ArrayList<>();
            for(CObject childConstraint:attribute.getChildren()) {
                if(childConstraint instanceof CComplexObject) {
                    RMObject childObject = constructEmptyRMObject(childConstraint);
                    children.add(childObject);
                }
            }
            //will fail when a single valued attribute has two values, check code in TestUtil.java for how to solve.
            creator.set(result, attribute.getRmAttributeName(), children);
        }
        return result;
    }
```

Setting primitive object values works in a similar way, with ```creator.set(...)```, or by setting them explicitly on the reference model object directly.

### Reference model APath queries

```java
List<Object> items = rmObject.itemsAtPath("/data[id2]/items");
```
Or if you want apath-expressions resolving to that single item together with every object returned, you can use the low-level method:

```java
List<RMObjectWithPath> itemsWithUniquePaths = new APathQuery("/data[id2]/items").findList(ArchieRMInfoLookup.getInstance(), rmObject);
```

#Experimental features

The following features are experimental. This means its working will likely change somewhat in the near future, but they can already be used.

## Full XPath support on reference model

The RMQueryContext provides full XPath 1-support on reference model instances. APath shorthand notations like /items[id2] will automatically be converted to the corresponding xpath. JAXB's Binder is used to get a DOM on which to run XPath. You can choose your own JAXB-implementation with the usual mechanisms, although only the internal java one has been tested.

## Rule evaluation

Basic rule evaluation is implemented, but the implementation is still experimental with missing features. The API and implementation is likely to be changed in the near future. See RuleEvaluation.java on how to use.

The syntax has a few deviations from the standard, for now. This can be changed when we switch to a whitespace-aware parser, but until then the grammar would contain ambiguities otherwise:

- every assertion MUST end with a ';'
- The specs say ```for_all $event in /events $event/something > 5``` is valid. We follow the XPath 2 syntax for this: ```every $event in /events satisfies $event/something > 5```. for_all and ∀ work, the XPath keyword 'every' is non-standard.

Rules are very similar to XPath expressions, with a few exceptions. However, the specification is lacking a formal definition of how to handle several kind of multiplicities. Where multiplicities occur in the paths in rules outside of a for_all rule, the W3C XPath 1 specification is used.


## Status

The project is quite usable for when you want to create an EHR implementation, but it is still missing many openEHR features which could make this library easier to use and more complete.

What we want this to do in the future:
- Date Constraint parsing with patterns, not just intervals
- More tests
- Validating if an RM object conforms to a certain archetype, apart from the rules
- ...

## Contributions

Feel free to create a pull request or use the github issue tracker to report issues.

## License

This software is licensed under the Apache license. See the LICENSE and NOTICE files.
