package web.http;

import lombok.SneakyThrows;
import okhttp3.*;
import okhttp3.Request;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HttpClient implements Client {

    private final OkHttpClient okClient;

    public HttpClient() {
        okClient = new OkHttpClient().newBuilder()
                .addNetworkInterceptor(provideCacheInterceptor())
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

}
