package br.com.nunius.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {
    public String[] getNullPropertiesName(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] propsDescriptor = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor propDescriptor : propsDescriptor) {
            var propName = propDescriptor.getName();
            Object sourceValue = src.getPropertyValue(propName);
            if (sourceValue == null) {
                emptyNames.add(propName);
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(resullt);
    }
}
