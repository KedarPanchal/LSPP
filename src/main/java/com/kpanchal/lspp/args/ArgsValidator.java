package com.kpanchal.lspp.args;

import java.util.Map;

import com.beust.jcommander.IParametersValidator;
import com.beust.jcommander.ParameterException;

public class ArgsValidator implements IParametersValidator {
    @Override
    public void validate(Map<String, Object> parameters) throws ParameterException {
        if (((int)(parameters.get("--depth"))) > 0 && parameters.get("--search") != null) {
            throw new ParameterException("--depth and --search and mutually exclusive");
        }
    }
}
