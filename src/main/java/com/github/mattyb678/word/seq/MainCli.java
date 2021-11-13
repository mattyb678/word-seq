package com.github.mattyb678.word.seq;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Help.Ansi;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.concurrent.Callable;

@Command(name = "word-seq", version = "1.0", mixinStandardHelpOptions = true)
public class MainCli implements Callable<Integer> {

    @Parameters
    File[] files;

    @Option(names = {"-l", "--limit"}, description = "Number of rows to show", defaultValue = "100")
    int limit;

    Sequencer sequencer = new Sequencer();

    public static void main(String[] args) {
        int exitCode = new CommandLine(new MainCli()).execute(args);
        System.exit(exitCode);
    }

    public Integer call() throws Exception {
        if (files != null) {
            System.out.println(Ansi.AUTO.string("@|bold,green, Sequencing " + files.length + " files|@"));
            for (File f : files) {
                sequencer.accept(f, limit);
            }
        } else {
            sequencer.acceptSystemIn(limit);
        }
        return 0;
    }
}
