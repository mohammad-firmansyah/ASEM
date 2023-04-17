package ptpn12.amanat.asem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ptpn12.amanat.asem.adapter.SearchAsetAdapter;
import ptpn12.amanat.asem.api.AsetInterface;
import ptpn12.amanat.asem.api.model.Afdelling;
import ptpn12.amanat.asem.api.model.AsetJenisModel;
import ptpn12.amanat.asem.api.model.AsetKode2;
import ptpn12.amanat.asem.api.model.AsetKodeModel2;
import ptpn12.amanat.asem.api.model.AsetKondisi;
import ptpn12.amanat.asem.api.model.AsetTipe;
import ptpn12.amanat.asem.api.model.DataAllSpinner;
import ptpn12.amanat.asem.api.model.Unit;
import ptpn12.amanat.asem.api.model.UnitModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity {
    Integer unit_id = 0;
    ListView listView;
    DataAllSpinner allSpinner;
    Map<Integer, Integer> mapAfdelingSpinner = new HashMap<Integer, Integer>();
    Map<Integer, Integer> mapSpinnerAfdeling = new HashMap<Integer, Integer>();
    Map<Integer, String> mapAfdeling = new HashMap();
    Map<Long, Integer> mapSap = new HashMap();

    Map<Integer,Integer> mapKodeSpinner = new HashMap();
    Map<Integer,Integer> mapSpinnerkode = new HashMap();

    List<String> listSpinnerSap=new ArrayList<>();
    List<AsetKode2> asetKode2 = new ArrayList<>();
    List<Afdelling> afdeling = new ArrayList<>();

    private AsetInterface asetInterface;
    final Calendar myCalendar1= Calendar.getInstance();
    final Calendar myCalendar2= Calendar.getInstance();
    Spinner spinnerTipeAset;
    SharedPreferences sharedPreferences;
    private static final String PREF_LOGIN = "LOGIN_PREF";
    Spinner spinnerJenisAset;
    Spinner spinnerAsetKondisi;
    Spinner spinnerKodeAset;
    Spinner spinnerJenisReport;
    Spinner spinnerUnit;

    String spinnerIdTipeAsset;
    String spinnerIdJenisAset;
    String spinnerIdAsetKondisi;
    String spinnerIdKodeAset;
    String getSpinnerIdJenisReport;


    Button btnSubmit;
    ImageView imgBack;
    String urlReportv3;
    TextView tvUnit;
    RadioGroup radioGroup;
    EditText inpTglInput1;
    EditText inpTglInput2;
    EditText inpHGU;
    private Dialog dialog;

    RecyclerView rcAset2; // untuk search dan filter
    LonglistAsetActivity longlistAsetActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        sharedPreferences = FilterActivity.this.getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);

        asetInterface = AsemApp.getApiClient().create(AsetInterface.class);

        unit_id = Integer.valueOf(sharedPreferences.getString("unit_id", "0"));
        Log.d("amanat19", String.valueOf(unit_id));
        dialog = new Dialog(FilterActivity.this,R.style.MyAlertDialogTheme);
        dialog.setContentView(R.layout.loading);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        dialog.show();

        rcAset2 = findViewById(R.id.recView2);
        rcAset2.setHasFixedSize(true);
        rcAset2.setLayoutManager(new LinearLayoutManager(this));

        tvUnit = findViewById(R.id.tvUnit);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        spinnerJenisReport = findViewById(R.id.spinnerReport);
        spinnerTipeAset = findViewById(R.id.inpTipeAset);
        spinnerJenisAset = findViewById(R.id.inpJenisAset);
        spinnerAsetKondisi = findViewById(R.id.inpKndsAset);
        spinnerKodeAset = findViewById(R.id.inpKodeAset);
        inpTglInput1 = findViewById(R.id.inpTglInput);
        inpTglInput2 = findViewById(R.id.inpTglInput2);
        btnSubmit = findViewById(R.id.btnSubmit);
        radioGroup = findViewById(R.id.qrcode);
        imgBack = findViewById(R.id.imgBack);

        inpTglInput1 = findViewById(R.id.inpTglInput);
        inpTglInput2 = findViewById(R.id.inpTglInput2);
        inpHGU = findViewById(R.id.inpHGU);

        // globally
        TextView tvTitle = findViewById(R.id.tvTitle);
        //in your OnCreate() method
        tvTitle.setText("FILTER DATA");

        imgBack.setVisibility(View.GONE);



//        getAllSpinnerData();
        getSpinnerData();
        initCalender();
        initCalender2();

        btnSubmit.setOnClickListener(v ->
//                getDataFilter();

                Toast.makeText(this, "Fitur Belum Tersedia", Toast.LENGTH_SHORT).show()
//                apiDownloadReport()
        );

        spinnerTipeAset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdTipeAsset = String.valueOf(position);
                editVisibilityDynamic();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerJenisAset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdJenisAset = String.valueOf(position);
                editVisibilityDynamic();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerAsetKondisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdAsetKondisi = String.valueOf(position);
                editVisibilityDynamic();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerKodeAset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerIdKodeAset = String.valueOf(position);
                editVisibilityDynamic();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

//        spinnerJenisReport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                getSpinnerIdJenisReport = String.valueOf(position);
//                editVisibilityDynamic();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//
//            }
//        });


        spinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


//        inpTglInput2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new DatePickerDialog(ReportActivity.this,date,myCalendar2.get(Calendar.YEAR),myCalendar2.get(Calendar.MONTH),myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
//                initCalender2();
//                updateLabel2();
//            }
//        });

//        editVisibilityDynamic();
    }

//    private void getDataFilter(Call<List<Filter>> call) {
//        call.enqueue(new Callback<List<Filter>>() {
//            @Override
//            public void onResponse(Call<List<Filter>> call, Response<List<Filter>> response) {
//                dialog.dismiss();
//                if(!response.isSuccessful() && response.body() == null){
//                    Toast.makeText(getApplicationContext(), "Data Tidak Ditemukan!", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), "Ulangi Isian Filter!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                List<Filter> dataS = response.body();
//                SearchAsetAdapter adapter = new SearchAsetAdapter(dataS, );
//                rcAset2.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<List<Filter>> call, Throwable t) {
//
//            }
//        });
//
//    }

    private void getTipeAset(){
        Call<List<AsetTipe>> call = asetInterface.getAsetTipe();
        call.enqueue(new Callback<List<AsetTipe>>() {
            @Override
            public void onResponse(Call<List<AsetTipe>> call, Response<List<AsetTipe>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
//                    utils.Ngetoast(getApplicationContext(),);
                    return;
                }
                List<String> listSpinner = new ArrayList<>();
                listSpinner.add("Semua");

                for (int i=0;i<response.body().size();i++){
                    listSpinner.add(response.body().get(i).getAset_tipe_desc());
                }
                Log.d("asetapi", listSpinner.get(0));

                // Set hasil result json ke dalam adapter spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerTipeAset.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<AsetTipe>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAsetKondisi(){
        Call<List<AsetKondisi>> call = asetInterface.getAsetKondisi();
        call.enqueue(new Callback<List<AsetKondisi>>() {
            @Override
            public void onResponse(Call<List<AsetKondisi>> call, Response<List<AsetKondisi>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    return;
                }
                List<String> listSpinner = new ArrayList<>();
                listSpinner.add("Semua");
                for (int i=0;i<response.body().size();i++){
                    Log.d("asetapi2",response.body().get(i).getAset_kondisi_desc());
                    listSpinner.add(response.body().get(i).getAset_kondisi_desc());
                }

                // Set hasil result json ke dalam adapter spinner

                try{

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerAsetKondisi.setAdapter(adapter);
                }
                catch(Exception e ) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<AsetKondisi>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    private void updateLabel(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        inpTglInput1.setText(dateFormat.format(myCalendar1.getTime()));
    }

    public void initCalender(){
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH,month);
                myCalendar1.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        inpTglInput1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(FilterActivity.this,date,myCalendar1.get(Calendar.YEAR),myCalendar1.get(Calendar.MONTH),myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel2(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        inpTglInput2.setText(dateFormat.format(myCalendar2.getTime()));
    }

    public void initCalender2(){
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH,month);
                myCalendar2.set(Calendar.DAY_OF_MONTH,day);
                updateLabel2();
            }
        };
        inpTglInput2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(FilterActivity.this,date,myCalendar2.get(Calendar.YEAR),myCalendar2.get(Calendar.MONTH),myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }




    private void getAsetJenis(){
        Call<AsetJenisModel> call = asetInterface.getAsetJenis();
        call.enqueue(new Callback<AsetJenisModel>() {
            @Override
            public void onResponse(Call<AsetJenisModel> call, Response<AsetJenisModel> response) {

                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    return;
                }

                List<String> listSpinner = new ArrayList<>();
                listSpinner.add("Semua");
                for (int i = 0; i < response.body().getData().size();i++){
                    listSpinner.add(response.body().getData().get(i).getAset_jenis_desc());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerJenisAset.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<AsetJenisModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getKodeAset(){
        Call<AsetKodeModel2> call = asetInterface.getAsetKode();
        call.enqueue(new Callback<AsetKodeModel2>() {
            @Override
            public void onResponse(Call<AsetKodeModel2> call, Response<AsetKodeModel2> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    return;
                }

                List<AsetKode2> asetKode = response.body().getData();
                List<String> listSpinner = new ArrayList<>();
                listSpinner.add("Semua");


                for ( AsetKode2 a : asetKode ){

                    String aset_kode_temp = "";
                    if (a.getAsetJenis() == 2 ) {
                        aset_kode_temp = a.getAsetClass() + "/" + a.getAsetDesc();
                    } else if (a.getAsetJenis() == 1) {
                        aset_kode_temp = a.getAsetClass() + "/" + a.getAsetGroup() + "/" + a.getAsetDesc();
                    } else {
                        aset_kode_temp = a.getAsetClass() + "/" + a.getAsetGroup() + "/" + a.getAsetDesc();
                    }
                    listSpinner.add(aset_kode_temp);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerKodeAset.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<AsetKodeModel2> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    private void getUnit(){
        Call<UnitModel> call = asetInterface.getUnit();
        call.enqueue(new Callback<UnitModel>() {
            @Override
            public void onResponse(Call<UnitModel> call, Response<UnitModel> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    return;
                }

                List<Unit> unit = response.body().getData();
                List<String> listSpinner = new ArrayList<>();
                listSpinner.add("Semua");


                for ( Unit a : unit ){

                    listSpinner.add(a.getUnit_desc());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerUnit.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<UnitModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                return;
            }
        });
    }



    private void getSpinnerData(){
        dialog.show();
        getAsetKondisi();
        getKodeAset();
        getTipeAset();
        getAsetJenis();
        getUnit();


        dialog.dismiss();
    }


    public void editVisibilityDynamic(){

//        TextView tvHgu = findViewById(R.id.tvHGU);
//        tvHgu.setVisibility(View.GONE);


        if (unit_id == 20) {
            spinnerUnit.setVisibility(View.VISIBLE);
            tvUnit.setVisibility(View.VISIBLE);
        } else {
            spinnerUnit.setVisibility(View.GONE);
            tvUnit.setVisibility(View.GONE);
        }

    }
}