package com.adjectivecolournoun.opencsviterator

import com.opencsv.CSVReader
import spock.lang.Ignore
import spock.lang.Specification

class TestOpenCSVExtension extends Specification {

    @Ignore('No longer works')
    void 'adds iterator to CSVReader'() {
        given:
        def rows = '''column1, column2
value1, value2'''
        def stringReader = new StringReader(rows)
        def csvReader = new CSVReader(stringReader)

        when:
        def read = csvReader.collect {
            it.column1 + it.column2
        }

        then:
        read == ['value1value2']
    }

    void 'does not replace iterator on non CSVReader objects'() {
        given:
        def anIterable = new Iterable<String>() {

            @Override
            Iterator<String> iterator() {
                ['item 1', 'item 2'].iterator()
            }
        }

        when:
        def read = anIterable.collect { it }

        then:
        read == ['item 1', 'item 2']
    }

    void 'supports supplying column names in an array'() {
        given:
        def rows = 'value1, value2'
        def stringReader = new StringReader(rows)
        def csvReader = new CSVReader(stringReader)

        when:
        def read = csvReader.iterator('column1', 'column2').collect { it.column1 + it.column2 }

        then:
        read == ['value1value2']
    }

    void 'supports supplying column names in a list'() {
        given:
        def rows = 'value1, value2'
        def stringReader = new StringReader(rows)
        def csvReader = new CSVReader(stringReader)

        when:
        def read = csvReader.iterator(['column1', 'column2']).collect { it.column1 + it.column2}

        then:
        read == ['value1value2']
    }
}
