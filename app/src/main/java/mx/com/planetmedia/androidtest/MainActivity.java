package mx.com.planetmedia.androidtest;

import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;

import mx.com.planetmedia.androidtest.constants.Constants;
import mx.com.planetmedia.androidtest.model.ImageModel;
import mx.com.planetmedia.androidtest.model.ImagesModel;

public class MainActivity extends FragmentActivity  implements MainFragment.OnItemSelectedListener {

    private FrameLayout mFragmentContainer;
    private MainFragment mainFragment;
    private ImagesModel data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUI(savedInstanceState);
    }

    private void setUI(Bundle savedInstanceState){
        mFragmentContainer = (FrameLayout) findViewById(R.id.fragmentContainer);

        if (mFragmentContainer != null) {

            if (savedInstanceState != null) {
                return;
            }

            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, mainFragment).commit();
        }
    }


    @Override
    public void onItemSelected(ImageModel model) {
        // Create fragment and give it an argument specifying the article it should show
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setItemImage(model);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, detailFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }


    @Override
    public void onDataCharged(ImagesModel data) {
        this.data = data;
    }
}
