package com.gamelist.gamelist;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.VolleyError;
import com.igdb.api_android_java.callback.onSuccessCallback;
import com.igdb.api_android_java.model.APIWrapper;
import com.igdb.api_android_java.model.Parameters;

import org.json.JSONArray;

/**
 * Created by Medy on 21.12.2017.
 */

public class JSONResponse extends AppCompatActivity {

// 1 .      extends AppCompatActivity important
// 2 .      Methods to Parse JSON from IGDB

/*** TODO: METHOD 1 Main Method - Game + Release date +  ***/
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

        public void GameListItem(){
            APIWrapper wrapper = new APIWrapper(getApplicationContext(), "90608a365bde4f14c80a710e2e5c2e85");

            Parameters params = new Parameters()
                    .addFields("*")
                    .addOrder("published_at:desc");

            wrapper.games(params, new onSuccessCallback() {
                @Override
                public void onSuccess(JSONArray result) {
                    Log.d("tesst", "result: " + result.toString());
                }

                @Override
                public void onError(VolleyError error) {
                    Log.e("tesst", "error: " + error);
                }
            });

        }

    /*** TODO: METHOD 2 - Single GAME ***/
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public void SingleGame(){
        APIWrapper wrapper = new APIWrapper(getApplicationContext(), "90608a365bde4f14c80a710e2e5c2e85");

        Parameters params = new Parameters()
                .addIds("1942");   // --> The Witcher 3

        wrapper.games(params, new onSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {

                Log.d("tesst", "result: " + result.toString());
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("tesst", "error: " + error);
            }
        });

    }


}
