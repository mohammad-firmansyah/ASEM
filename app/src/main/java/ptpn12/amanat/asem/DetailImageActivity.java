package ptpn12.amanat.asem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import ptpn12.amanat.asem.R;
import com.squareup.picasso.Picasso;

public class DetailImageActivity extends AppCompatActivity {

//    public static String baseUrl = "http://202.148.9.226:7710/mnj_aset_production/public/api/";
//    public static String baseUrlImg = "http://202.148.9.226:7710/mnj_aset_production/public";
    //    public static String baseUrl = "http://amanat.ptpn12.com/api/";
//        public static String baseUrlAset = "http://202.148.9.226:7710/mnj_aset_production/public";
//     public static String baseUrlImg = "http://amanat.ptpn12.com";
    String url;
    ImageView detailImgData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        detailImgData = findViewById(R.id.detailImgData);

//        url = baseUrlImg + url;
        Log.d("asetapix",url);
        Picasso.get().load(url).fit().into(detailImgData);
    }
}