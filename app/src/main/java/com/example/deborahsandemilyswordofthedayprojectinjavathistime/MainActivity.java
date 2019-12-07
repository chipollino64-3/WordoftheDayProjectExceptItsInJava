package com.example.deborahsandemilyswordofthedayprojectinjavathistime;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vividsolutions.jts.util.Stopwatch;


/**
 * Represents the screen of our app.
 */
public class MainActivity extends AppCompatActivity {

    /** How long the word will be up until the whole thing run's again. Make static? */
    private Timer timer = new Timer();

    /** Map with required headers. */
    private static Map<String, String> headers = new HashMap<>();

    /** URL parameter. */
    private static final String URL = "https://wordsapiv1.p.mashape.com/words/?random=true&hasDetails=definitions&partOfSpeech=noun&lettersMin=5";

    /*/** Private API key for WordsAPI.
    private static String API_KEY = "6bab6e59d6msh6ce8a6594046873p1352fbjsn5f6f098fbffd";*/


    /**
     * Runs when the app is first opened.
     * @param savedInstanceState the... saved... instance... state?
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //make web.api request for random word
        //make web.api request using that word
        //parse the object into: word, word class, pronunciation, definitions
        //set textview to the strings for word, pronunciation
        //put each definition in a chunk
        headers.put("X-RapidAPI-Host", "wordsapiv1.p.rapidapi.com");
        headers.put("X-RapidAPI-Key", "6bab6e59d6msh6ce8a6594046873p1352fbjsn5f6f098fbffd");
        headers.put("Accept", "application/json");
        // ...
        // Instantiate the RequestQueue.
        /*TextView otherWord = findViewById(R.id.Word);
        otherWord.setVisibility(View.VISIBLE);
        otherWord.setText("Welcome!");*/
        RequestQueue queue = Volley.newRequestQueue(this);
        GsonRequester<JsonObject> random = new GsonRequester<>("https://wordsapiv1.p.mashape.com/words/welcome",
                JsonObject.class, headers, response -> {
            setInitialTimer();
            parser(response);
        }, error -> {
            TextView word = findViewById(R.id.Word);
            word.setText("Whoops! Something went wrong.");
            error.getMessage();
            error.printStackTrace();
        });
        queue.add(random);


        // Request a string response from the provided URL.
        Button newWord = findViewById(R.id.newWord);
        newWord.setOnClickListener(unused -> makeRequest());
    }

    /**
     * Makes a GET request to the WordsAPI.
     * In the event of a response, calls parser on said response.
     * In the event of an error, notifies user.
     */
    private void makeRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        GsonRequester<JsonObject> random = new GsonRequester<>(URL,
                JsonObject.class, headers, response -> {
            //timer.cancel();
            parser(response);
        }, error -> {
            TextView word = findViewById(R.id.Word);
            word.setText("Whoops! Something went wrong.");
            error.getMessage();
            error.printStackTrace();
        });
        queue.add(random);
    }

    /**
     * Parses the JSONObject it receives into word, word class, pronunciation, definitions.
     * Sets the text-views to the string properties of the JSONObject.
     * Re-sets the timer back to 24 hours.
     * @param o the response merriamWebsterWord's web.api request will return; a word object
     */
    private void parser(JsonObject o) {
        LinearLayout definitions = findViewById(R.id.Definitions);
        definitions.removeAllViews();
        TextView word = findViewById(R.id.Word);
        String setWord = o.get("word").getAsString();
        if (setWord.contains(" ")) {
            makeRequest();
            return;
        }
        TextView pronunciation = findViewById(R.id.Pronunciation);
        word.setVisibility(View.GONE);
        pronunciation.setVisibility(View.GONE);
        definitions.setVisibility(View.GONE);
        System.out.println(setWord);
        word.setText(setWord);
        if (!o.has("pronunciation")) {
            makeRequest();
            return;
        }

        if (!(o.get("pronunciation") instanceof JsonObject)) {
            String stringPronunciation = o.get("pronunciation").getAsString();
            pronunciation.setText("/" + stringPronunciation + "/");
        } else {
            JsonObject findPronunciation = o.get("pronunciation").getAsJsonObject();
            if (findPronunciation.has("all")) {
                String setPronunciation = findPronunciation.get("all").getAsString();
                pronunciation.setText("/" + setPronunciation + "/");
            } else {
                String nounPronunciation = "Noun: /" + findPronunciation.get("noun").getAsString() + "/";
                pronunciation.setText("Noun: /" + nounPronunciation + "/");
                if (findPronunciation.has("noun")) {
                    String verbPronunciation = "Verb: /" + findPronunciation.get("verb").getAsString() + "/";
                    pronunciation.setText(nounPronunciation + "; " + verbPronunciation);
                }
            }
        }

        JsonArray getResults = o.get("results").getAsJsonArray();
        for (JsonElement def : getResults) {
            View definitionsChunk = getLayoutInflater().inflate(R.layout.definitions_chunk, definitions, false);
            TextView wordClassView = definitionsChunk.findViewById(R.id.wordClass);
            TextView actualDef = definitionsChunk.findViewById(R.id.actualDefinition);
            definitionsChunk.setVisibility(View.VISIBLE);
            wordClassView.setVisibility(View.VISIBLE);
            actualDef.setVisibility(View.VISIBLE);
            JsonObject defJson = def.getAsJsonObject();
            String definition = defJson.get("definition").getAsString();
            System.out.println(definition);
            if (!defJson.has("partOfSpeech") || defJson.get("partOfSpeech") == null) {
                makeRequest();
                return;
            }
            String wordClass = defJson.get("partOfSpeech").getAsString();
            System.out.println(wordClass);
            //on our to-do list: figure out how to italicize
            wordClassView.setText(wordClass);
            actualDef.setText(definition);
            definitions.addView(definitionsChunk);
            System.out.println(definitions.getChildCount());
        }
        word.setVisibility(View.VISIBLE);
        pronunciation.setVisibility(View.VISIBLE);
        definitions.setVisibility(View.VISIBLE);
    }

    private void setInitialTimer() {
        class InitialWordTask extends TimerTask {
            InitialWordTask() { }
            public void run() {
                setTimer();
            }
        }
        TimerTask task = new InitialWordTask();
        timer.schedule(task, 15000);
    }

    private void setTimer() {
        class ChangeWordTask extends TimerTask {
            ChangeWordTask() { }
            public void run() {
                makeRequest();
            }
        }
        TimerTask task = new ChangeWordTask();
        timer.schedule(task, new Date(), 15000);
    }
}
