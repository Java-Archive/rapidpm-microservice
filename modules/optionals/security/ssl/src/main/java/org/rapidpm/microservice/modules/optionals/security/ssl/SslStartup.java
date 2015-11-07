package org.rapidpm.microservice.modules.optionals.security.ssl;


import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Optional;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.optionals.cli.CmdLineSingleton;

/**
 * Created by b.bosch on 05.11.2015.
 */
public class SslStartup implements Main.MainStartupAction {
    @Override
    public void execute(Optional<String[]> args) {
        CmdLineSingleton instance = CmdLineSingleton.getInstance();
        instance.addCmdLineOption(new Option("keystore", "javaKeystore", true, "the location of the keystore"));
        instance.addCmdLineOption(new Option("key", "keystoreKey", true, "passwordForTheKeystore"));


        CommandLine commandLine = instance.getCommandLine().get();
        if(commandLine.hasOption("keystore")){
            try {
                String optionValue = commandLine.getOptionValue("key", "macrosreply");
                String keystore = commandLine.getOptionValue("keystore");
                FileInputStream fileInputStream = new FileInputStream(keystore);
                KeyStore jks = KeyStore.getInstance(KeyStore.getDefaultType());
                jks.load(fileInputStream, optionValue.toCharArray());
                Enumeration<String> aliases = jks.aliases();
                String alias = aliases.nextElement();
                PrivateKey key = (PrivateKey) jks.getKey(alias, optionValue.toCharArray());
                X509Certificate certificate = (X509Certificate) jks.getCertificate(alias);

               SSLContext sslContext = SSLCertificateHelper.createSSLContext(certificate, key);
                //SSLContext sslContext = SSLCertificateHelper.createSSLContext();
                setSSLContextInMain(sslContext);

            }
            catch (Exception e){

            }

        }

    }

    private void setSSLContextInMain(SSLContext sslContext) {
        try {
            Field sslContext1 = Main.class.getDeclaredField("sslContext");
            boolean accessible = sslContext1.isAccessible();
            sslContext1.setAccessible(true);
            sslContext1.set(null, sslContext);
            sslContext1.setAccessible(accessible);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
