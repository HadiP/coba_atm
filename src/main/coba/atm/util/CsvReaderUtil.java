package coba.atm.util;

import coba.atm.annotations.CsvHeader;
import coba.atm.exception.NoDefaultConstructorException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

public class CsvReaderUtil {

    private String delimiter = ",";
    private FileInputStream fileStream;
    private boolean headerDisabled = true;
    private String[] headers = new String[0];

    private CsvReaderUtil(FileInputStream fileStream) {
        this.fileStream = fileStream;
    }

    public static CsvReaderUtil getInstance(FileInputStream inputStream) {
        return new CsvReaderUtil(inputStream);
    }

    public CsvReaderUtil setDelimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    public CsvReaderUtil setFile(FileInputStream inputStream) {
        this.fileStream = inputStream;
        return this;
    }

    public CsvReaderUtil disableHeader(boolean headerDisabled) {
        this.headerDisabled = headerDisabled;
        return this;
    }

    /**
     * Read all content of file and convert it into pojo that have CommonEntity as
     * super class.
     *
     * @param entityList
     */
    public void readAll(List entityList, Class<?> entityClass) throws NoDefaultConstructorException {
        try (BufferedReader bfr = new BufferedReader(new InputStreamReader(fileStream))) {
            //synchronized (bfr) {
            if (!headerDisabled) {
                this.headers = bfr.readLine().split(delimiter);
            }
            bfr.lines().parallel().forEach(line -> {
                try {
                    Object en = entityClass.getConstructor().newInstance();
                    //convert line of csv into entity
                    String[] row = line.split(delimiter);
                    assignValueToEntity(en, en.getClass().getDeclaredFields(), row);
                    entityList.add(en);
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                         | InvocationTargetException | SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e){
                    System.out.println("Please "+ entityList.getClass().getName() +" didn't contain any empty/default constructor.");
                }
            });
            //}
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void assignValueToEntity(Object entity, Field[] fs, String[] value) throws IllegalAccessException {
        for(int i =0; i < value.length; i++){
            for(Field f : fs){
                CsvHeader ann = f.getAnnotation(CsvHeader.class);
                if(ann != null && (headerDisabled || ann.value().equals(headers[i]))){
                    f.setAccessible(true);
                    f.set(entity, convertValue(f.getType(), value[i]));
                }
            }
        }
    }

    private Object convertValue(Class<?> type, String value){
        Object resp;
        if(Double.class.getName().equals(type.getName())){
            resp = Double.valueOf(value);
        } else if (Integer.class.getName().equals(type.getName())) {
            resp = Integer.valueOf(value);
        } else if (BigDecimal.class.getName().equals(type.getName())) {
            resp = new BigDecimal(value);
        } else {
            resp = value;
        }
        return resp;
    }

}
