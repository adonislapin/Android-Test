package mx.com.planetmedia.androidtest.controller;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mx.com.planetmedia.androidtest.MainFragment;
import mx.com.planetmedia.androidtest.constants.Constants;
import mx.com.planetmedia.androidtest.io.HttpInvoker;
import mx.com.planetmedia.androidtest.model.ImageModel;
import mx.com.planetmedia.androidtest.parser.Parser;

/**
 * Created by Adoniram on 10/11/15.
 */
public class SearchController {
    private MainFragment mContext;

    Response.Listener mResponse = new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject response) {
            Log.d(Constants.TAG, response.toString());
            ArrayList<ImageModel> imageData = Parser.getImages(response.toString());

            mContext.setImageData(imageData);
        }
    };

    Response.ErrorListener mErrorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            VolleyLog.d(Constants.TAG, "Error: " + error.getMessage());
            mContext.showProgress(false);
        }
    };

    public SearchController (MainFragment context){
        this.mContext = context;
    }

    public void callGoogleApiImageSearch(String search){
        HttpInvoker.getGoogleAPIImageResponseFor(search, mResponse, mErrorListener );
    }




}
