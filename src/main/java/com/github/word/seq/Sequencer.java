package com.github.word.seq;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Sequencer {

    public void accept(File file, int limit) {
        try {
            process(FileUtils.lineIterator(file), limit, file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptSystemIn(int limit) {
        try (var in = new InputStreamReader(System.in)) {
            process(new LineIterator(in), limit, "standard in");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process(LineIterator iter, int limit, String fileName) {
        try {
            var sequenceCounts = new HashMap<String, Integer>();
            var q = new ArrayDeque<String>();
            while (iter.hasNext()) {
                var line = iter.nextLine();
                var words = line.split("[\\p{Punct}\\s]+");
                for (var word : words) {
                    if (StringUtils.isBlank(word)) continue;
                    if (q.size() < 3) {
                        q.addLast(word.trim());
                    } else if (q.size() == 3) {
                        q.removeFirst();
                        q.addLast(word.trim());
                    }

                    if (q.size() == 3) {
                        var key = String.join(" ", q).toLowerCase();
                        sequenceCounts.put(key, sequenceCounts.getOrDefault(key, 0) + 1);
                    }
                }
            }
            var sortedLimit = sequenceCounts.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(limit)
                    .collect(Collectors.toList());
            var count = new AtomicInteger(1);
            var table = AsciiTable.getTable(sortedLimit, Arrays.asList(
                    new Column().with(entry -> Integer.toString(count.getAndIncrement())),
                    new Column().header("Sequence").with(Map.Entry::getKey),
                    new Column().header("Count").with(entry -> String.valueOf(entry.getValue()))
            ));
            var title = CommandLine.Help.Ansi.AUTO.string("@|bold,green, ****** Sequenced %s ***** |@\n");
            System.out.printf(title, fileName);
            System.out.println(table);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
