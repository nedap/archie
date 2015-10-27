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

## Status

This is work in progress, but already usable for some situations. 

What we want this to do in the future:
- Temporal constraint parsing
- Full rules parsing, once the adl-antlr grammar supports this fully
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
- ...

## Contributions

Feel free to create a pull request or use the github issue tracker to report issues.

## License

This software is licensed under the Apache license. See the LICENSE and NOTICE files.
