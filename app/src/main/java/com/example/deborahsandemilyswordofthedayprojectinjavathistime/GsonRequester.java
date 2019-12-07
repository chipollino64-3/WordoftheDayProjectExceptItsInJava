package com.example.deborahsandemilyswordofthedayprojectinjavathistime;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;


/**
 * Title: Implement A Custom Request - Example: GsonRequest
 * Author: Android Developers
 * Date: 12/7/2019
 * Code Version: 1.0
 * Availability: https://developer.android.com/training/volley/request-custom
 * @param <T> The server's response to the GET request. Should be a JsonObject.
 */
public class GsonRequester<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonRequester(String url, Class<T> clazz, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
    }

    /**
     * Getter for the response headers.
     *
     * @return a map which maps the parameter titles to their values
     * @throws AuthFailureError if parameters aren't correct, or if your API key is wrong
     */
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    /**
     * Calls back onto main thread with the parsed response from parseNetworkResponse
     *
     * @param response the parsed object returned by parseNetworkResponse
     */
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    /**
     * Parses the server's response into the needed object, in this case a JSON object.
     * Catches possible errors.
     *
     * @param response the server's response
     * @return a parsed object for deliverResponse to use
     */
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
