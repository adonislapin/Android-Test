package mx.com.planetmedia.androidtest;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import mx.com.planetmedia.androidtest.model.ImageModel;

public class DetailFragment extends Fragment {

    public ImageModel getItemImage() {
        return itemImage;
    }

    public void setItemImage(ImageModel itemImage) {
        this.itemImage = itemImage;
    }

    private ImageModel itemImage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView imageView = (ImageView) getView().findViewById(R.id.imgDetail);

        Picasso.with(getActivity())
                .load(itemImage.getUrl())
                .into(imageView);

    }
}
