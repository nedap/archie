# Archie: ADL 2.0 parser    

ADL 2.0 parser, Archetype Object Model and Reference Model implementation for use in openEHR-implementations, written in Java. Based on the ANTLR-grammar by Thomas Beale at https://github.com/openehr/adl-antlr . See also www.openehr.org.

## Build

[![Build Status](https://travis-ci.org/nedap/archie.svg?branch=master)](https://travis-ci.org/nedap/archie)

You need Java 8 and Gradle to build. Then type:

```sh
./gradlew build
```

To install to your local maven repository for use in other gradle or maven projects:

```sh
./gradlew install
```

## Usage

### Parsing and Queries

```java
Archetype archetype = new ADLParser().parse(adlFile);
APathQuery query = new APathQuery("/context[id1]/items[id2]/value");
ArchetypeModelObject object = query.find(archetype.getDefinition());
CAttribute attribute = archetype.getDefinition()
    .getAttribute("context").getChild("id1").getAttribute("items");
String text = archetype.getTerminology().getTermDefinition("en", "id2").getText();
```

Or, if you prefer your paths to be human readable, you could do:
```java
APathQuery query = new APathQuery("/context[systolic]/items");
CAttribute attribute = archetype.getDefinition()
    .getAttribute("context").getChildByMeaning("systolic").getAttribute("items");
    attribute.getLogicalPath(); // is 'context[systolic]/items'
```

### Flattener

First, create an ArchetypeRepository - create your own or use the supplied in memory SimpleArchetypeRespository. You need it to contain all Archetypes that are to be used, in parsed form. Then do:

```java
SimpleArchetypeRepository repository = new SimpleArchetypeRepository();
repository.addArchetype(archetype1); //repeat for all your archetypes
Archetype flattened = new Flattener(repository).createOperationalTemplate(true).flatten(archetype);
```

### Terminology

You can of course directly use archetype.terminology() to get the meaning in any desired language. But that doesn't work with OperationalTemplates because you need to handle component terminologies. So instead, do:

```java
CObject cobject = archetype.getDefinition().getAttribute("context").getChild("id1");
ArchetypeTerm term = archetype.getTerm(cobject, "en");
logger.info("and the archetype term text is: " + term.getText());
```

This takes component terminologies into account as well. Alternatively, use ```archetype.getTerminology(cobject)``` to get the full terminology object for that path.

### Intervals and primitive objects

The Interval has its ```has(value)``` method implemented. So you can do:

```java
new Interval<>(2,4).has(3); //returns true
Interval.upperUnbounded(2).has(1); //returns false
```

CPrimitiveObject has a isValidValue(value) method, so you can do:

```java
CString cString = new CString();
cString.addConstraint("test");
cString.addConstraint("/more te+st");
cString.isValidValue("test"); //returns true
cString.isValidValue("more teeeeest"); //returns true - regexp matching
cString.isValidValue("mooooore test"); //returns false
```

ISO-8601 date constraint patterns are not yet implemented

### Constraints imposed by default by the reference model

The openEHR specification mentions that cardinality is not a required field, because the reference model has some default constraints. The value of is_multiple is determined in the same way,

That means you need knowledge about the reference model to correctly fill an archetype object model.That's what the RMConstraintImposer does. For many use cases, you'll want to use it:

```
Archetype archetype = new ADLParser(new RMConstraintImposer()).parse(adlFile);
```

Archetypes can be used to further constrain any model. To use this for a different model than the reference model, see the superclasses of RMConstraintImposer - you can easily write your own.


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

There's also a specific implementation available with named predefined callbacks for every RM object constraint. See RMArchetypeTreeListener and BaseRMArchetypeTreeListener.

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

Converting to JSON is a great way to get ODIN object mapping with very little code and it parses enough to parse the ODIN used in ADL 2. However, ODIN has a few features that are not supported natively in JSON, specifically object references and intervals. It's very possible to add interval support, object references are tricky.

If someone wants to do a full Jackson extension for ODIN, plus perhaps ODIN-serialization support, it is welcome. It is not currently a priority for us.

### Reference model

A reference model implementation is available. It deserializes with Jackson into json, but probably not yet in a standard way.

## Status

The project is quite usable for when you want to create an EHR implementation. It is not yet usable for when you want to create an ADL-editor, mainly because there is no serialization to ADL-files.

What we want this to do in the future:
- Date Constraint parsing with patterns, not just intervals
- Tests for the reference model
- Many more convenience methods in the archetype object model
- More complete APath-queries
- Full rules parsing, once the adl-antlr grammar supports this fully
- Probably rule evaluation
- ADL serialization (to ADL and perhaps JSON and XML)
- Many more tests
- ...

## Contributions

Feel free to create a pull request or use the github issue tracker to report issues.

## License

This software is licensed under the Apache license. See the LICENSE and NOTICE files.
