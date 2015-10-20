# Archie: ADL 2.0 parser

An ADL 2.0 parser for use in openEHR-implementations, written in Java. Based on the ANTLR-grammar by Thomas Beale at https://github.com/openehr/adl-antlr . See also www.openehr.org

## Build

You need Java 8 and Gradle to build:

```sh
./gradlew build
```

To install to your local maven repository:

```sh
./gradlew install
```

## Usage

```java
Archetype archetype = new ADLParser().parse(adlFile);
APathQuery query = new APathQuery("/context[id1]/items[id2]/value");
ArchetypeModelObject object = query.find(archetype.getDefinition());
String text = archetype.getTerminology().getTermDefinition("en", "id2").getText();
```

## Features
This is work in progress, but already usable for some situations. 

What it features:

- ADL 2.0 parsing, including tuples
- basic Archetype Object Model implementation
- very basic apath-queries - absolute paths with nodeid only so far, you can add a 'and name=""' clause if you want to, but it will be ignored
- rather basic test coverage

What we want this to do in the future:
- Sibling order parsing
- Rules parsing
- A template flattener
- Many convenience methods in the archetype object model
- More extended APath-queries
- A listener to easily walk the tree of archetypes describing an openEHR reference model
- A reference model implementation?
- Probably rule evaluation
- ADL serialization
- Many more tests

Known issues:
- The ADL ANTLR grammar still has some issues - at least with regular expression parsing and sibling order. Until this is fixed, not all ADL-files will parse.

## Contributions

Feel free to create a pull request or use the github issue tracker to report issues.

## License

This software is licensed under the Apache license. See LICENSE.TXT.
