package com.example.application_navibountho;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity {
    private String[] defaultValues;
    private EditText nameEdit;
    private EditText speciesEdit;
    private EditText heightEdit;
    private EditText weightEdit;
    private EditText hpEdit;
    private EditText attackEdit;
    private EditText defenseEdit;
    private EditText nationalNumEdit;
    private Button resetButton;
    private Button submitButton;
    private Button pokedexButton;
    private RadioGroup genderRadioGroup;
    private Spinner levelSpinner;
    private int nextLayout = 0;


    private int[] layouts = {R.layout.linear_layout, R.layout.table_layout, R.layout.constraint_layout};
    private Button layoutSwitcher;

    int currentLayoutID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.constraint_layout);
        layoutSwitcher = findViewById(R.id.layoutSwitcher);
        layoutSwitcher.setOnClickListener(layoutListener);
        levelSpinner = findViewById(R.id.levelSpinner);
        defaultValues = new String[]{"896", "Glastrier", "Wild Horse Pokemon", "2.2", "800.0", "0", "0", "0"};
        initiallizeViewsAndListeners();
        ArrayList<Integer> levels = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            levels.add(i);
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, levels);

        levelSpinner.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }


    View.OnClickListener layoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currentLayoutID = layouts[nextLayout];
            setContentView(currentLayoutID);
            initiallizeViewsAndListeners();
            nextLayout = (nextLayout + 1) % layouts.length;
            String layoutName = getResources().getResourceName(currentLayoutID).toString();
            Toast.makeText(v.getContext(), "Switched to " + layoutName, Toast.LENGTH_SHORT).show();
        }
    };

    public void initiallizeViewsAndListeners() {

        layoutSwitcher = findViewById(R.id.layoutSwitcher);

        if (layoutSwitcher != null) {
            layoutSwitcher.setOnClickListener(layoutListener);
        } else {
            Log.e("layoutSwitcher", "layoutSwitcher is null");
        }

        defaultValues = new String[]{"896", "Glastrier", "Wild Horse Pokemon", "2.2", "800.0", "0", "0", "0"};
        nameEdit = findViewById(R.id.nameEdit);
        speciesEdit = findViewById(R.id.speciesEdit);
        heightEdit = findViewById(R.id.heightEdit);
        weightEdit = findViewById(R.id.weightEdit);
        hpEdit = findViewById(R.id.hpEdit);
        attackEdit = findViewById(R.id.attackEdit);
        defenseEdit = findViewById(R.id.defenseEdit);
        nationalNumEdit = findViewById(R.id.nationalNumEdit);
        resetButton = findViewById(R.id.resetButton);
        submitButton = findViewById(R.id.submitButton);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        levelSpinner = findViewById(R.id.levelSpinner);
        pokedexButton = findViewById(R.id.PokedexListButton);


        int genderID = genderRadioGroup.getCheckedRadioButtonId();


        if (submitButton != null) {
            submitButton.setOnClickListener(submitListener);
        }
        if (resetButton != null) {
            resetButton.setOnClickListener(resetListener);
        }
        if(pokedexButton != null){
            pokedexButton.setOnClickListener(pokedexListener);
        }

    }

    View.OnClickListener pokedexListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             Intent intent = new Intent(MainActivity.this, PokeDexListActivity.class);
             startActivity(intent);
        }
    };
    View.OnClickListener resetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            genderRadioGroup.clearCheck();
            nationalNumEdit.setText(defaultValues[0]);
            nameEdit.setText(defaultValues[1]);
            speciesEdit.setText(defaultValues[2]);
            heightEdit.setText(defaultValues[3]);
            weightEdit.setText(defaultValues[4]);
            hpEdit.setText(defaultValues[5]);
            attackEdit.setText(defaultValues[6]);
            defenseEdit.setText(defaultValues[7]);
            heightEdit.append("m");
            weightEdit.append("kg");
            levelSpinner.setSelection(0);


            Toast.makeText(v.getContext(), "Reset", Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener submitListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String name, species, nationNumStr, heightStr, weightStr, hpStr, attackStr, defenseStr;
            int hp, attack, defense;
            float height, weight;


            nationNumStr = nationalNumEdit.getText().toString();
            species = speciesEdit.getText().toString();
            heightStr = heightEdit.getText().toString();
            weightStr = weightEdit.getText().toString();
            hpStr = hpEdit.getText().toString();
            attackStr = attackEdit.getText().toString();
            defenseStr = defenseEdit.getText().toString();
            name = nameEdit.getText().toString();

            if (heightStr.contains("m")) {
                heightStr = heightStr.replace("m", "");
                height = Float.parseFloat(heightStr);
            } else {
                height = Float.parseFloat(heightStr);
            }
            if (weightStr.contains("kg")) {
                weightStr = weightStr.replace("kg", "");
                weight = Float.parseFloat(weightStr);
            } else {
                weight = Float.parseFloat(weightStr);
            }


            hp = Integer.parseInt(hpStr);
            attack = Integer.parseInt(attackStr);
            defense = Integer.parseInt(defenseStr);


            int nationNum = Integer.parseInt(nationNumStr);

            if (nationNumStr.isEmpty() || name.isEmpty() || species.isEmpty() || heightStr.isEmpty() ||
                    weightStr.isEmpty() || hpStr.isEmpty() || attackStr.isEmpty() || defenseStr.isEmpty()) {
                Toast.makeText(v.getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (nationNum < 0 || nationNum > 1010) {
                nationalNumEdit.setTextColor(Color.RED);
                nationalNumEdit.setError("Invalid National Number");
                Toast.makeText(v.getContext(), "National Number must be between 0 and 1010", Toast.LENGTH_LONG).show();
            } else if (name.length() >= 12 || name.length() <= 3) {
                nameEdit.setTextColor(Color.RED);
                nameEdit.setError("Name must have between 3 and 12 characters");
                Toast.makeText(v.getContext(), "Name must have between 3 and 12 characters", Toast.LENGTH_LONG).show();
            } else if (hp < 1 || hp > 362) {
                hpEdit.setTextColor(Color.RED);
                hpEdit.setError("HP must be between 1 and 362");
                Toast.makeText(v.getContext(), "HP must be between 1 and 362", Toast.LENGTH_LONG).show();
            } else if (attack < 1 || attack > 562) {
                attackEdit.setTextColor(Color.RED);
                attackEdit.setError("Attack must be between 1 and 562");
                Toast.makeText(v.getContext(), "Attack must be between 1 and 562", Toast.LENGTH_LONG).show();
            } else if (defense < 10 || defense > 614) {
                defenseEdit.setError("Defense must be between 10 and 614");
                defenseEdit.setTextColor(Color.RED);
                Toast.makeText(v.getContext(), "Defense must be between 10 and 614", Toast.LENGTH_LONG).show();
            } else if (height < 0.2 || height > 169.99) {
                heightEdit.setTextColor(Color.RED);
                heightEdit.setError("Height must be between 0.2 and 169.99");
                Toast.makeText(v.getContext(), "Height must be between 0.2 and 169.99", Toast.LENGTH_LONG).show();
            } else if (weight < 0.1 || weight > 992.7) {
                weightEdit.setTextColor(Color.RED);
                weightEdit.setError("Weight must be between 0.1 and 992.7");
                Toast.makeText(v.getContext(), "Weight must be between 0.1 and 992.7", Toast.LENGTH_LONG).show();
            } else if (genderRadioGroup.getCheckedRadioButtonId() == -1) {
                genderRadioGroup.setBackgroundColor(Color.RED);
                Toast.makeText(v.getContext(), "Please select a gender", Toast.LENGTH_SHORT).show();
            } else {


                nationalNumEdit.setTextColor(Color.BLACK);
                nameEdit.setTextColor(Color.BLACK);
                speciesEdit.setTextColor(Color.BLACK);
                heightEdit.setTextColor(Color.BLACK);
                weightEdit.setTextColor(Color.BLACK);
                hpEdit.setTextColor(Color.BLACK);
                attackEdit.setTextColor(Color.BLACK);
                defenseEdit.setTextColor(Color.BLACK);
                genderRadioGroup.setBackgroundColor(Color.TRANSPARENT);

                if (!heightEdit.getText().toString().endsWith("m")) {
                    heightEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                    heightEdit.append("m");

                }
                if (!weightEdit.getText().toString().trim().endsWith("kg")) {
                    weightEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                    weightEdit.append("kg");
                }

                heightEdit.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                weightEdit.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

                RadioGroup genderRadioGroup = findViewById(R.id.genderRadioGroup);
                RadioButton rb = findViewById(genderRadioGroup.getCheckedRadioButtonId());
                String gender = rb.getText().toString();


                ContentValues cv = new ContentValues();
                cv.put(MyContentProvider.NATIONAL_NUM_COL,nationNum);
                cv.put(MyContentProvider.NAME_COL, name);
                cv.put(MyContentProvider.SPECIES_COL, species);
                cv.put(MyContentProvider.GENDER_COL, gender);
                cv.put(MyContentProvider.HEIGHT_COL, height);
                cv.put(MyContentProvider.WEIGHT_COL, weight);
                cv.put(MyContentProvider.LEVEL_COL, levelSpinner.getSelectedItem().toString());
                cv.put(MyContentProvider.HP_COL,hp);
                cv.put(MyContentProvider.ATTACK_COL,attack);
                cv.put(MyContentProvider.DEFENSE_COL,defense);
                ContentResolver cr = getContentResolver();
                cr.insert(MyContentProvider.CONTENT_URI, cv);

                Cursor c = cr.query(MyContentProvider.CONTENT_URI, null, null, null, MyContentProvider.ID_COL + " DESC");

                if (c != null && c.moveToFirst()) {

                    int id = c.getInt(c.getColumnIndexOrThrow(MyContentProvider.ID_COL));
                    int nationalNumResult = c.getInt(c.getColumnIndexOrThrow(MyContentProvider.NATIONAL_NUM_COL));
                    String nameResult = c.getString(c.getColumnIndexOrThrow(MyContentProvider.NAME_COL));
                    String speciesResult = c.getString(c.getColumnIndexOrThrow(MyContentProvider.SPECIES_COL));
                    String genderResult = c.getString(c.getColumnIndexOrThrow(MyContentProvider.GENDER_COL));
                    float heightResult = c.getFloat(c.getColumnIndexOrThrow(MyContentProvider.HEIGHT_COL));
                    float weightResult = c.getFloat(c.getColumnIndexOrThrow(MyContentProvider.WEIGHT_COL));
                    int levelResult = c.getInt(c.getColumnIndexOrThrow(MyContentProvider.LEVEL_COL));
                    int hpResult = c.getInt(c.getColumnIndexOrThrow(MyContentProvider.HP_COL));
                    int attackResult = c.getInt(c.getColumnIndexOrThrow(MyContentProvider.ATTACK_COL));
                    int defenseResult = c.getInt(c.getColumnIndexOrThrow(MyContentProvider.DEFENSE_COL));

                    Log.i("PROVIDER_TEST", "--- Last Inserted Pokemon ---");
                    Log.d("PROVIDER_TEST", "ID: " + id);
                    Log.d("PROVIDER_TEST", "NationalNum: " + nationalNumResult);
                    Log.d("PROVIDER_TEST", "Name: " + nameResult);
                    Log.d("PROVIDER_TEST", "Species: " + speciesResult);
                    Log.d("PROVIDER_TEST", "Gender: " + genderResult);
                    Log.d("PROVIDER_TEST", "Height: " + heightResult);
                    Log.d("PROVIDER_TEST", "Weight: " + weightResult);
                    Log.d("PROVIDER_TEST", "Level: " + levelResult);
                    Log.d("PROVIDER_TEST", "HP: " + hpResult);
                    Log.d("PROVIDER_TEST", "Attack: " + attackResult);
                    Log.d("PROVIDER_TEST", "Defense: " + defenseResult);
                    Log.d("PROVIDER_TEST", "-----------------------------");
                    c.close();
                }

               // cr.update(MyContentProvider.CONTENT_URI,cv,null,null);

                Toast.makeText(v.getContext(), "Pokemon Added to PokeDex", Toast.LENGTH_SHORT).show();
            }

        }
    };


}