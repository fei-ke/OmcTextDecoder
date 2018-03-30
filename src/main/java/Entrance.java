import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Entrance {
    public static void main(String[] args) throws ParseException, IOException {
        Options options = new Options();

        options.addOption("h", "help", false, "print this message");
        options.addOption("e", "encode", false, "encode omc file");
        options.addOption("d", "decode", false, "decode omc file");
        options.addOption("i", "input", true, "input file");
        options.addOption("o", "output", true, "output file");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);


        File inputFile = null;
        File outputFile;

        boolean decode = true;

        if (cmd.hasOption('h') || args.length == 0) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar <the jar name> -i cscfeture.xml -o cscfeture_decoded.xml", options);
            return;
        }

        if (cmd.hasOption('i')) {
            inputFile = new File(cmd.getOptionValue("i"));
        } else {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar <the jar name> -i cscfeture.xml -o cscfeture_decoded.xml", options);
        }
        if (cmd.hasOption("o")) {
            outputFile = new File(cmd.getOptionValue('o'));
        } else {
            outputFile = new File(inputFile.getParent(), "out_" + inputFile.getName());
        }

        if (cmd.hasOption('e')) {
            decode = false;
        }

        OmcTextDecoder decoder = new OmcTextDecoder();
        byte[] bytes;
        if (decode)
            bytes = decoder.decode(inputFile);
        else {
            bytes = decoder.encode(inputFile);
        }
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(bytes);
        outputStream.close();
        System.out.println("output " + outputFile.getName() + " success");
    }
}

