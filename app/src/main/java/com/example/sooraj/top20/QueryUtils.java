package com.example.sooraj.top20;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.sooraj.top20.MainActivity.LOG_TAG;

public class QueryUtils extends AsyncTaskLoader<List<News>> {



    private static final String USGS_REQUEST_URL =
           "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=d6b34536cdba4c9c9415a159edbdfa6c";

        /**
         * Query the USGS dataset and return a list of {@link News} objects.
         */
        public QueryUtils(Context context) {
            super(context);
        }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        List<News> listOfNews;
        URL url;
        url = createUrl(USGS_REQUEST_URL);

        String reponseStr = null;
        try {
            reponseStr = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<News> newsli = extractFeatureFromJson(reponseStr);
        Log.i("iam here",newsli.toString());
        return newsli;
    }
    /*
        public static List<News> fetchNews(String requestUrl) {
            // Create URL object
            URL url = createUrl(requestUrl);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = null;
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem making the HTTP request.", e);
            }

            // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
            List<News> newsli = extractFeatureFromJson(jsonResponse);

            // Return the list of {@link Earthquake}s
            return newsli;
        }

    public static URL buildURL(String githubSearchQuery) {
        Uri builtUri = Uri.parse(USGS_REQUEST_URL).buildUpon()

                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

        /**
         * Returns new URL object from the given string URL.
         */
        private static URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Problem building the URL ", e);
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private static String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";

            // If the URL is null, then return early.
            if (url == null) {
                return jsonResponse;
            }

            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // If the request was successful (response code 200),
                // then read the input stream and parse the response.
                if (urlConnection.getResponseCode() >=0) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else {
                    Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // Closing the input stream could throw an IOException, which is why
                    // the makeHttpRequest(URL url) method signature specifies than an IOException
                    // could be thrown.
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private static String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        /**
         * Return a list of {@link News} objects that has been built up from
         * parsing the given JSON response.
         */
        private static List<News> extractFeatureFromJson(String newsJson) {
            // If the JSON string is empty or null, then return early.
            if (TextUtils.isEmpty(newsJson)) {
                return null;
            }



            List<News> news = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject jnew= new JSONObject(newsJson);
            JSONArray articlearray = jnew.getJSONArray("articles");
            for(int i=0;i<=articlearray.length();i++){
                JSONObject currentnews=articlearray.getJSONObject(i);
                String nwtitle=currentnews.getString("title");
                String nwdesc=currentnews.getString("description");
                String nwurlimg=currentnews.getString("urlToImage");
                String nwurl=currentnews.getString("url");
                News nw =new News(nwtitle,nwdesc,nwurlimg,nwurl);
                news.add(nw);

            }

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        // Return the list of earthquakes
        return news;
    }

}

