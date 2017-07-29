package com.hanson.geoclick;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hanson.geoclick.Adapters.GalleryAdapter;
import com.hanson.geoclick.Helper.DBHelper;
import com.hanson.geoclick.Helper.ImageHelper;
import com.hanson.geoclick.Model.CityItem;
import com.hanson.geoclick.Model.PictureItem;

import java.util.ArrayList;


public class GalleryFragment extends Fragment {
    ///////////////////////////////////////////////////////
    ImageHelper imgeHelper = new ImageHelper();

    ArrayList<CityItem> PicList;
    ///////////////////////////////////////////////////////
    private final String image_titles[] = {
            "Img1",
            "Img2",
            "Img3",
            "Img4",
            "Img5",
            "Img6",
            "Img7",
            "Img8",
    };

    private final Integer image_ids[] = {
            R.drawable.lion,
            R.drawable.micky,
            R.drawable.logowithtitle,
            R.drawable.fire,
            R.drawable.lion,
            R.drawable.micky,
            R.drawable.logowithtitle,
            R.drawable.fire,

    };

    ArrayList<PictureItem> pictures = new ArrayList<PictureItem>();


    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_gallery, container, false);



///////////////////////////////////////////////////////
        //Connect with databases
        DBHelper dbHelper = new DBHelper(this.getContext(), "Picture.db", null, 1);
        //getting the images
        //PicList = dbHelper.selectPicFromCity("Milan");
       PicList = dbHelper.selectPicGroupByCity();

        ArrayList<CityItem> mockcities = new ArrayList<>();

        mockcities = dbHelper.selectPicGroupByCity();

        for(int i=0; i<mockcities.size(); i++){
            mockcities.get(i).setImage_id(i);
        }

        ///////////////////////////////////////////////////////

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        //ArrayList<CreateList> createLists = prepareData();
        GalleryAdapter adapter = new GalleryAdapter(getActivity().getApplicationContext(), mockcities);
        recyclerView.setAdapter(adapter);



        return view;
    }

    //to fix!!!

    private ArrayList<CreateList> prepareData(){

        ArrayList<CreateList> theimage = new ArrayList<>();
        for(int i = 0; i< image_titles.length; i++){
            CreateList createList = new CreateList();
            createList.setImage_title(image_titles[i]);
            createList.setImage_ID(image_ids[i]);
            theimage.add(createList);
        }
        return theimage;
    }


}



