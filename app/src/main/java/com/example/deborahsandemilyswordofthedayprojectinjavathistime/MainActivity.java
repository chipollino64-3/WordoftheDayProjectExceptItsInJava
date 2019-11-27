package com.example.deborahsandemilyswordofthedayprojectinjavathistime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import java.util.Timer;
import java.util.TimerTask;
import android.widget.TextView;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Represents the screen of our app.
 */
public class MainActivity extends AppCompatActivity {

    /** How long the word will be up until the whole thing run's again. Make static? */
    private Timer timer;

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
        Button newWord = findViewById(R.id.newWord);
        newWord.setOnClickListener(unused -> merriamWebsterWord());

    }

    /**
     * Makes a web.api request for a random word.
     * @return that word as a string
     */
    private String randomWord() {
        return "Oy vey mamma mia santa maria";
    }

    /**
     * Passes the word returned in randomWord() to the endpoint of a web.api request to Merriam-Webster.
     */
    private void merriamWebsterWord() {
        //The response object will immediately call this parser.
    }

    /**
     * Parses the JSONObject it receives into word, word class, pronunciation, definitions.
     * Sets the text-views to the string properties of the JSONObject.
     * Re-sets the timer back to 24 hours.
     * @param o the response merriamWebsterWord's web.api request will return; a word object
     */
    private void parser(JSONObject o) {
        TextView word = findViewById(R.id.Word);
        TextView pronunciation = findViewById(R.id.Pronunciation);
        LinearLayout definitions = findViewById(R.id.Definitions);
    }

}
