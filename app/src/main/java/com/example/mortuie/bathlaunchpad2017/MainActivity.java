package com.example.mortuie.bathlaunchpad2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addDropDownOfRoutes();
        TextView points = (TextView) findViewById(R.id.points);
        points.setText(String.valueOf(getPoints()));
    }

    public void goToRoute(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("routeID", 1);
        startActivity(intent);
    }

    public void goToTest(View view) {
        Intent intent = new Intent(this, test.class);
        startActivity(intent);
    }

    public void goToRewards(View view) {
        Intent intent = new Intent(this, Rewards.class);
        startActivity(intent);
    }

    private void addDropDownOfRoutes() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Button customRoute = (Button) findViewById(R.id.customRoute);
        ArrayList<String> routeNames = new ArrayList<>();
        // Adds the route names to the arraylist
        try {
            spinner.setVisibility(View.VISIBLE);
            customRoute.setVisibility(View.VISIBLE);
            FileInputStream fis = openFileInput("data.txt");
            byte[] buffer = new byte[1024];
            int n = 0;
            while ((n = fis.read(buffer)) != -1) {
                String string = new String(buffer, 0, n);
                String[] split = string.split("\n");
                for (int i = 0; i < split.length; i++) {
                    String[] splitAgain = split[i].split(",");
                    if (splitAgain[0].equals("route")) {
                        routeNames.add(splitAgain[1]);
                    }
                }
            }
            fis.close();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, routeNames);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        } catch (IOException e) {
            spinner.setVisibility(View.INVISIBLE);
            customRoute.setVisibility(View.INVISIBLE);

        }
    }

    public void goToCustomRoute(View view) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("routeID", spinner.getSelectedItem().toString());
        startActivity(intent);
    }

    private int getPoints() {
        try {
            FileInputStream fis = openFileInput("points.txt");
            byte[] buffer = new byte[1024];
            int n = 0;
            String string = "";
            while ((n = fis.read(buffer)) != -1) {
                string = new String(buffer, 0, n);
            }
            return Integer.parseInt(string);
        } catch (IOException e) {
            return 0;
        }
    }


}
