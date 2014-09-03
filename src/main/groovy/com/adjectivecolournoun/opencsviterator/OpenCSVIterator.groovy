package com.adjectivecolournoun.opencsviterator

import au.com.bytecode.opencsv.CSVReader

import javax.naming.OperationNotSupportedException

class OpenCSVIterator implements Iterator<Map<String, String>> {

    private final CSVReader reader

    private final List fields

    private String[] nextRow

    private int currentLine = 0

    OpenCSVIterator(final CSVReader reader) {
        this.reader = reader
        fields = reader.readNext().collect { it.trim() }.asImmutable()
    }

    OpenCSVIterator(final CSVReader reader, final String... fields) {
        this.reader = reader
        this.fields = (fields as List).asImmutable()
    }

    OpenCSVIterator(final CSVReader reader, final List fields) {
        this.reader = reader
        this.fields = fields.asImmutable()
    }

    List<String> getFields() {
        fields
    }

    @Override
    boolean hasNext() {
        currentLine++
        nextRow = reader.readNext()
        nextRow != null
    }

    @Override
    Map<String, String> next() {
        validateNextRow()
        validateLength()

        def next = [:]
        fields.eachWithIndex { field, i ->
            next[field] = nextRow[i].trim() ?: null
        }

        next.asImmutable()
    }

    private validateNextRow() {
        if (!nextRow) throw new NoSuchElementException()
    }

    private validateLength() {
        if (nextRow.size() < fields.size()) {
            throw new IllegalArgumentException("Not enough fields at line $currentLine")
        } else if (nextRow.size() > fields.size()) {
            throw new IllegalArgumentException("Too many fields at line $currentLine")
        }
    }

    @Override
    void remove() {
        throw new OperationNotSupportedException()
    }
}
