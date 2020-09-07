package joomla;

import joomla.request.CheckComponentRequest;
import okhttp3.Response;

public class Main {

    public static void main(String[] args) {
        Response response = new CheckComponentRequest().send("http", "youmagic.com", "com_newsfeeds");
        if (response.code() == 200)
            System.out.println("com_newsfeeds is find!");
    }

}
