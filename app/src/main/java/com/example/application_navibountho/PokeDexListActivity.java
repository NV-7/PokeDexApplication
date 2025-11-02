package com.example.application_navibountho;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PokeDexListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_poke_dex_list);
        ContentResolver cr = getContentResolver();
        String[] projection = {
                MyContentProvider.ID_COL + " AS _id",
                MyContentProvider.NATIONAL_NUM_COL,
                MyContentProvider.NAME_COL,
                MyContentProvider.SPECIES_COL,
                MyContentProvider.GENDER_COL,
                MyContentProvider.HEIGHT_COL,
                MyContentProvider.WEIGHT_COL,
                MyContentProvider.LEVEL_COL,
                MyContentProvider.HP_COL,
                MyContentProvider.ATTACK_COL,
                MyContentProvider.DEFENSE_COL
        };

        String sortOrder = MyContentProvider.ID_COL + " DESC";
        Cursor c = cr.query(MyContentProvider.CONTENT_URI, projection, null, null, sortOrder);

        String[] col = {
                MyContentProvider.NATIONAL_NUM_COL,
                MyContentProvider.NAME_COL,
                MyContentProvider.SPECIES_COL,
                MyContentProvider.GENDER_COL,
                MyContentProvider.HEIGHT_COL,
                MyContentProvider.WEIGHT_COL,
                MyContentProvider.LEVEL_COL,
                MyContentProvider.HP_COL,
                MyContentProvider.ATTACK_COL,
                MyContentProvider.DEFENSE_COL,
        };

        int[] toViews = {R.id.tvNationalNumber,
                R.id.tvName,
                R.id.tvSpecies,
                R.id.tvGender,
                R.id.tvHeight,
                R.id.tvWeight,
                R.id.tvLevel,
                R.id.tvHP,
                R.id.tvAttack,
                R.id.tvDefence,
        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row, c, col, toViews, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        ListView listView = findViewById(R.id.pokedex);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(onHold);

    }

    public AdapterView.OnItemLongClickListener onHold = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PokeDexListActivity.this);
            builder.setTitle("Delete Pokemon?");

            builder.setPositiveButton("Yes", (dialog, which) -> {
                delete();
                dialog.dismiss();
            });

            builder.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });
            AlertDialog alert = builder.create();
            alert.show();

            return true;

        }
    };

    public void delete(){
        ContentResolver cr = getContentResolver();
        cr.delete(MyContentProvider.CONTENT_URI, null);
        recreate();
    }
    public AdapterView.OnItemLongClickListener onHold1 = (parent, view, position, id) ->


            true;

}


