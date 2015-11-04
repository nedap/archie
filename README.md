# Archie: ADL 2.0 parser    

A work-in-progress ADL 2.0 parser for use in openEHR-implementations, written in Java. Based on the ANTLR-grammar by Thomas Beale at https://github.com/openehr/adl-antlr . See also www.openehr.org.

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

### Constraints imposed by default by the reference model

The openEHR specification mentions that cardinality is not a required field, because there already is a cardinality constraint in the model being described by the Archetype. is_multiple has a similar source.

That means you need knowledge about the reference model to correctly fill an archetype object model.

That's what the RMConstraintImposer does. The ADLParser can be configured to directly use it:

```
Archetype archetype = new ADLParser(new RMConstraintImposer()).parse(adlFile);
```

If you do this and no cardinality is specified: ELEMENT.value will have a multiplicity interval of 0..1, isMultiple false. ITEM_TREE.items will have a multiplicity interval of 0..*, isMultiple true. ADMIN_ENTRY.data will have a multiplicity interval of 1..1, isMultiple false. If a cardinality is specified in the ADL, it will override the default constraint.

It only sets default values for the attributes that are specified in the ADL, not every possible attribute in the reference model. If you want this otherwise, you can create a subclass of the RMConstraintImposer to act differently. If you do, we would be happy with a pull request.

To do this for other models than the reference model, have a look at the superclasses of RMConstraintImposer - you can easily write your own.


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

And it handles it for you.

A helper function for locally defined ac/at-codes that works in operational templates will be implemented soon - or do it yourself and create a pull request :)

### Archetype tree walking for openEHR reference models

You can implement the RMArchetypeTreeListener or the BaseRMArchetypeTreeListener and you'll get a specific callback for every reference model object. Very little instanceof calls needed there.

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

If someone wants to do a full Jackson extension for odin, plus perhaps ODIN-serialization support, it is welcome. It is not currently a priority for us.

### Reference model

A basic reference model implementation is available. It has not yet been tested, except for use as a source for the RMConstraintImposer.

## Status

This is work in progress, but already usable for some situations. 

What we want this to do in the future:
- Full rules parsing, once the adl-antlr grammar supports this fully
- A fully featured flattener
- Many more convenience methods in the archetype object model
- More complete APath-queries
- A more complete reference model implementation
- Probably rule evaluation
- ADL serialization (to ADL and perhaps JSON and XML)
- Many more tests
- ...

## Contributions

Feel free to create a pull request or use the github issue tracker to report issues.

## License

This software is licensed under the Apache license. See the LICENSE and NOTICE files.
