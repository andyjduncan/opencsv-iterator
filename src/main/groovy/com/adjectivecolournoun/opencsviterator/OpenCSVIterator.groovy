package com.adjectivecolournoun.opencsviterator

import com.opencsv.CSVReader

/**
 * A wrapper around {@link com.opencsv.CSVReader} to provide an {@link java.util.Iterator}
 * over the rows.
 *
 * @author Andy Duncan
 * @see com.opencsv.CSVReader
 */
class OpenCSVIterator implements Iterator<Map<String, String>> {

    private final CSVReader reader

    private final List columns

    private String[] nextRow

    private int currentLine = 0

    /**
     * Create a new CSV iterator using the first row as column names
     *
     * @param reader {@link com.opencsv.CSVReader} to read
     */
    OpenCSVIterator(final CSVReader reader) {
        this.reader = reader
        columns = reader.readNext().collect { it.trim() }.asImmutable()
    }

    /**
     * Create a new CSV iterator using the specified column names
     *
     * @param reader {@link com.opencsv.CSVReader} to read
     * @param columns Column names
     */
    OpenCSVIterator(final CSVReader reader, final String... columns) {
        this.reader = reader
        this.columns = (columns as List).asImmutable()
    }

    /**
     * Create a new CSV iterator using the specified column names
     *
     * @param reader {@link com.opencsv.CSVReader} to read
     * @param columns Column names
     */
    OpenCSVIterator(final CSVReader reader, final List<String> columns) {
        this.reader = reader
        this.columns = columns.asImmutable()
    }

    /**
     * Return the column names
     *
     * @return an immutable list of column names
     */
    List<String> getColumns() {
        columns
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    boolean hasNext() {
        currentLine++
        nextRow = reader.readNext()
        nextRow != null
    }

    /**
     * Returns the next row.  Rows are returned as a map of column names to row values.  The values are trimmed,
     * and any empty values are not included in the map.
     *
     * @return an immutable map of column names to values
     * @throws {@link java.util.NoSuchElementException} if there are no more rows
     * @throws {@link java.lang.IllegalArgumentException} is there are too many or too few values in the row
     */
    @Override
    Map<String, String> next() {
        validateNextRow()
        validateLength()

        def next = [:]
        columns.eachWithIndex { field, i ->
            next[field] = nextRow[i].trim() ?: null
        }

        next.asImmutable()
    }

    private validateNextRow() {
        if (!nextRow) throw new NoSuchElementException()
    }

    private validateLength() {
        if (nextRow.size() < columns.size()) {
            throw new IllegalArgumentException("Not enough fields at line $currentLine")
        } else if (nextRow.size() > columns.size()) {
            throw new IllegalArgumentException("Too many fields at line $currentLine")
        }
    }

    /**
     * Not supported
     *
     * @throws {@link java.lang.UnsupportedOperationException}
     */
    @Override
    void remove() {
        throw new UnsupportedOperationException()
    }
}
