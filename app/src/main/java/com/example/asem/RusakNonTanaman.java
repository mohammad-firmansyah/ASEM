package com.example.asem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RusakNonTanaman extends AppCompatActivity {
    private Button inpUploudBA;
    private TextView tvUploudBA;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_UPLOAD_SPTA = 13;
    private static final int REQUEST_UPLOAD_NOTA = 14;

    File imageSPTA;
    File imageNota;
    ImageView imgViewSpta;
    ImageView imgViewNotaPG;


    Integer estimasi;
    Integer afdelID;
    Integer zonasiID;
    Integer petaktebang;
    Integer tebangmuat;
    Integer kebunID;
    Integer rentengID;
    Integer ptaID;

    String nopol;
    String AUTH_API;
    String TOKEN_API;
    String date;
    String time;
    String docPath;
    String notaPath;
    String tebmuat_askep;
    String valSpAfdeling;
    String valSpPetak;
    String notaURL;

    TextView imgPath;
    private static final int PICK_IMAGE_REQUEST = 9544;
    ImageView image;
    Uri selectedImage;
    String part_image;
    public static final int RESULT_CODE = 110;
    public static final String EXTRA_SELECTED_VALUE = "extra_selected_value";
    private Button btnFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rusak_non_tanaman);
        btnFile = findViewById(R.id.inpUploudBA);

        btnFile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RusakNonTanaman.this, FilePickerActivity.class);

                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder().setCheckPermission(true).setShowFiles(true).setShowImages(false).setShowVideos(false).setMaxSelection(1).setSuffixes("txt","pdf","doc","docx").setSkipZeroSizeFiles(true).build());
                startActivityForResult(intent,102);

            }


        });
    }



    // This method will be generating the Bitmap Object from the URL of image.
    public static Bitmap getBitmapFromURL(String src) {
        final Bitmap[] bmp = new Bitmap[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(src);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    bmp[0] = BitmapFactory.decodeStream(input);
                } catch (IOException e) {
                    e.printStackTrace();
                    bmp[0] = null;
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bmp[0];
    }


    public void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (resultCode == RESULT_OK && data != null ) {
            ArrayList<MediaFile> mediaFiles = data.getParcelableArrayListExtra(
                    FilePickerActivity.MEDIA_FILES
            );
            String path =  mediaFiles.get(0).getPath();

            switch (requestCode) {
                case 102:
                    String d = "File path :" + path;
                    Toast.makeText(getApplicationContext(),d,Toast.LENGTH_SHORT).show();

                    tvUploudBA.setText(d);
                    break;

            }
        }
    }

    // Upload the image to the remote database
//
//    private String queryName(Context context, Uri uri) {
//        Cursor returnCursor =
//                context.getContentResolver().query(uri, null, null, null, null);
//        assert returnCursor != null;
//        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//        returnCursor.moveToFirst();
//        String name = returnCursor.getString(nameIndex);
//        returnCursor.close();
//        return name;
//    }
//
//    public File getFile(Context context, Uri uri) throws IOException {
//        // save file to directory Documents di package aplikasi
//        File destinationFilename = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath() + File.separatorChar + queryName(context, uri));
//        try (InputStream ins = context.getContentResolver().openInputStream(uri)) {
//            createFileFromStream(ins, destinationFilename);
//        } catch (Exception ex) {
//            Toast.makeText(RusakNonTanaman.this, "File tidak dapat dimuat", Toast.LENGTH_LONG).show();
//            Log.e("Save File", ex.getMessage());
//            ex.printStackTrace();
//        }
//        return destinationFilename;
//    }
//
//    // file yang dipilih akan di copy ke directory aplikasi
//    public void createFileFromStream(InputStream ins, File destination) {
//        try (OutputStream os = new FileOutputStream(destination)) {
//            byte[] buffer = new byte[4096];
//            int length;
//            while ((length = ins.read(buffer)) > 0) {
//                os.write(buffer, 0, length);
//            }
//            os.flush();
//        } catch (Exception ex) {
//            Log.e("Save File", ex.getMessage());
//            ex.printStackTrace();
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_UPLOAD_SPTA && resultCode == Activity.RESULT_OK){
//            assert data != null;
//            Uri urifile = data.getData();
//    }
}