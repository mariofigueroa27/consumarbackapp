package com.fabrica.hutchisonspring.utils;

import com.fabrica.hutchisonspring.utils.vm.ValidatorItem;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * see https://bezkoder.com/spring-boot-upload-excel-file-database/
 */
public final class ExcelUtils {

    public static final String HAS_ERRORS_KEY = "hasErrors";
    public static final String LIST_KEY = "list";
    public static final String NO_FILE_PATH = "no-file-path";

    private static final String[] TYPES = { "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "application/vnd.ms-excel" };

    public static <T> Map<String, Object> validateItems(List<T> list) {
        var factory = Validation.buildDefaultValidatorFactory();
        var validator = factory.getValidator();
        var hasErrors = new AtomicBoolean(false);
        var validatedList = list.stream().map(elm -> {
            ValidatorItem<T> item = new ValidatorItem<>();
            item.setItem(elm);
            var set = validator.validate(elm).stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
            if (!hasErrors.get() && !set.isEmpty()) {
                hasErrors.set(true);
            }
            item.setErrors(set);
            return item;
        }).collect(Collectors.toList());
        var mapReturn = new HashMap<String, Object>();
        mapReturn.put(HAS_ERRORS_KEY, hasErrors.get());
        mapReturn.put(LIST_KEY, validatedList);
        return mapReturn;
    }

    public static boolean hasExcelFormat(MultipartFile file) {
        for (String TYPE : TYPES) {
            if (TYPE.equals(file.getContentType())) {
                return true;
            }
        }
        return false;
    }

    public static <T> List<T> sheetToEntities(Class<T> clazz, InputStream inputStream, String sheetName, boolean hasId,
            boolean includeId) {
        try {
            Workbook workbook = WorkbookFactory.create(inputStream) /* new XSSFWorkbook(inputStream) */;
            DataFormatter formatter = new DataFormatter();
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                return null;
            }
            Iterator<Row> rowIterator = sheet.iterator();
            List<T> list = new ArrayList<>();

            int rowNumber = 0;

            List<PropertyDescriptor> propertyDescriptors = getDescriptors(clazz, hasId, includeId);
            while (rowIterator.hasNext()) {
                Row currRow = rowIterator.next();
                // to skip the header row ...
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                if (!(currRow == null || isCellEmpty(currRow.getCell(2)))) {
                    Iterator<Cell> cellIterator = currRow.iterator();
                    T t = clazz.getDeclaredConstructor().newInstance();

                    int cellNumber = 0;
                    while (cellIterator.hasNext() && cellNumber < propertyDescriptors.size()) {
                        Cell currCell = cellIterator.next();
                        if (currCell != null && currCell.getCellType() != CellType.BLANK) {
                            typeSetter(formatter, currCell, propertyDescriptors.get(cellNumber), t);
                        }
                        cellNumber++;
                    }
                    list.add(t);
                }
            }
            workbook.close();
            return list;
        } catch (IOException | IntrospectionException | NoSuchMethodException | InstantiationException
                | IllegalAccessException | InvocationTargetException | ParseException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private static <D> void typeSetter(DataFormatter formatter, Cell cell, PropertyDescriptor descriptor, D d)
            throws InvocationTargetException, IllegalAccessException, IntrospectionException, NoSuchMethodException,
            InstantiationException, ParseException {
        Class<?> propertyType = descriptor.getPropertyType();
        if (String.class.equals(propertyType)) {
            String value = formatter.formatCellValue(cell);
            value = value.trim();
            if (value.isBlank())
                value = null;
            descriptor.getWriteMethod().invoke(d, value);
        } else if (Integer.class.equals(propertyType)) {
            descriptor.getWriteMethod().invoke(d, (int) cell.getNumericCellValue());
        } else if (Long.class.equals(propertyType)) {
            descriptor.getWriteMethod().invoke(d, (long) cell.getNumericCellValue());
        } else if (Double.class.equals(propertyType)) {
            descriptor.getWriteMethod().invoke(d, cell.getNumericCellValue());
        } else if (LocalDate.class.equals(propertyType)) {
            descriptor.getWriteMethod().invoke(d, cell.getLocalDateTimeCellValue().toLocalDate());
        } else if (LocalTime.class.equals(propertyType)) {
            descriptor.getWriteMethod().invoke(d, cell.getLocalDateTimeCellValue().toLocalTime());
        } else if (Instant.class.equals(propertyType)) {
            if (cell.getCellType().equals(CellType.STRING)) {
                descriptor.getWriteMethod().invoke(d,
                        (new SimpleDateFormat("dd/MM/yyyy").parse(cell.getStringCellValue())).toInstant());
            } else {
                descriptor.getWriteMethod().invoke(d, cell.getDateCellValue().toInstant());
            }
            // to add if there is other data types ...
        } else {
            // if the instance need a foreign key, obviously this attribute must be a custom
            // class
            Object o = propertyType.getDeclaredConstructor().newInstance();
            PropertyDescriptor[] aux = Introspector.getBeanInfo(propertyType).getPropertyDescriptors();
            for (PropertyDescriptor a : aux) {
                // finding its id ...
                if (a.getName().equals("id")) {
                    a.getWriteMethod().invoke(o, (long) cell.getNumericCellValue());
                    break;
                }
            }
            descriptor.getWriteMethod().invoke(d, o);
        }
    }

    public static <T> List<PropertyDescriptor> getDescriptors(Class<T> clazz, boolean hasId, boolean includeId)
            throws IntrospectionException {
        var fields = clazz.getDeclaredFields();
        var descriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
        int difference = 1; // the serialVersionUID
        if (hasId && !includeId) {
            difference = 2;
        }
        var newDescriptors = new ArrayList<PropertyDescriptor>();
        for (int f = difference; f < fields.length; f++) {
            for (PropertyDescriptor descriptor : descriptors) {
                if (fields[f].getName().equals(descriptor.getName())) {
                    newDescriptors.add(descriptor);
                    break;
                }
            }
        }
        return newDescriptors;
    }

    private static boolean isCellEmpty(final Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return true;
        }
        return cell.getCellType() == CellType.STRING && cell.getStringCellValue().isEmpty();
    }
}
