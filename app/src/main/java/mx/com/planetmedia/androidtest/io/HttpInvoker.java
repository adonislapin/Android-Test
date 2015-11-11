package mx.com.planetmedia.androidtest.io;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import mx.com.planetmedia.androidtest.application.AppController;
import mx.com.planetmedia.androidtest.constants.Constants;
/**
 * Created by Adoniram on 10/11/15.
 */
public class HttpInvoker {

    private static String tag_json_obj_cancel = "json_obj_req";


    public static void getGoogleAPIImageResponseFor(String searchText , Response.Listener listener , Response.ErrorListener errorListener){

        try {
            String requestURL = Constants.url + URLEncoder.encode(searchText, "UTF-8");
            Log.w(Constants.TAG , "Requested url: " + requestURL);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,requestURL , (String)null,
                    listener, errorListener);

            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj_cancel);
        }
        catch (UnsupportedEncodingException uex){
            uex.printStackTrace();
        }


    }


}
