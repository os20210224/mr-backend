package thread;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class lobby extends Thread {
	
	private SSLServerSocket serverSoket;
	private int port = 7259;
	ExecutorService threadPool = Executors.newFixedThreadPool(10);
	
	public lobby() {
		SSLContext sslContext = this.createSSLContext();
		try {
			SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
			serverSoket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);
		} catch (IOException ex) {
			System.out.println("> Socket initialization error: " + ex.getMessage() + "\n");
			ex.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (!serverSoket.isClosed()) {
				System.out.println("> awaiting a connection request\n");
				SSLSocket clientSocket = (SSLSocket) serverSoket.accept();
				// TODO print neki klijent id po konkeicji
				threadPool.execute(new clientHandler(clientSocket));
			}
		} catch(IOException e) {
			System.out.println("> lobby exception: " + e.getMessage());
			if (e.getMessage().equals("Socket closed")) {
				return;
			}
		}
	}
	
	private SSLContext createSSLContext() {
		try {
			char[] passphrase = System.getProperty("KEYSTORE_PASSWORD").toCharArray();
			
			KeyStore keyStore = KeyStore.getInstance("JKS");
			keyStore.load(new FileInputStream("dev_key_store"), passphrase);
			
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			keyManagerFactory.init(keyStore, passphrase);
			KeyManager[] km = keyManagerFactory.getKeyManagers();
			
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(keyStore);
			TrustManager[] tm = trustManagerFactory.getTrustManagers();
			
			SSLContext sslContext = SSLContext.getInstance("TLSv1");
			sslContext.init(km,  tm, null);
					
			return sslContext;
		} catch (KeyStoreException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (UnrecoverableKeyException ex) {
			ex.printStackTrace();
		} catch (KeyManagementException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			Logger.getLogger(lobby.class.getName()).log(Level.SEVERE, null, ex);
		} catch (CertificateException ex) {
			Logger.getLogger(lobby.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
}
