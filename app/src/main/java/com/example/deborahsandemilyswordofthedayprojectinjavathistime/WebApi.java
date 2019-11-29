package com.example.deborahsandemilyswordofthedayprojectinjavathistime;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * Title: WebApi source code
 * Author: Challen, G
 * Date: 2019
 * Code verson: 1.0
 * Availability: http://cs125.ca.illinois.edu/MP/
 */
public final class WebApi {

    /** Tag for logged messages. */
    private static final String TAG = "WebApi";

    /** The URL at which the server is hosted. */
    static final String API_BASE = "https://www.dictionaryapi.com/api/v3/references/collegiate/json/";

    /** Second part of the URL. */
    static final String API_KEY_PARAM = "?key=";

    /** The Web Api key for this webpage. */
    private static final String API_KEY = "51a870b090mshd70a525b49254abp1ab4afjsn705a4cd5f124";

    /** The HTTP status code for Bad Request. */
    private static final int HTTP_BAD_REQUEST = 400;

    /** The Volley request queue for the application, or null if the queue hasn't been set up yet. */
    private static RequestQueue requestQueue;

    /** The Gson parser used to parse response JSON. */
    private static JsonParser jsonParser = new JsonParser();

    /** Private constructor to prevent creating instances. */
    private WebApi() { }

    /**
     * Starts an HTTP GET request.
     * @param context an Android context
     * @param url the URL to contact
     * @param listener callback to run with response data
     * @param errorListener callback to run if an error occurs
     */


    public static void startRequest(final Context context, final String url,
                                    final Response.Listener<JsonObject> listener, final Response.ErrorListener errorListener) {
        startRequest(context, url, Request.Method.GET, null, listener, errorListener);
    }

    /**
     * Starts a network request with a JSON object as the payload.
     * @param context an Android context
     * @param url the URL to contact
     * @param method the HTTP method (e.g. GET or POST)
     * @param body the JSON object to include in the body
     * @param listener callback to run with response data
     * @param errorListener callback to run if an error occurs
     */
    public static void startRequest(final Context context, final String url, final int method, final JsonElement body,
                                    final Response.Listener<JsonObject> listener, final Response.ErrorListener errorListener) {
        if (requestQueue == null) {
            Log.i(TAG, "Creating request queue");
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        Response.Listener<String> serverResponseListener = stringResponse -> {
            if (stringResponse == null || stringResponse.isEmpty()) {
                Log.i(TAG, "Delivering empty response from " + url);
                listener.onResponse(null);
            } else {
                Log.i(TAG, "Delivering parsed response from " + url);
                listener.onResponse(jsonParser.parse(stringResponse).getAsJsonObject());
            }
        };
        Response.ErrorListener serverErrorListener = error -> {
            if (error.networkResponse != null && error.networkResponse.data != null
                    && error.networkResponse.statusCode == HTTP_BAD_REQUEST) {
                String responseData = new String(error.networkResponse.data);
                try {
                    JsonObject errObject = jsonParser.parse(responseData).getAsJsonObject();
                    Log.i(TAG, "Delivering application-level error from " + url);
                    errorListener.onErrorResponse(new VolleyError(errObject.get("error").getAsString()));
                } catch (Exception e) {
                    Log.i(TAG, "Delivering 400 error from " + url);
                    errorListener.onErrorResponse(error);
                }
            } else {
                Log.i(TAG, "Delivering Volley error response from " + url);
                errorListener.onErrorResponse(error);
            }
        };
    }

}
