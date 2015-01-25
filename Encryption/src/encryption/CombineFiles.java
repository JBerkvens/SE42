package encryption;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author jeroen
 */
public class CombineFiles {

    private static final boolean DEVELOPING = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (DEVELOPING) {
            args = new String[3];
            args[0] = "sig";
            args[1] = "DataFile.txt";
            args[2] = "J.B.A.J. Berkvens";
        }
        if (args.length != 3) {
            System.out.println("Usage: CreateSignedFile signatureFile nameOfFileToSign nameOfSigner");
        } else {
            try {
                StringBuilder fileName = new StringBuilder();
                if (args[1].contains(".")) {
                    String[] split = args[1].split("\\.");
                    for (int i = 0; i < split.length - 1; i++) {
                        fileName.append(split[i]);
                    }
                    fileName.append("(SignedBy: ");
                    fileName.append(args[2]);
                    fileName.append(").");
                    fileName.append(split[split.length-1]);
                } else {
                    fileName.append(args[1]);
                    fileName.append("(SignedBy: ");
                    fileName.append(args[2]);
                    fileName.append(")");
                }
                FileOutputStream combinedFOS = new FileOutputStream(fileName.toString());
                Path path = Paths.get(args[0]);
                byte[] readAllBytes = Files.readAllBytes(path);
                int byteLength = readAllBytes.length;
                combinedFOS.write(byteLength);
                FileInputStream sigFIS = new FileInputStream(args[0]);
                byte[] sig = new byte[sigFIS.available()];
                sigFIS.read(sig);
                sigFIS.close();
                combinedFOS.write(sig);
                FileInputStream dataFIS = new FileInputStream(args[1]);
                byte[] data = new byte[dataFIS.available()];
                dataFIS.read(data);
                dataFIS.close();
                combinedFOS.write(data);
                combinedFOS.close();
            } catch (IOException e) {
                System.err.println("Caught exception " + e.toString());
            }
        }
    }
}
