package g.y.v.vyg;


public class Constants {

    public static String  gmApi(String latitude,String longitude,String radius){
        String url= "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius="+radius+"&type=restaurant&key=AIzaSyC3_ndLS93DsNFqSB-78VuA00A0hrI8B5A";
        return url;
    }
}

//https://developers.google.com/places/web-service/search