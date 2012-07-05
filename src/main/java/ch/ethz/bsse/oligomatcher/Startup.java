/**
 * Copyright (c) 2011-2012 Armin Töpfer
 *
 * This file is part of OligoMatcher.
 *
 * OligoMatcher is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 *
 * OligoMatcher is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * OligoMatcher. If not, see <http://www.gnu.org/licenses/>.
 */
package ch.ethz.bsse.oligomatcher;

import ch.ethz.bsse.oligomatcher.stored.FuzzyOligo;
import ch.ethz.bsse.oligomatcher.stored.Read;
import ch.ethz.bsse.oligomatcher.utils.FastaParser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * @author Armin Töpfer (armin.toepfer [at] gmail.com)
 */
public class Startup {

    @Option(name = "-i")
    private String input;
    @Option(name = "-g")
    private String genes;
    @Option(name = "-K")
    private int K;
    @Option(name = "-o", usage = "Path to the output directory (default: current directory)", metaVar = "PATH")
    private String output;

    public static void main(String[] args) throws IOException {
        new Startup().doMain(args);
    }

    public void doMain(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        parser.setUsageWidth(80);
        try {
            parser.parseArgument(args);

            if (output == null) {
                this.output = System.getProperty("user.dir") + File.separator;
            } else if (this.output.endsWith(File.separator)
                    && !new File(this.output).exists()) {
                new File(this.output).mkdirs();
            }


            if (this.input == null && this.genes == null) {
                throw new CmdLineException("");
            } else {
                Map<String, String> oligoMap = FastaParser.parseMultipleFastaFile(input);
                FuzzyOligo[] oligoList = new FuzzyOligo[oligoMap.size()];
                int j = 0;
                for (String identifier : oligoMap.keySet()) {
                    oligoList[j++] = new FuzzyOligo(oligoMap.get(identifier),identifier,K);
                }
                Map<String, String> geneMap = FastaParser.parseMultipleFastaFile(this.genes);
                //kmer -> read
                Map<String,List<String>> kmap = new HashMap<>();
                for (String s : geneMap.keySet()) {
                    Read read = new Read(geneMap.get(s), s,K);
                    
                    for (String kmer: read.getKmers()) {
                        if (!kmap.containsKey(kmer)) {
                            kmap.put(kmer, new ArrayList());
                        }
                        kmap.get(kmer).add(read.getIdentifier());
                    }
                }
                for (FuzzyOligo oligo : oligoList) {
                    if (kmap.containsKey(oligo.getOligo())) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(oligo.getIdentifier()).append(";");
                        for (String kmer : kmap.get(oligo.getOligo())) {
                            sb.append(kmer).append(";");
                        }
                        System.out.println(sb.toString());
                    }
                }
            }

        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar OligoMatcher.jar [options...]\n");
            System.err.println(" ------------------------");
            System.err.println(" === GENERAL options ===");
            System.err.println("  -i PATH\t\t: Path to the oligo file (FASTA or FASTQ format) [REQUIRED]");
            System.err.println("  -g PATH\t\t: Path to the genes file (FASTA format) [REQUIRED]");
            System.err.println("  -K int\t\t: Size of the kmer [REQUIRED]");
            System.err.println("  -verbose\t\t: Print debug information");
            System.err.println(" ------------------------");
        }
    }
}
