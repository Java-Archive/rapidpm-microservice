package org.rapidpm.microservice.modules.optionals.security.ssl;

import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

public class SSLCertificateHelper {


    private static final String password = "macrosreply";

    private static X509Certificate createSignedCertificate(X509Certificate cetrificate, X509Certificate issuerCertificate, PrivateKey issuerPrivateKey) {
        try {
            Principal issuer = issuerCertificate.getSubjectDN();
            String issuerSigAlg = issuerCertificate.getSigAlgName();

            byte[] inCertBytes = cetrificate.getTBSCertificate();
            X509CertInfo info = new X509CertInfo(inCertBytes);
            info.set(X509CertInfo.ISSUER, (X500Name) issuer);

            //No need to add the BasicContraint for leaf cert
            if (!cetrificate.getSubjectDN().getName().equals("CN=TOP")) {
                CertificateExtensions exts = new CertificateExtensions();
                BasicConstraintsExtension bce = new BasicConstraintsExtension(true, -1);
                exts.set(BasicConstraintsExtension.NAME, new BasicConstraintsExtension(false, bce.getExtensionValue()));
                info.set(X509CertInfo.EXTENSIONS, exts);
            }

            X509CertImpl outCert = new X509CertImpl(info);
            outCert.sign(issuerPrivateKey, issuerSigAlg);

            return outCert;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static KeyStore createKeyStoreFromChain(String alias, char[] password, Key key, X509Certificate[] chain) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("jks");
        keyStore.load(null, null);

        keyStore.setKeyEntry(alias, key, password, chain);
        return keyStore;

    }

    public static SSLContext createSSLContext() {
        try {
            KeyChain keyChain = createKeyChain();
            KeyStore keyStore = createKeyStoreFromChain("Test", password.toCharArray(), keyChain.topPrivateKey, keyChain.chain);

            // Create key manager
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, password.toCharArray());
            KeyManager[] km = keyManagerFactory.getKeyManagers();

            // Create trust manager
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(keyStore);
            TrustManager[] tm = trustManagerFactory.getTrustManagers();

            // Initialize SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLSv1");
            sslContext.init(km, tm, null);

            return sslContext;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


    public static KeyChain createKeyChain() {
        try {
            //Generate ROOT certificate
            CertAndKeyGen keyGen = new CertAndKeyGen("RSA", "SHA1WithRSA", null);
            keyGen.generate(1024);
            PrivateKey rootPrivateKey = keyGen.getPrivateKey();

            X509Certificate rootCertificate = keyGen.getSelfCertificate(new X500Name("CN=ROOT"), (long) 365 * 24 * 60 * 60);

            //Generate intermediate certificate
            CertAndKeyGen keyGen1 = new CertAndKeyGen("RSA", "SHA1WithRSA", null);
            keyGen1.generate(1024);
            PrivateKey middlePrivateKey = keyGen1.getPrivateKey();

            X509Certificate middleCertificate = keyGen1.getSelfCertificate(new X500Name("CN=MIDDLE"), (long) 365 * 24 * 60 * 60);

            //Generate leaf certificate
            CertAndKeyGen keyGen2 = new CertAndKeyGen("RSA", "SHA1WithRSA", null);
            keyGen2.generate(1024);
            PrivateKey topPrivateKey = keyGen2.getPrivateKey();

            X509Certificate topCertificate = keyGen2.getSelfCertificate(new X500Name("CN=TOP"), (long) 365 * 24 * 60 * 60);

            rootCertificate = createSignedCertificate(rootCertificate, rootCertificate, rootPrivateKey);
            middleCertificate = createSignedCertificate(middleCertificate, rootCertificate, rootPrivateKey);
            topCertificate = createSignedCertificate(topCertificate, middleCertificate, middlePrivateKey);

            X509Certificate[] chain = new X509Certificate[3];
            chain[0] = topCertificate;
            chain[1] = middleCertificate;
            chain[2] = rootCertificate;

            System.out.println(Arrays.toString(chain));
            KeyChain keyChain = new KeyChain();
            keyChain.chain = chain;
            keyChain.topPrivateKey = topPrivateKey;

            return keyChain;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static SSLContext createSSLContext(X509Certificate rootCertificate, PrivateKey key) {
        try {
            //Generate leaf certificate
            CertAndKeyGen keyGen2 = new CertAndKeyGen("RSA", "SHA1WithRSA", null);
            keyGen2.generate(1024);
            PrivateKey topPrivateKey = keyGen2.getPrivateKey();
            X509Certificate topCertificate = keyGen2.getSelfCertificate(new X500Name("CN=TOP"), (long) 365 * 24 * 60 * 60);
            topCertificate = createSignedCertificate(topCertificate, rootCertificate, key);

            X509Certificate[] chain = new X509Certificate[2];
            chain[0] = topCertificate;
            chain[1] = rootCertificate;



            KeyStore keyStore = createKeyStoreFromChain("Test", password.toCharArray(), topPrivateKey, chain);

            // Create key manager
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, password.toCharArray());
            KeyManager[] km = keyManagerFactory.getKeyManagers();

            // Create trust manager
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(keyStore);
            TrustManager[] tm = trustManagerFactory.getTrustManagers();

            // Initialize SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLSv1");
            sslContext.init(km, tm, null);

            return sslContext;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static class KeyChain {
        public X509Certificate[] chain;
        PrivateKey topPrivateKey;

    }


}