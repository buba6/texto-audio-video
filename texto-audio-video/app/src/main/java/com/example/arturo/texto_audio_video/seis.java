package com.example.arturo.texto_audio_video;

import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class seis extends AppCompatActivity {
    private Button bGrabar,bDetener;
    private static final String LOG_TAG ="Grabadora";
    private MediaRecorder mediaRecorder;

    //private static String t;
    // private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seis);

        bGrabar = (Button) findViewById(R.id.g);
        bDetener = (Button) findViewById(R.id.bDetener);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_seis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void grabar(View view) {

        String i = getIntent().getStringExtra("ruta");
//String file ="m.3gp";

        //String t= Environment.getExternalStorageDirectory().getAbsolutePath()+"m.3gp";
        //String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();

// Not sure if the / is on the path or not
        //File f = new File(t + File.separator + fileName);
        //f.write(...);

        bGrabar.setEnabled(false);
        bDetener.setEnabled(true);
        //bReproducir.setEnabled(false);
        //bDetenerReproduccion.setEnabled(false);
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setOutputFile( i+"audio.3GP");
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();

            // i= Environment.getExternalStorageDirectory().getAbsolutePath() +"audio.3gp";
        } catch (IOException e) {
            Log.e(LOG_TAG, "Fallo la grabacion");
        }
        mediaRecorder.start();
    }
    public void detener(View view) {
        mediaRecorder.stop();
        mediaRecorder.release();
        bGrabar.setEnabled(true);
        bDetener.setEnabled(false);
        //bReproducir.setEnabled(true);


    }
}