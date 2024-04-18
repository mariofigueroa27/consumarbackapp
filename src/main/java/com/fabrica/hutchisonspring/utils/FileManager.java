package com.fabrica.hutchisonspring.utils;

import com.fabrica.hutchisonspring.config.ApplicationProperties;
import com.fabrica.hutchisonspring.utils.vm.ValidatorItem;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class FileManager {

    private final ApplicationProperties applicationProperties;

    public FileManager(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public <T> File toFlatFile(Class<T> clazz, List<ValidatorItem<T>> list, Boolean hasErrors) throws IOException, IntrospectionException {
        if (!hasErrors) {
            var okFile = new File(
                    applicationProperties.getBatchFolder().getPath() +
                            clazz.getAnnotation(Table.class).name() +
                            applicationProperties.getBatchFolder().getOkSuffix() +
                            applicationProperties.getBatchFolder().getExtension()
            );
            var writer = new FileWriter(okFile);
            List<PropertyDescriptor> props = ExcelUtils.getDescriptors(clazz, true, true);
            list.forEach(elm -> {
                try {
                    StringBuilder row = this.itemToCsvString(props, elm);
                    row.append("\r\n");
                    writer.write(row.toString());
                } catch (InvocationTargetException | IllegalAccessException | IOException | IntrospectionException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
            return okFile;
        }
        var errFile = new File(
                applicationProperties.getBatchFolder().getPath() +
                        clazz.getAnnotation(Table.class).name() +
                        applicationProperties.getBatchFolder().getErrorSuffix() +
                        applicationProperties.getBatchFolder().getExtension()
        );
        var writer = new FileWriter(errFile);
        List<PropertyDescriptor> props = ExcelUtils.getDescriptors(clazz, true, true);
        list.forEach(elm -> {
            try {
                StringBuilder row = this.itemToCsvString(props, elm);
                elm.getErrors().forEach(err -> {
                    row.append(';').append(err);
                });
                row.append("\r\n");
                writer.write(row.toString());
            } catch (InvocationTargetException | IllegalAccessException | IOException | IntrospectionException e) {
                e.printStackTrace();
            }
        });
        writer.close();
        return errFile;
    }

    private <T> StringBuilder itemToCsvString(List<PropertyDescriptor> props, ValidatorItem<T> elm) throws IllegalAccessException, InvocationTargetException, IntrospectionException {
        StringBuilder row = new StringBuilder();
        for (int p = 0; p < props.size(); p++) {
            var attribute = props.get(p).getReadMethod().invoke(elm.getItem());
            if (attribute != null && !attribute.toString().contains("{") && !attribute.toString().startsWith("[")) {
                if (p == 0) {
                    row.append(attribute);
                } else {
                    row.append('|').append(attribute);
                }
            } else if (attribute != null && attribute.toString().contains("{") && !attribute.toString().startsWith("[")) {
                var attrDescriptors = ExcelUtils.getDescriptors(attribute.getClass(), true, true);
                attrDescriptors.forEach(attrElm -> {
                    if (attrElm.getName().equals("id")) {
                        try {
                            row.append('|').append(attrElm.getReadMethod().invoke(attribute));
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else if (attribute == null) {
                if (p != 0) row.append('|');
            }
        }
        return row;
    }
}
