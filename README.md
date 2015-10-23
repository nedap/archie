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

## Features
This is work in progress, but already usable for some situations. 

What it features:

- ADL 2.0 parsing, including tuples
- basic Archetype Object Model implementation
- very basic apath-queries - paths with nodeIds and paths with meanings.
- A listener to more easily walk the tree of archetypes describing an openEHR reference model
- A far from complete Flattener implementation
	- with id overriding if specified in template
	- with RMTypeName setting
	- with some basic attribute overriding/adding
	- with archetype root expansion
	- without terminology merging
	- without name overriding
- rather basic test coverage

What we want this to do in the future:
- Temporal constraint parsing
- Rules parsing
- Archetype metadata parsing
- Proper archetype slot assertions parsing (and tools to evaluate?)
- Annotations parsing
- A fully featured flattener
- Many more convenience methods in the archetype object model
- More extended APath-queries
- A reference model implementation?
- Probably rule evaluation
- ADL serialization (to ADL and perhaps JSON and XML)
- Many more tests

Known issues:
- The ADL ANTLR grammar still has some issues, mainly with regular expression parsing, but also some more issues. You might not even notice this in basic use cases

## Contributions

Feel free to create a pull request or use the github issue tracker to report issues.

## License

This software is licensed under the Apache license. See the LICENSE and NOTICE files.
