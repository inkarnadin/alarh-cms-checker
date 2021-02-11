package web.http;

import lombok.SneakyThrows;
import okhttp3.Request;
import okhttp3.*;

import javax.net.ssl.*;
import java.io.File;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HttpClient implements Client {

    private final WildCardTrustManager trustManager = new WildCardTrustManager();
    private final OkHttpClient okClient;

    public HttpClient() {
        okClient = new OkHttpClient().newBuilder()
                .sslSocketFactory(provideSSLFactory(), trustManager)
                .addNetworkInterceptor(provideCacheInterceptor())
                .hostnameVerifier(provideVerifier())
                .cache(new Cache(new File("http_cache"), 50L * 1024L * 1024L))                
                .followSslRedirects(true)
                .build();
    }

    @SneakyThrows
    @Override
    public Response execute(Request request) {
        return okClient.newCall(request).execute();
    }
    
    @SneakyThrows
    @Override
    public void evictAll() {
        if (Objects.nonNull(okClient.cache()))
            okClient.cache().evictAll();
    }

    private Interceptor provideCacheInterceptor () {
        return chain -> {
            Response response = chain.proceed(chain.request());
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(2, TimeUnit.MINUTES)
                    .build();

            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", cacheControl.toString())
                    .build();
        };
    }

    @SneakyThrows
    private SSLSocketFactory provideSSLFactory() {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[] { trustManager }, new SecureRandom());
        return sslContext.getSocketFactory();
    }

    private HostnameVerifier provideVerifier() {
        return (hostname, session) -> true;
    }

}
