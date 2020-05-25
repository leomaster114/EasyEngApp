package com.example.easyengapp.Database;

import com.example.easyengapp.R;

import java.util.ArrayList;

public class ImageForSubject {
    public static ArrayList<Integer> imageId() {
        ArrayList<Integer> imageId = new ArrayList<>();
        imageId.add(R.drawable.family);
        imageId.add(R.drawable.education);
        imageId.add(R.drawable.sport);
        imageId.add(R.drawable.nature);
        imageId.add(R.drawable.travel);
        imageId.add(R.drawable.house);
        imageId.add(R.drawable.vehicle);
        imageId.add(R.drawable.human);
        imageId.add(R.drawable.scient);
        imageId.add(R.drawable.social);
        return imageId;
    }
}
