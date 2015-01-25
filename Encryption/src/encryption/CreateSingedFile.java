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
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
                keyPairGenerator.initialize(1024, secureRandom);
                KeyPair keyPair = keyPairGenerator.generateKeyPair();
                PrivateKey privateKey = keyPair.getPrivate();
                PublicKey publicKey = keyPair.getPublic();
                Signature signature = Signature.getInstance("SHA1withRSA");
                signature.initSign(privateKey);
                FileInputStream dataFIS = new FileInputStream(args[0]);
                BufferedInputStream bufferIn = new BufferedInputStream(dataFIS);
                byte[] buffer = new byte[1024];
                int part;
                while ((part = bufferIn.read(buffer)) >= 0) {
                    signature.update(buffer, 0, part);
                }
                bufferIn.close();
                byte[] realSignature = signature.sign();
                FileOutputStream signatureFOS = new FileOutputStream("sig");
                signatureFOS.write(realSignature);
                signatureFOS.close();
                byte[] key = publicKey.getEncoded();
                FileOutputStream keyFOS = new FileOutputStream("suepk");
                keyFOS.write(key);
                keyFOS.close();
            } catch (NoSuchAlgorithmException | InvalidKeyException | IOException | SignatureException e) {
                System.err.println("Caught exception " + e.toString());
            }
        }
    }
}
