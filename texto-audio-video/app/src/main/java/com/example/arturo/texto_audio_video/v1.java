package com.example.arturo.texto_audio_video;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.SurfaceHolder.*;

public class v1 extends AppCompatActivity implements Callback{

    private Camera myCamera;
    private SurfaceView mSurfaceView;
    private MediaRecorder mediaRecorder;
    private SurfaceHolder mHolder;
    Button myButton;

    boolean recording;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recording = false;

        setContentView(R.layout.activity_v1);

        //Get Camera for preview
        myCamera = getCameraInstance();
        if (myCamera == null) {

        }

        mSurfaceView = (SurfaceView) findViewById(R.id.s);
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);

        myButton = (Button) findViewById(R.id.mybutton);
        myButton.setOnClickListener(myButtonOnClickListener);
    }

    Button.OnClickListener myButtonOnClickListener
            = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            try {
                if (recording) {
                    // stop recording and release camera
                    mediaRecorder.stop();  // stop the recording
                    releaseMediaRecorder(); // release the MediaRecorder object

                    //Exit after saved
                    //finish();
                    myButton.setText("REC");
                    recording = false;
                } else {

                    //Release Camera before MediaRecorder start
                    releaseCamera();

                    if (!prepareMediaRecorder()) {

                        finish();
                    }

                    mediaRecorder.start();
                    recording = true;
                    myButton.setText("STOP");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    private Camera getCameraInstance() {
        // TODO Auto-generated method stub
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }




    private boolean prepareMediaRecorder() {
        String i2 = getIntent().getStringExtra("ruta");
        myCamera = getCameraInstance();
        mediaRecorder = new MediaRecorder();

        myCamera.unlock();
        mediaRecorder.setCamera(myCamera);

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));


        mediaRecorder.setOutputFile(i2+ "v.mp4");
        //mediaRecorder.setOutputFile("/sdcard/myvideo1.mp4");
        mediaRecorder.setMaxDuration(60000); // Set max duration 60 sec.
        mediaRecorder.setMaxFileSize(50000000); // Set max file size 50M

        mediaRecorder.setPreviewDisplay(mSurfaceView.getHolder().getSurface());

        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            releaseMediaRecorder();
            return false;
        }
        return true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
    }

    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();   // clear recorder configuration
            mediaRecorder.release(); // release the recorder object
            mediaRecorder = new MediaRecorder();
            myCamera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera() {
        if (myCamera != null) {
            myCamera.release();        // release the camera for other applications
            myCamera = null;
        }
    }




    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            myCamera.setPreviewDisplay(holder);
            myCamera.startPreview();
        } catch (IOException e) {
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_v1, menu);
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
}