package com.effisoft.kata.domain.core;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class MultService extends OperationService<Integer> {

    private static Logger logger = Logger.getLogger(MultService.class);

    public MultService() {
        super("([0-9]*)\\*([0-9]*)");
    }

    @Override
    public Integer compute(Integer val1, Integer val2) {
        Integer result = null;

        Integer unixSh = tryComputeUnixSh(val1, val2);
        if (unixSh != null) {
            result = unixSh;
        }

        Integer winCmd = tryComputeWinCmd(val1, val2);
        if (winCmd != null) {
            result = winCmd;
        }

        return result;
    }

    private Integer tryComputeWinCmd(Integer val1, Integer val2) {
        String[] commands = {"cmd.exe", "/c", "set", "/a", val1 + "*" + val2};
        return tryCompute(commands);
    }

    private Integer tryComputeUnixSh(Integer val1, Integer val2) {
        String[] commands = {"echo", "$((" + val1 + "*" + val2 + "+))"};
        return tryCompute(commands);
    }

    private Integer tryCompute(String[] commands) {
        Integer result = null;
        try {
            ProcessBuilder pb = new ProcessBuilder(commands);
            Process process = pb.start();
            process.waitFor();
            try (BufferedReader out = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                result = Integer.valueOf(out.lines().collect(Collectors.joining()));
            }
            logger.info("process "+commands[0]+" succeeded, it was a " + process.getClass().getCanonicalName());
        } catch (IOException | InterruptedException | NoSuchElementException e) {
            logger.debug(e.getMessage(), e);
        }
        return result;
    }
}
