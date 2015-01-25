package encryption;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author jeroen
 */
public class VerifySingedFile {

    private static final boolean DEVELOPING = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (DEVELOPING) {
            args = new String[2];
            args[0] = "suepk";
            args[1] = "DataFile(SignedBy: J.B.A.J. Berkvens).txt";
        }
        if (args.length != 2) {
            System.out.println("Usage: VerifySignedFile publicKeyFile combinedDataFile");
        } else {
            try {
                FileInputStream keyFIS = new FileInputStream(args[0]);
                byte[] key = new byte[keyFIS.available()];
                keyFIS.read(key);
                keyFIS.close();
                X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(key);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);
                FileInputStream combinedFIS = new FileInputStream(args[1]);
                int available = combinedFIS.available();
                int byteLength = combinedFIS.read();
                byte[] signatureToVerify = new byte[byteLength];
                combinedFIS.read(signatureToVerify);
                Signature signature = Signature.getInstance("SHA1withRSA");
                signature.initVerify(publicKey);
                FileOutputStream generatedDataFOS = new FileOutputStream("generatedDataFile.txt");
                BufferedInputStream bufferIn = new BufferedInputStream(combinedFIS);
                BufferedOutputStream bufferOut = new BufferedOutputStream(generatedDataFOS);
                byte[] buffer = new byte[1024];
                int part;
                List<Integer> data = new ArrayList<>(); 
                while (bufferIn.available() != 0) {
                    part = bufferIn.read(buffer);
                    bufferOut.write(buffer, 0, part);
                    signature.update(buffer, 0, part);
                };
                bufferIn.close();
                bufferOut.close();
                generatedDataFOS.close();
                boolean verifies = signature.verify(signatureToVerify);
                System.out.println("signature verifies: " + verifies);
            } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | SignatureException e) {
                System.err.println("Caught exception " + e.toString());
            }
        }
    }
}
