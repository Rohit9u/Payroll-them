package api;


import android.app.Application;

public class documentApi extends Application {
    private String randomid;
    public String getRandomid() {
        return randomid;
    }

    public void setRandomid(String randomid) {
        this.randomid = randomid;
    }
}
