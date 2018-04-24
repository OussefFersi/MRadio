package fatalgamer.com.mradioto.entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by FATALGAMER on 12/7/2016.
 */

public class Radio {

    private String desc;
    private String flux;
    private String logo;
    private String name;
    private String location;
    private int nbrR;
    private int nbrlistner;
    private float sommeR;
    private String Webcam_URL;
    private String Facebook;
    private String Twitter;
    private String RSS;
    private String Youtube;
    private String Instagram;
    private Radio() {

    }

    public Radio(String desc, String flux, String logo, String name, String location, int nbrR, int nbrlistner, float sommeR, String webcam_URL, String facebook, String twitter, String RSS, String youtube, String instagram) {
        this.desc = desc;
        this.flux = flux;
        this.logo = logo;
        this.name = name;
        this.location = location;
        this.nbrR = nbrR;
        this.nbrlistner = nbrlistner;
        this.sommeR = sommeR;
        Webcam_URL = webcam_URL;
        Facebook = facebook;
        Twitter = twitter;
        this.RSS = RSS;
        Youtube = youtube;
        Instagram = instagram;
    }
/*
    public Radio(String desc, String flux, String location, String logo, String name, int nbR, int nbrlistner, int sommeR) {
        this.desc = desc;
        this.flux = flux;
        this.location=location;
        this.logo = logo;
        this.name=name;
        this.nbR = nbR;
        this.nbrlistner = nbrlistner;
        this.sommeR = sommeR;
    }*/


    public String getLocation() {
        return location;
    }

    public String getWebcam_URL() {
        return Webcam_URL;
    }

    public void setWebcam_URL(String webcam_URL) {
        Webcam_URL = webcam_URL;
    }

    public String getFacebook() {
        return Facebook;
    }

    public void setFacebook(String facebook) {
        Facebook = facebook;
    }

    public String getTwitter() {
        return Twitter;
    }

    public void setTwitter(String twitter) {
        Twitter = twitter;
    }

    public String getRSS() {
        return RSS;
    }

    public void setRSS(String RSS) {
        this.RSS = RSS;
    }

    public String getYoutube() {
        return Youtube;
    }

    public void setYoutube(String youtube) {
        Youtube = youtube;
    }

    public String getInstagram() {
        return Instagram;
    }

    public void setInstagram(String instagram) {
        Instagram = instagram;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFlux() {
        return flux;
    }

    public void setFlux(String flux) {
        this.flux = flux;
    }

    public int getNbrR() {
        return nbrR;
    }

    public void setNbrR(int nbrR) {
        this.nbrR = nbrR;
    }

    public int getNbrlistner() {
        return nbrlistner;
    }

    public void setNbrlistner(int nbrlistner) {
        this.nbrlistner = nbrlistner;
    }

    public float getSommeR() {
        return sommeR;
    }

    public void setSommeR(float sommeR) {
        this.sommeR = sommeR;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();


        result.put("desc",desc );
        result.put("flux",flux);
        result.put("location",location);
        result.put("logo",logo);
        result.put("name",name );
        result.put("nbrR",nbrR);
        result.put("nbrlistner",nbrlistner);
        result.put("sommeR",sommeR);
        result.put("Webcam_URL",Webcam_URL);
        result.put("Facebook",Facebook);
        result.put("Twitter",Twitter);
        result.put("RSS",RSS);
        result.put("Youtube",Youtube);
        result.put("Instagram",Instagram);

        return result;
    }
}
