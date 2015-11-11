package mx.com.planetmedia.androidtest.parser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mx.com.planetmedia.androidtest.constants.Constants;
import mx.com.planetmedia.androidtest.model.ImageModel;

/**
 * Created by Adoniram on 10/11/15.
 */
public class Parser {

    public static ArrayList<ImageModel> getImages(String jsonString){
        ArrayList<ImageModel> dataImage = null;

        try
        {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject responseData = jsonObject.getJSONObject("responseData");
            JSONArray results = responseData.getJSONArray("results");

            if(results.length() > 0 ){
                dataImage = new ArrayList<ImageModel>();

                for(int i = 0; i < results.length(); i++){
                    JSONObject jsonTemp = (JSONObject) results.get(i);

                    ImageModel imgModelTemp = new ImageModel();
                    imgModelTemp.setTitle( (String) jsonTemp.get("titleNoFormatting") );
                    imgModelTemp.setUrl((String) jsonTemp.get("url"));

                    Log.w("Adonis" , "Pinche titulo: " + (String) jsonTemp.get("titleNoFormatting"));

                    dataImage.add(imgModelTemp);
                    imgModelTemp = null;
                    jsonTemp = null;
                }
            }

            if(dataImage != null) {
                Log.w(Constants.TAG, "numero " + dataImage.size());
            }
        }
        catch (JSONException jsonExpection){
            jsonExpection.printStackTrace();
            return null;
        }

        return dataImage;
    }


}
