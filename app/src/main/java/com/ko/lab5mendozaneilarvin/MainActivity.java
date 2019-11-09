package com.ko.lab5mendozaneilarvin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String[] company, country, industry, ceo, info;
    ListView lstversions;

    int[] logo = {R.drawable.icbc, R.drawable.jpmorgan, R.drawable.chinaconstruction, R.drawable.agriculturalbank, R.drawable.bankofamerica,
            R.drawable.apple, R.drawable.pingan, R.drawable.bankofchina, R.drawable.shell, R.drawable.wellsfargo,
            R.drawable.exxon, R.drawable.att, R.drawable.samsung, R.drawable.citibank,};

    ArrayList<AndroidVersion> versions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        company = getResources().getStringArray(R.array.company);
        country = getResources().getStringArray(R.array.country);
        industry = getResources().getStringArray(R.array.industry);
        ceo = getResources().getStringArray(R.array.ceo);
        info = getResources().getStringArray(R.array.info);

        for (int i = 0; i < company.length; i++) {
            versions.add(new AndroidVersion(logo[i], company[i], "Country: " + country[i], "Industry: " + industry[i], "CEO: " + ceo[i]));
        }

        AndroidAdapter adapter = new AndroidAdapter(this, R.layout.company, versions);
        lstversions = findViewById(R.id.lvAndroid);
        lstversions.setAdapter(adapter);
        lstversions.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long id) {
        final File folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        final File file = new File(folder, "companies.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            String choice_name = versions.get(i).getCompany() + "\n";
            String choice_country = versions.get(i).getCountry() + "\n";
            String choice_industry = versions.get(i).getIndustry() + "\n";
            String choice_ceo = versions.get(i).getCeo();
            fos.write(choice_name.getBytes());
            fos.write(choice_country.getBytes());
            fos.write(choice_industry.getBytes());
            fos.write(choice_ceo.getBytes());
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setIcon(logo[i]);
            dialog.setTitle(company[i]);
            dialog.setMessage(info[i]);
            dialog.setNeutralButton("Close", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int position) {
                    dialog.dismiss();
                    String data = "";
                    try {
                        FileInputStream fis = new FileInputStream(file);
                        int token;
                        while ((token = fis.read()) != -1) {
                            data = data + (char) token;
                        }
                    } catch (FileNotFoundException fne) {

                    } catch (IOException io) {

                    }
                    Toast.makeText(MainActivity.this, data, Toast.LENGTH_LONG).show();
                }
            });
            dialog.create().show();
        } catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.this, "File Not Found", Toast.LENGTH_LONG).show();
        } catch (IOException io) {
            Toast.makeText(MainActivity.this, "IO Exception", Toast.LENGTH_LONG).show();
        }
    }
}
