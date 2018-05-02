package vietmydental.immortal.com.vietmydental.api;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import vietmydental.immortal.com.vietmydental.utils.DomainConst;

/**
 * Created by nguyenpt on 4/16/18.
 */

public class RequestAllowAllFactory extends SSLSocketFactory {
    /** Timeout connect */
    private static final int TIMEOUT_CONNECTION = 30 * 1000;
    /** Timeout connect */
    private static final int SOCKET_TIMEOUT_CONNECTION = 90 * 1000;
    /** SSL context */
    private SSLContext sslContext = SSLContext.getInstance("TLS");

    /**
     * Constructor.
     * @param trustStore Trust store
     * @throws NoSuchAlgorithmException Exception
     * @throws KeyManagementException Exception
     * @throws KeyStoreException Exception
     * @throws UnrecoverableKeyException Exception
     */
    public RequestAllowAllFactory(KeyStore trustStore) throws NoSuchAlgorithmException, KeyManagementException,
            KeyStoreException, UnrecoverableKeyException {
        super(trustStore);

        TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                try {
                    chain[0].checkValidity();
                } catch (Exception e) {
                    throw new CertificateException(DomainConst.CERTIFICATE_NOT_VALID_OR_TRUSTED);
                }
            }

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sslContext.init(null, new TrustManager[] { tm }, null);
    }

    /**
     * Create socket.
     * @param socket Socket name
     * @param host Host
     * @param port Port
     * @param autoClose Auto close flag
     * @return Socket object
     * @throws IOException Exception
     */
    @Override
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
        return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
    }

    /**
     * Create socket.
     * @return Socket object
     * @throws IOException
     */
    @Override
    public Socket createSocket() throws IOException {
        return sslContext.getSocketFactory().createSocket();
    }

    /**
     * Get new HTTP client.
     * @return HTTP client
     */
    public static HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            SSLSocketFactory sf = new RequestAllowAllFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
            HttpConnectionParams.setConnectionTimeout(params, TIMEOUT_CONNECTION);
            HttpConnectionParams.setSoTimeout(params, SOCKET_TIMEOUT_CONNECTION);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme(DomainConst.SCHEME_HTTP, PlainSocketFactory.getSocketFactory(),
                    DomainConst.PORT_80));
            registry.register(new Scheme(DomainConst.SCHEME_HTTPS, sf,
                    DomainConst.PORT_443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            e.printStackTrace();
            return new DefaultHttpClient();
        }
    }

    /**
     * Make request.
     * @param httpRequest Http request
     * @return Http response
     * @throws IOException
     */
    public static HttpResponse makeRequest(HttpUriRequest httpRequest) throws IOException {
        HttpClient httpClient = getNewHttpClient();

        // Execute HTTP Post Request
        return httpClient.execute(httpRequest);
    }
}
