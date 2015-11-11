package mx.com.planetmedia.androidtest.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Adoniram on 11/11/15.
 */
public class ImagesModel implements Serializable {

    private ArrayList<ImageModel> images;

    public ArrayList<ImageModel> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageModel> images) {
        this.images.clear();
        this.images = images;
    }

    public ImagesModel(ArrayList<ImageModel> images){
        this.images = images;
    }

    public ArrayList<ImageModel> getImagesModel(){
        return this.images;
    }



}
