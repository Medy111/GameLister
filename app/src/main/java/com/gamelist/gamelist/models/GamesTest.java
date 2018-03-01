package com.gamelist.gamelist.models;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.android.volley.VolleyError;
import com.igdb.api_android_java.callback.onSuccessCallback;
import com.igdb.api_android_java.model.APIWrapper;
import com.igdb.api_android_java.model.Parameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;


/**
 * Created by captainawesome on 2017-09-15.
 */

public class GamesTest {

// simple TEST Method FOR UNDERSTANDING JSON
// We input a game id and as result we get a JSONArray - then we choose Object from that Array for Output.
// Notes to myself :D ( Medy @ GameList Creator)

// 1. our INPUT is "Game ID" - .addIds("1942"); in this sample
// 2. it will return = JSONArray "result"
// 3. from JSONArray "result" we choose a JSON OBJECT ("id") (Parameters - look them up in the Documentation or in the Parameters Activity in the external liberies from igdb)
// 4. We put the result ( testId ) into a TextView or log them via console
// Some more notes in the method below.

// 5. Bonus :
// Arraylists for outputs with multiple results



    private Context context;



    APIWrapper wrapper = new APIWrapper(context, "90608a365bde4f14c80a710e2e5c2e85");

    public void setUp() throws InterruptedException {
        context = InstrumentationRegistry.getContext();
        String key = System.getProperty("API_KEY");
        wrapper = new APIWrapper(context, key);

    }

    @Test
    public void testSingleGames() throws InterruptedException {
        setUp();
        Parameters parameters = new Parameters()
                .addIds("1942");

        final CountDownLatch lock = new CountDownLatch(1);
        wrapper.games(parameters, new onSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    lock.countDown();
                    JSONObject object = result.getJSONObject(0); // index 0 = first result
                    int testId = object.getInt("id");   // testId output is what we will use for our App
                    assertEquals(1942, testId);

                    // testId will return 1942 since we choose the ("id") field
                    // try to display (testId inside a TextView)
                    // if complete , celebrate and be proud^^1

                    Log.d("Firsttest","JSON" + result.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                    fail("JSONException!!");
                }


            }

            @Override
            public void onError(VolleyError error) {
                fail("Volley Error!!");
            }
        });
        lock.await(20000, TimeUnit.MILLISECONDS);

    }
}
