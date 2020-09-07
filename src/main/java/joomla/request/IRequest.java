package joomla.request;

import okhttp3.Response;

public interface IRequest {

    Response send(String... params);

}
