package encryption;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
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
        if (DEVELOPING){
            args = new String[3];
            args[0] = "suepk";
            args[1] = "sig";
            args[2] = "DataFile.txt";
        }
        if (args.length != 3) {
            System.out.println("Usage: VerifySignedFile publicKeyFile signatureFile dataFile");
        } else {
            try {
                FileInputStream keyfis = new FileInputStream(args[0]);
                byte[] encKey = new byte[keyfis.available()];
                keyfis.read(encKey);
                keyfis.close();
                X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
                KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
                PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
                FileInputStream sigfis = new FileInputStream(args[1]);
                byte[] sigToVerify = new byte[sigfis.available()];
                sigfis.read(sigToVerify);
                sigfis.close();
                Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
                sig.initVerify(pubKey);
                FileInputStream datafis = new FileInputStream(args[2]);
                BufferedInputStream bufin = new BufferedInputStream(datafis);
                byte[] buffer = new byte[1024];
                int len;
                while (bufin.available() != 0) {
                    len = bufin.read(buffer);
                    sig.update(buffer, 0, len);
                };
                bufin.close();
                boolean verifies = sig.verify(sigToVerify);
                System.out.println("signature verifies: " + verifies);
            } catch (IOException | NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException | InvalidKeyException | SignatureException e) {
                System.err.println("Caught exception " + e.toString());
            }
        }
    }
}
