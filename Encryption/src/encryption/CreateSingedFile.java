package encryption;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

/**
 *
 * @author jeroen
 */
public class CreateSingedFile {

    private static final boolean DEVELOPING = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (DEVELOPING){
            args = new String[1];
            args[0] = "DataFile.txt";
        }
        if (args.length != 1) {
            System.out.println("Usage: CreateSignedFile nameOfFileToSign");
        } else {
            try {
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
                keyGen.initialize(1024, random);
                KeyPair pair = keyGen.generateKeyPair();
                PrivateKey priv = pair.getPrivate();
                PublicKey pub = pair.getPublic();
                Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
                dsa.initSign(priv);
                FileInputStream fis = new FileInputStream(args[0]);
                BufferedInputStream bufin = new BufferedInputStream(fis);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = bufin.read(buffer)) >= 0) {
                    dsa.update(buffer, 0, len);
                }
                bufin.close();
                byte[] realSig = dsa.sign();
                FileOutputStream sigfos = new FileOutputStream("sig");
                sigfos.write(realSig);
                sigfos.close();
                byte[] key = pub.getEncoded();
                FileOutputStream keyfos = new FileOutputStream("suepk");
                keyfos.write(key);
                keyfos.close();
            } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | IOException | SignatureException e) {
                System.err.println("Caught exception " + e.toString());
            }
        }
    }
}