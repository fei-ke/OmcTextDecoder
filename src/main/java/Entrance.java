import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipException;

public class Entrance {
    private static OmcTextDecoder decoder = new OmcTextDecoder();

    public static void main(String[] args) throws ParseException {
        Options options = new Options();

        options.addOption("h", "help", false, "print this message");
        options.addOption("e", "encode", false, "encode omc file");
        options.addOption("d", "decode", false, "decode omc file");
        options.addOption("i", "input", true, "input file or dir");
        options.addOption("o", "output", true, "output file or dir");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);


        File inputFile;
        File outputFile;

        boolean decode = true;

        if (cmd.hasOption('h') || args.length == 0) {
            printHelp(options);
            return;
        }

        if (cmd.hasOption('i')) {
            inputFile = new File(cmd.getOptionValue("i"));
        } else {
            printHelp(options);
            return;
        }
        if (cmd.hasOption("o")) {
            outputFile = new File(cmd.getOptionValue('o'));
        } else {
            outputFile = new File(inputFile.getParent(), "out_" + inputFile.getName());
        }

        if (cmd.hasOption('e')) {
            decode = false;
        }

        decode(inputFile, outputFile, decode);
    }

    private static void decode(File in, File out, boolean decode) {
        if (in.isDirectory()) {
            File[] files = in.listFiles();
            if (files != null) {
                for (File file : files) {
                    decode(file, new File(out, file.getName()), decode);
                }
            }
        } else {
            decodeFile(in, out, decode);
        }
    }

    private static void decodeFile(File inputFile, File outputFile, boolean decode) {
        try {
            String path = inputFile.getAbsolutePath();
            if (!path.endsWith(".xml")) {
                //not xml file, ignored
                return;
            }

            byte[] bytes;
            if (decode)
                bytes = decoder.decode(inputFile);
            else {
                bytes = decoder.encode(inputFile);
            }

            if (outputFile.exists()) {
                outputFile.delete();
            }
            File parentFile = outputFile.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            outputFile.createNewFile();

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(bytes);
            outputStream.close();
            System.out.println("output " + outputFile.getAbsolutePath() + " success");
        } catch (ZipException e) {
            //
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar <the jar name> -i cscfeture.xml -o cscfeture_decoded.xml", options);
    }
}

