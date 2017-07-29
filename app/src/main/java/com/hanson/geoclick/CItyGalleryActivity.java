package com.hanson.geoclick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hanson.geoclick.Adapters.CityGalleryAdapter;
import com.hanson.geoclick.Helper.DBHelper;
import com.hanson.geoclick.Model.PictureItem;

import java.util.ArrayList;

public class CItyGalleryActivity extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(this, "Picture.db", null, 1);
    ArrayList<PictureItem> picCities;
    //ArrayList<CityItem> picCities2;

    //PictureItem SelePic = new PictureItem()
    private final String image_titles[] = {
            "",

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_gallery);

        picCities = dbHelper.selectPicFromCity("Milan");

        //picCities2 = dbHelper.selectPicGroupByCity();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        //ArrayList<CreateList> createLists = prepareData();
        //GalleryAdapter adapter = new GalleryAdapter(getApplicationContext(), createLists);
        CityGalleryAdapter adapter = new CityGalleryAdapter(getApplicationContext(), picCities);
        recyclerView.setAdapter(adapter);
    }


    //TO FIX!!!
    private ArrayList<CreateList> prepareData(){

        ArrayList<CreateList> theimage = new ArrayList<>();
        for(int i = 0; i< image_ids.length; i++){
            CreateList createList = new CreateList();
            createList.setImage_title("");
            createList.setImage_ID(image_ids[i]);
            //createList.setImage_ID(picCities.get(i).get_mainImg());
            theimage.add(createList);
        }
        return theimage;
    }

}
