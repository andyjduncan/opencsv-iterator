package com.adjectivecolournoun.opencsviterator

import com.opencsv.CSVReader
import spock.lang.Specification

class TestOpenCSVIterator extends Specification {

    CSVReader csvReader

    OpenCSVIterator iterator

    void 'reads field names from first row'() {
        given:
        csv_file('field1, field2')

        when:
        new_iterator()

        then:
        iterator.columns == ['field1', 'field2']
    }

    void 'returns rows as maps'() {
        given:
        csv_file('''field1, field2
value 1, value 2''')

        when:
        new_iterator()

        then:
        read == [[field1: 'value 1', field2: 'value 2']]
    }

    void 'returns empty fields as nulls'() {
        given:
        csv_file('''field1, field2
value 1,''')

        when:
        new_iterator()

        then:
        read == [[field1: 'value 1', field2: null]]
    }

    void 'uses supplied fields as field names'() {
        given:
        csv_file('value 1, value 2')

        when:
        new_iterator_with_fields 'field1', 'field2'

        then:
        read == [[field1: 'value 1', field2: 'value 2']]
    }

    void 'disallows short lines'() {
        given:
        csv_file('value')

        when:
        new_iterator_with_fields 'field1', 'field2'
        read

        then:
        thrown IllegalArgumentException
    }

    void 'disallows long lines'() {
        given:
        csv_file('value 1, value 2')

        when:
        new_iterator_with_fields 'field1'
        read

        then:
        thrown IllegalArgumentException
    }

    void 'does not allow remove'() {
        given:
        csv_file('value 1')

        when:
        new_iterator_with_fields('field1')
        iterator.remove()

        then:
        thrown UnsupportedOperationException
    }

    void 'throws NoSuchElementException after last element'() {
        given:
        csv_file('field1')

        when:
        new_iterator()
        iterator.next()

        then:
        thrown NoSuchElementException
    }

    private void csv_file(String fileContents) {
        def csvFileReader = new StringReader(fileContents)
        csvReader = new CSVReader(csvFileReader)
    }

    private void new_iterator() {
        iterator = new OpenCSVIterator(csvReader)
    }

    private void new_iterator_with_fields(String... fields) {
        iterator = new OpenCSVIterator(csvReader, fields)
    }

    private List getRead() {
        def read = []
        iterator.collect(read) {
            it
        }
        read
    }
}