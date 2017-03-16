package com.example.brenda.jccexample;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brenda.jccexample.database.AccionesEscritura;
import com.example.brenda.jccexample.database.AccionesLectura;
import com.example.brenda.jccexample.database.MyDB;
import com.example.brenda.jccexample.fragmentos.DummyDisplayFragment;
import com.example.brenda.jccexample.fragmentos.ListaPaisesFragment;
import com.example.brenda.jccexample.networking.ContactoConServidor;
import com.example.brenda.jccexample.parser.PaisParser;
import com.example.brenda.jccexample.proveedores.ProveedorSolicitudes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
// Y pus ya. Compilalo
    // me mandas whats va? claro gracuas!!
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        boolean consumed = false;
        if(item.getItemId() == R.id.main_menu_iniciar_chamba){
            consumed = true;
        }
        return consumed;
    }
*/
    @Override
    public void onResume(){
        super.onResume();
        if(AccionesLectura.obtenerPaises(this).length == 0){
            new ContactoConServidor(1, ProveedorSolicitudes.solicitudSimple(), new ContactoConServidor.AccionContactoConServidor() {
                @Override
                public void accionPositiva(String content) {
                    try{
                        JSONObject json = new JSONObject(content);
                        JSONArray jarr = json.getJSONArray("Paises");
                        for(int i=0; i<jarr.length(); i++) {
                            AccionesEscritura.escribePais(MainActivity.this, PaisParser.parsePais(jarr.getJSONObject(i)));
                        }
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                cambioDeFragment(new ListaPaisesFragment());
                            }
                        });
                    }catch(JSONException e){
                        e.printStackTrace();
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                cambioDeFragment(new DummyDisplayFragment());
                            }
                        });
                    }

                }

                @Override
                public void accionNegativa(String content) {
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            cambioDeFragment(new DummyDisplayFragment());
                        }
                    });
                }
            }).start();
        }else{
            cambioDeFragment(new ListaPaisesFragment());
        }
    }

    private void cambioDeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_main_container, fragment)
                .commit();
    }

    public void cambioDeFragmentConBackStack(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_main_container, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }
}
