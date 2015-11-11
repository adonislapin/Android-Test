package mx.com.planetmedia.androidtest;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mx.com.planetmedia.androidtest.constants.Constants;
import mx.com.planetmedia.androidtest.controller.SearchController;
import mx.com.planetmedia.androidtest.model.ImageModel;
import mx.com.planetmedia.androidtest.model.ImagesModel;

public class MainFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private AlertDialog.Builder alertDialog;

    private Button btnSetText;

    private ListView lstData;

    private EditText input;
    private SearchView mSearchView;

    private ProgressBar progressBar;

    private SearchController mSearchController;

    private ImageAdapter mImageAdapter;

    private ImagesModel mImagesData;

    private OnItemSelectedListener mListener;

    public void setmImagesData(ImagesModel mImagesData) {
        this.mImagesData = mImagesData;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSearchController = new SearchController(this);

        setUI();
        if(mImagesData != null){
            setImageData(mImagesData.getImages());
        }
    }

    private void setUI(){
        btnSetText = (Button) getView().findViewById(R.id.btnSetText);
        btnSetText.setOnClickListener(this);

        progressBar = (ProgressBar) getView().findViewById(R.id.progressBar);

        lstData = (ListView) getView().findViewById(R.id.lstData);
        lstData.setOnItemClickListener(this);

        alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Buscar");
        alertDialog.setMessage(getString(R.string.principal_ingrese));

        input = new EditText(getActivity());
        mSearchView = new SearchView(getActivity());

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                showProgress(true);
                mSearchController.callGoogleApiImageSearch(input.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton(getString(R.string.principal_busqueda),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        showProgress(true);
                        mSearchController.callGoogleApiImageSearch(input.getText().toString());
                    }
                });
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnSetText){
            alertDialog.show();
        }
    }

    public void showProgress(boolean visible){
        progressBar.setIndeterminate(visible);
        progressBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setImageData(ArrayList<ImageModel> images){
        if(images != null){
            if(mImageAdapter == null){
                mImagesData = new ImagesModel(images);
                mImageAdapter = new ImageAdapter(getActivity(),0,mImagesData.getImages());
                lstData.setAdapter(mImageAdapter);
            } else{
                mImagesData.setImages(images);
                mImageAdapter.clear();
                mImageAdapter.addAll(images);
                mImageAdapter.notifyDataSetChanged();
            }
            mListener.onDataCharged(mImagesData);
        } else {
            Toast.makeText(getActivity(), "Lo sentimos ocurrio algo, intenta mas tarde", Toast.LENGTH_LONG).show();
        }
        showProgress(false);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(view.getTag() instanceof ImageModel){
            Log.w(Constants.TAG, " url: " +  ((ImageModel)view.getTag()).getUrl() );
            mListener.onItemSelected(((ImageModel)view.getTag()));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnItemSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

    public interface OnItemSelectedListener {
        public void onItemSelected(ImageModel model);
        public void onDataCharged(ImagesModel data);
    }

    public class ImageAdapter extends ArrayAdapter<ImageModel> {

        public ImageAdapter(Context context, int resource, List<ImageModel> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if(view == null){
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.image_item, parent, false);
            }

            ImageModel item = getItem(position);

            if(item != null){
                TextView title = (TextView) view.findViewById(R.id.title);
                if(title != null){
                    title.setText(item.getTitle());
                    view.setTag(item);
                }
            }

            return view;
        }
    }



}
