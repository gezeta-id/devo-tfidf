package net.tinselcity.devo.helpers;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.io.File;

public class ValidDirectory implements IParameterValidator {
    public void validate(String name, String value) throws ParameterException {
        File f = new File(value);
        if(!f.exists() || !f.isDirectory() || !f.canRead()) {
            throw new ParameterException("Directory '" + value + "' can't be found or is not a readable directory.");
        }
    }
}
