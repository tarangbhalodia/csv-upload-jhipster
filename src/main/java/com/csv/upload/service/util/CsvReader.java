package com.csv.upload.service.util;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static <T> List<T> parse(File csvFile, Class<T> clazz) throws Exception {

        List<T> list = new ArrayList<>();

        ColumnPositionMappingStrategy columnPositionMappingStrategy = new ColumnPositionMappingStrategy();
        columnPositionMappingStrategy.setType(clazz);

        Reader reader = new BufferedReader(new FileReader(csvFile));
        CsvToBean cb = new CsvToBeanBuilder(reader)
            .withType(clazz)
            .withMappingStrategy(columnPositionMappingStrategy)
            .build();

        for (T t : (Iterable<T>) cb) {
            list.add(t);
        }
        reader.close();
        return list;
    }
}
