OpenCSVIterator
===============

[![Build Status](https://travis-ci.org/andyjduncan/opencsv-iterator.svg?branch=master)](https://travis-ci.org/andyjduncan/opencsv-iterator)

Conveniently wraps `com.opencsv.CSVReader` from the
[OpenCSV project](http://opencsv.sourceforge.net/) in an Iterator

Usage
-----

OpenCSVIterator can be used in plain Java but the main intention is to provide support for Groovy iterator functions.
For example, to print each row of a CSV file:

    new File('myfile.csv').withReader { fileReader ->
        def csvReader = new CSVReader(fileReader)
        def csvIterator = new OpenCSVIterator(csvReader)
        csvIterator.each { row ->
            println row
        }
    }

The returned row object is an immutable Map of strings, mapping row values to column names.  Column names and
row values are trimmed.  Each column must be present in each row, but empty values will not be included in the row map.

Column Names
-------------

OpenCSVIterator will read the column names from the first row of the reader.  Alternatively, they can be specified
as a List or String array:

    def withList = new OpenCSVIterator(csvReader, ['field1', 'field2'])
    def withArray = new OpenCSVIterator(csvReader, 'field1', 'field2')

The column names can be retrieved from the `columns` property on the iterator.

Extension
---------

OpenCSVIterator also includes an extension module that adds iterator methods directly to `CSVReader`.  There is a
version to support the implicit Groovy iterator functions and variants to set column names.

Implicit iterator:

    def csvReader = new CSVReader(reader)
    csvReader.each {
        ...
    }
    
Array of column names:

    def csvReader = new CSVReader(reader)
    csvReader.iterator('column1', 'column2').each {
        ...
    }
    
List of column names:

    def csvReader = new CSVReader(reader)
    csvReader.iterator(['column1', 'column2']).each {
        ...
    }

From OpenCSV version 3, CSVReader began implementing Iterable and added a new `iterator()` method which returns an
iterator of `String[]`.  The extension module still replaces this method with the `Map<String, String>` version.

Get it
------

OpenCSVIterator can be obtained from Bintray [JCenter](https://bintray.com/bintray/jcenter).
Groovy is also required as a dependency of the project.

`build.gradle`

    repositories {
        jcenter()
    }
    
    dependencies {
        compile 'com.adjectivecolournoun:opencsv-iterator:2.0.2'
        compile 'org.codehaus.groovy:groovy-all:2.4.3
    }
    
`pom.xml`

    <repository>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
        <id>central</id>
        <name>bintray</name>
        <url>http://jcenter.bintray.com</url>
    </repository>
    
    <dependency>
        <groupId>com.adjectivecolournoun</groupId>
        <artifactId>opencsv-iterator</artifactId>
        <version>2.0.2</version>
    </dependency>

    <dependency>
        <groupId>org.codehaus.groovy</groupId>
        <artifactId>groovy-all</artifactId>
        <version>2.4.3</version>
    </dependency>

Licence
-------

Copyright 2014 Andy Duncan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
