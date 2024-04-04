package com.kpanchal.lspp.args;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.DefaultUsageFormatter;

public class Usage extends DefaultUsageFormatter {

    public Usage(JCommander commander) {
        super(commander);
    }
    
    @Override
    public void usage(StringBuilder out, String indent) {

    }
}
