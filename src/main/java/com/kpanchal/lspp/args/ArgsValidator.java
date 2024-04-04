package com.kpanchal.lspp.args;

import java.util.Map;

import com.beust.jcommander.IParametersValidator;
import com.beust.jcommander.ParameterException;

public class ArgsValidator implements IParametersValidator {
    @Override
    public void validate(Map<String, Object> parameters) throws ParameterException {
        boolean[] boolArray = {parameters.get("--depth") != null, parameters.get("--search") != null, parameters.get("--search-all") != null};
        int count = 0;
        for (boolean bool : boolArray) {
            if (bool) {
                count++;
            }
        }
        if (count > 1) {
            throw new ParameterException("--depth, --search, and --search-all are mutually exclusive");
        }
    }
}