package ptpn12.amanat.asem.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import id.zelory.compressor.Compressor;


public class utils {
//    FusedLocationProviderClient mFusedLocationClient;
    public static double longitudeValue = 0;
    public static double latitudeValue = 0;


    private void showSnackbarMessage(RecyclerView rcview,String message) {
        Snackbar.make(rcview, message, Snackbar.LENGTH_SHORT).show();
    }

    public static class Directory {
        public static final String DIRECTORY_TANAMAN = "Asem/Aset/Tanaman/Pictures";
        public static final String DIRECTORY_NON_TANAMAN = "Asem/NonTanaman/Pictures";
        public static final String DIRECTORY_KAYU = "Asem/Kayu/Pictures";
        public static final String DIRECTORY_TANAMAN_DOCUMENT = "Asem/Tanaman/Dokumen";
        public static final String DIRECTORY_NON_TANAMAN_DOCUMENT = "Asem/NonTanaman/Dokumen";
        public static final String DIRECTORY_KAYU_DOCUMENT = "Asem/Kayu/Dokumen";

    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeBitmapFile(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static File savePictureResult(Context context, String fotoName, ImageView imageView, boolean isSquare){
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fotoName);
        try {
            Bitmap bitmap;
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),bitmapOptions);
            if (isSquare){
                bitmap = decodeBitmapFile(file.getAbsolutePath(), 100, 100);
            }

            ExifInterface ei = new ExifInterface(file.getAbsolutePath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            Bitmap rotatedBitmap = rotateImage(bitmap, orientation);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(rotatedBitmap);

            file = new Compressor(context).compressToFile(file);

        } catch (Exception e){
            e.printStackTrace();
            Ngetoast(context, "Error saved picture : "+e.getMessage());
        }

        return file;
    }
    public static Bitmap rotateImage(Bitmap source, int orientation) {
        Matrix matrix = new Matrix();
        Bitmap rotatedBitmap = null;
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                rotatedBitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                        matrix, true);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                rotatedBitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                        matrix, true);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                rotatedBitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                        matrix, true);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = source;
        }

        return rotatedBitmap;
    }

    public static void Ngetoast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public static void setExifLocation(File fileImage){
        try {
            ExifInterface exif = new ExifInterface(fileImage.getAbsoluteFile().getAbsolutePath());
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, GpsConverter.convert(latitudeValue));
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, GpsConverter.latitudeRef(latitudeValue));
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, GpsConverter.convert(longitudeValue));
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, GpsConverter.latitudeRef(longitudeValue));
            exif.saveAttributes();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Untuk mendownload satu foto
     * @param context context
     * @param imageURL url image yang akan di unduh .String
     * @param imageName nama image yang di simpan. String
     * @return downloadmanager.enqueu(request) .long
     */
    public static long downloadImage(Context context, String imageURL, String imageName){
        long downloadID = 0;

        try {
            String dirPath = Directory.DIRECTORY_TANAMAN+File.separator+imageName;
            //            File fileDir = new File(
            //                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            //                    OssPlanters.Directory.DIRECTORY_TEBU_TMA_PICTURE
            //            );
            //            if (!fileDir.exists()){
            //                fileDir.mkdirs();
            //            }
            //            File fileResult = new File(fileDir.getAbsolutePath() + File.separator + imageName);

            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

            Uri downloadUri = Uri.parse(imageURL);
            DownloadManager.Request request = new DownloadManager.Request(
                    downloadUri);

            request.setAllowedNetworkTypes(
                            DownloadManager.Request.NETWORK_WIFI
                                    | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false).setTitle("Download Image")
                    .setDescription("Mengunduh foto oss")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, dirPath);
            //                    .setDestinationUri(Uri.fromFile(fileResult));

            downloadID = downloadManager.enqueue(request);
            return downloadID;
        } catch (Exception e){
            e.printStackTrace();
        }
        return downloadID;
    }



    public static String MonthToYear(int month) {
        int year = 0;
        if (month <= 0 && year <= 0) {
            String result = "0";
            return result;
        }

        while (true) {



            if (year == 0 && month < 12) {


                String result = String.valueOf(year) + " Tahun " + String.valueOf(month) +" Bulan";
                return result;

            }



            month -= 12;
            year++;



            if (month < 12 ) {
                String result = String.valueOf(year) + " Tahun " + String.valueOf(month) +" Bulan";
                return  result;
            }

        }
    }

    public static String Formatrupiah(Double number){
        Locale localeID = new Locale("IND","ID");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(localeID);
        String formatRupiah =  numberFormat.format(number);
        String[] split = formatRupiah.split(",");
        int length = split[0].length();
        return split[0].substring(0,2)+". "+split[0].substring(2,length);
    }

    public static Long CurrencyToNumber(String curr){
        String s= "";
        String[] splited = curr.split(" ");
        s=splited[1];
//        ArrayList<String> splitedTwc = new ArrayList<String>();
        if (curr.contains(".")){
            String[] splitedTwc = s.split("[.]");

            String result = "";
            for (String split : splitedTwc){
                result+=split;
            }

            return Long.parseLong(result);
        } else{
            return Long.parseLong(s);
        }}

        public static String CurrencyToNumberString(String curr){
        String s= "";
        String[] splited = curr.split(" ");
        s=splited[1];
//        ArrayList<String> splitedTwc = new ArrayList<String>();
        if (curr.contains(".")){
            String[] splitedTwc = s.split("[.]");

            String result = "";
            for (String split : splitedTwc){
                result+=split;
            }

            return result;
        } else{
            return s;
        }

        // System.out.println(splitedTwc.length);
    }


    public static Integer masaSusutToMonth(Integer masasusut,String tglinput) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(tglinput);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date now = new Date();

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(now);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date);

        Integer monthsDiff = (cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR)) * 12 + (cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH));
        Log.d("debug-masasusut", String.valueOf(monthsDiff));
        Log.d("debug-masasusut2", String.valueOf((masasusut*12) - monthsDiff));
        if (monthsDiff < 0) {

            return (masasusut*12) + monthsDiff;
        }

        return (masasusut*12) - monthsDiff;
    }

}



