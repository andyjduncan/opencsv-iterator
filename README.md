opencsv-iterator
================

Conveniently wraps [CSVReader](http://opencsv.sourceforge.net/apidocs/au/com/bytecode/opencsv/CSVReader.html) from the
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

The column names can be retrieved from the columns property on the iterator.