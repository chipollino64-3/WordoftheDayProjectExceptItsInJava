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
        TextView word = findViewById(R.id.Word);
        TextView pronunciation = findViewById(R.id.Pronunciation);
        LinearLayout definitions = findViewById(R.id.Definitions);
        newWord.setVisibility(View.VISIBLE);
        word.setVisibility(View.INVISIBLE);
        pronunciation.setVisibility(View.INVISIBLE);
        //definitions.setVisibility(View.GONE);
        newWord.setOnClickListener(unused -> {
            word.setVisibility(View.VISIBLE);
            word.setText("Incredible");
            pronunciation.setVisibility(View.VISIBLE);
            pronunciation.setText("ink-RED-ib-ul");
        });

    }

    /**
     * Makes a web.api request for a random word.
     * The web.api returns the response as a text, so cast that to a string
     *
     * @return that word as a string
     */
    private void randomWord() {
        /*WebApi.startRequest(this, , response -> {
            randomWord = response.getText();
        }, error -> {
            Toast.makeText(this, "Oh no!", Toast.LENGTH_LONG).show();
        });*/
    }

    /**
     * Passes the word returned in randomWord() to the endpoint of a web.api request to Merriam-Webster.
     * @param s idk yet
     */
    private void merriamWebsterWord(String s) {
        WebApi.startRequest(this, WebApi.API_BASE + randomWord + WebApi.API_KEY_PARAM
                + "3ebf6109-78fe-4fde-95c5-d5a52cd5fcfa", response -> {
            parser(response);

        }, error -> {
            Toast.makeText(this, "Oh no!", Toast.LENGTH_LONG).show();
        });
        //The response object will immediately call this parser.
    }

    /**
     * Parses the JSONObject it receives into word, word class, pronunciation, definitions.
     * Sets the text-views to the string properties of the JSONObject.
     * Re-sets the timer back to 24 hours.
     * @param o the response merriamWebsterWord's web.api request will return; a word object
     */
    private void parser(JsonObject o) {
        TextView word = findViewById(R.id.Word);
        TextView pronunciation = findViewById(R.id.Pronunciation);
        LinearLayout definitions = findViewById(R.id.Definitions);
    }

}
