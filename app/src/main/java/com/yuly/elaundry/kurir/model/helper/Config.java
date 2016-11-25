package com.yuly.elaundry.kurir.model.helper;

/**
 * Created by anonymous on 22/02/16.
 */

public class Config {
    //JSON URL
 //   public static final String DATA_URL = "http://192.168.2.249/elaundry/v1/index.php/harga";

    //Tags used in the JSON String
    public static final String TAG_USERNAME = "packet";
  //  public static final String TAG_NAME = "harga";
    public static final String TAG_COURSE = "keterangan";
    public static final String TAG_SESSION = "status";

    //JSON array name
    public static final String JSON_ARRAY = "harga";

/*
    0:  {
        "error": false
        "harga": {
            "id": 1
            "packet": "Hemat"
            "harga": "5000"
            "keterangan": "keterangan paket hemat"
            "status": "1"
        }-
    }*/

    //URL of my API
    public static final String DATA_URL = "http://simplifiedcoding.16mb.com/PesananModels/superheroes.php";

    //Tags for my JSON
    public static final String TAG_IMAGE_URL = "image";
    public static final String TAG_NAME = "name";
    public static final String TAG_RANK = "rank";
    public static final String TAG_REAL_NAME = "realName";
    public static final String TAG_CREATED_BY = "createdBy";
    public static final String TAG_FIRST_APPEARANCE = "firstAppearance";
    public static final String TAG_POWERS = "powers";
}