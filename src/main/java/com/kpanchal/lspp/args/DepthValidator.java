package com.kpanchal.lspp.args;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class DepthValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        if (Integer.parseInt(value) < 0) {
            throw new ParameterException("Error: Depth should be greater than 0");
        }
    }
}