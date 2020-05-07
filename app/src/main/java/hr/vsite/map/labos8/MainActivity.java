package hr.vsite.map.labos8;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    final String FILE_NAME = "spremi.txt";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onSpremi(View v){
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        //stare vrijednosti, čitanje
        String staraZivotinja = preferences.getString("zivotinja", "Nema životinje");
        String staraPtica = preferences.getString("ptica", "Nema ptice");
        //nova vrijednost, zapis u SP iz EditText-a
        String novaZivotinja = ((EditText)findViewById(R.id.et_zivotinja)).getText().toString();
        String novaPtica = ((EditText)findViewById(R.id.et_ptica)).getText().toString();

        SharedPreferences.Editor editor = preferences.edit ();

        editor.putString("zivotinja", novaZivotinja);
        editor.putString("ptica", novaPtica);

        editor.commit();



        //Toast poruka X u Y
        Toast.makeText(this,
                "Promijenili ste omiljenu životinju iz " + staraZivotinja + " u " + novaZivotinja + ".\n"
                        + "Promijenili ste omiljenu pticu iz " + staraPtica + " u " + novaPtica + ".",
                Toast.LENGTH_LONG).show();

        EditText etZivotinja = findViewById(R.id.et_zivotinja);
        EditText etPtica = findViewById(R.id.et_ptica);

        String unesenaZivotinja = novaZivotinja.toString();
        String unesenaPtica = novaPtica.toString();
        String uneseno = unesenaZivotinja + ", " + unesenaPtica + ";\n";


        //Upiši sada u file
        //Otvori stream za pisanje
        try (OutputStream fos = openFileOutput(FILE_NAME, Context.MODE_APPEND)){
            fos.write(uneseno.getBytes()); //evo ga piši/
            // Ako smo ovdje - upisano
            etZivotinja.setText("");
            etPtica.setText("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onProcitaj(View v){
        // Čitanje iz datoteke i ispis

        // Otvaranje datoteke
        StringBuilder builder = new StringBuilder();
        try (InputStream fis = openFileInput(FILE_NAME)){

            int i;
            while((i = fis.read()) !=-1){
                char c = (char)i;
                builder.append(c);
            }
        } catch (FileNotFoundException e){
            Toast.makeText(this,"Upišite nešto prije čitanja.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        //Dohvat textview-a
        TextView tv = findViewById(R.id.tv_zapisano);
        //Ispis
        tv.setText(builder.toString());
    }
}


