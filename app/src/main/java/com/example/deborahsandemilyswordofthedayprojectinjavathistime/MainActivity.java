package com.example.deborahsandemilyswordofthedayprojectinjavathistime;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import java.util.Timer;
import java.util.TimerTask;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;



/**
 * Represents the screen of our app.
 */
public class MainActivity extends AppCompatActivity {

    /** How long the word will be up until the whole thing run's again. Make static? */
    private Timer timer;

    /** The random word that we will get with a GET request. */
    private static String randomWord;

    /** Private API key for WordsAPI. */
    private static String API_KEY = "6bab6e59d6msh6ce8a6594046873p1352fbjsn5f6f098fbffd";

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
        // ...
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.google.com";

        // Request a string response from the provided URL.
        Button newWord = findViewById(R.id.newWord);
        TextView word = findViewById(R.id.Word);
        TextView pronunciation = findViewById(R.id.Pronunciation);
        LinearLayout definitions = findViewById(R.id.Definitions);
        newWord.setVisibility(View.VISIBLE);
        word.setVisibility(View.VISIBLE);
        word.setText("Welcome!");
        pronunciation.setVisibility(View.INVISIBLE);
        //definitions.setVisibility(View.GONE);
        newWord.setOnClickListener(unused -> word.setText("Word"));
//            word.setText("Incredible");
//            pronunciation.setVisibility(View.VISIBLE);
//            pronunciation.setText("ink-RED-ib-ul");
        //});

    }

    /**
     * Makes a web.api request for a random word.
     * The web.api returns the response as a text, so cast that to a string
     *
     * @return that word as a string
     */
    private void randomWord() {


// Access the RequestQueue through your singleton class.
        //MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }

    /**
     * Passes the word returned in randomWord() to the endpoint of a web.api request to Merriam-Webster.
     * @param s idk yet
     */
    private void merriamWebsterWord(String s) {

    }

    /**
     * Parses the JSONObject it receives into word, word class, pronunciation, definitions.
     * Sets the text-views to the string properties of the JSONObject.
     * Re-sets the timer back to 24 hours.
     * @param o the response merriamWebsterWord's web.api request will return; a word object
     */
    private void parser(JsonObject o) {
        TextView pronunciation = findViewById(R.id.Pronunciation);
        JsonArray pronunciations = o.get("prs").getAsJsonArray();
        String pro = "Pronunciation: ";
        for (JsonElement p : pronunciations) {
            JsonObject pObject = p.getAsJsonObject();
            pro = pro + pObject.get("mw").getAsString() + "; ";
        }
        pronunciation.setText(pro);
        LinearLayout definitions = findViewById(R.id.Definitions);
        //View definitionsChunk = getLayoutInflater().inflate(R.layout.definitions_chunk, definitions, false);
        //TextView def = definitionsChunk.findViewById(R.id.def);
        JsonArray defs = o.get("def").getAsJsonArray();
        for (JsonElement x : defs) {
            JsonObject xAsObject = x.getAsJsonObject();
            JsonArray xAsArray = xAsObject.get("sseq").getAsJsonArray();
            for (JsonElement d : xAsArray) {
                JsonObject dAsObject = d.getAsJsonObject();
                JsonArray singleDef = dAsObject.get("dt").getAsJsonArray();
                for (JsonElement e : singleDef) {
                    JsonObject eAsObject = e.getAsJsonObject();
                    String definition = eAsObject.getAsString();
                    //def.setText(definition);
                    //definitions.addView(definitionsChunk);
                }
            }
        }
        //JsonArray wordDefinitions =
    }

}
