package com.adjectivecolournoun.opencsviterator

import com.opencsv.CSVReader

/**
 * Extension class adding iterator methods to {@link com.opencsv.CSVReader}
 *
 * @author Andy Duncan
 * @see CSVReader
 */
class OpenCSVExtension {

    /**
     * Return an iterator over a {@link CSVReader}
     *
     * @param self {@link CSVReader} to iterate over
     * @return an iterator over {@code self}
     *
     * @see OpenCSVIterator#OpenCSVIterator(CSVReader)
     */
    static Iterator<Map<String, String>> iterator(Iterable self) {
        if (self instanceof  CSVReader) {
            new OpenCSVIterator(self)
        } else {
            self.class.methods.find { it.name == 'iterator' }.invoke(self, null)
        }
    }
    /**
     * Return an iterator over the {@link CSVReader} with the specified column names
     *
     * @param self {@link CSVReader} to iterate over
     * @param columns column names to use
     * @return an iterator over {@code self}
     *
     * @see OpenCSVIterator#OpenCSVIterator(CSVReader, String...)
     */
    static Iterator<Map<String, String>> iterator(CSVReader self, String... columns) {
        new OpenCSVIterator(self, columns)
    }

    /**
     * Return an iterator over the {@link CSVReader} with the specified column names
     *
     * @param self {@link CSVReader} to iterate over
     * @param columns column names to use
     * @return an iterator over {@code self}
     *
     * @see OpenCSVIterator#OpenCSVIterator(CSVReader, List<String>)
     */
    static Iterator<Map<String, String>> iterator(CSVReader self, List<String> columns) {
        new OpenCSVIterator(self, columns)
    }
}
