package admybrand.rohanthakur7055.com.videoeditor;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialcamera.MaterialCamera;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    protected String dstMediaPath = null;
    protected int videoWidthOut = 640;
    protected int videoHeightOut = 480;
    protected final String videoMimeType = "video/avc";
    protected int videoBitRateInKBytes = 6000;
    protected final int videoFrameRate = 30;
    protected final int videoIFrameInterval = 1;
    protected final String audioMimeType = "audio/mp4a-latm";
    protected final int audioBitRate = 44100;//96 * 1024;
    private boolean isStopped = false;
    ProgressBar progressBar;
    TextView message;
    Button compress;
    int ACTION;
    int DURATION=60;
    Button capture;
    private final static int CAMERA_RQ = 6969;
    File saveFolder = new File(Environment.getExternalStorageDirectory(), "VideoEditingApp/Videos");
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>=23) {

            String[] PERMISSIONS = { Manifest.permission.READ_EXTERNAL_STORAGE,  Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if(!hasPermissions(this, PERMISSIONS)){
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
            }
        }
        capture = (Button)findViewById(R.id.capture);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialCamera(MainActivity.this)
                        .primaryColorAttr(R.attr.md_divider_color)
                        .saveDir(saveFolder)
                        .showPortraitWarning(false)
                        .iconRecord(R.drawable.mcam_action_capture)
                        .iconStop(R.drawable.mcam_action_stop)
                        .iconFrontCamera(R.drawable.mcam_camera_front)
                        .iconRearCamera(R.drawable.mcam_camera_rear)
                        .iconPlay(R.drawable.evp_action_play)
                        .iconPause(R.drawable.evp_action_pause)
                        .iconRestart(R.drawable.evp_action_restart)
                        .qualityProfile(MaterialCamera.QUALITY_480P)
                        .videoEncodingBitRate(1714000)
                        .audioEncodingBitRate(95000)
                        .videoPreferredAspect(4f/3f)
                        .countdownMinutes(0.5f)
                        .start(CAMERA_RQ);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_RQ && resultCode == RESULT_OK) {
            Intent intent = new Intent(MainActivity.this, EditVideoActivity.class);
            intent.putExtra("path", data.getDataString().substring(7,data.getDataString().length()));
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(data.getDataString().substring(7,data.getDataString().length()));
            String duration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            int intduration = (Integer.parseInt(duration)) / 1000;
            intent.putExtra("duration", "" + intduration);
            startActivity(intent);
            //Log.e("File", "" + data.getStringExtra(CameraConfiguration.Arguments.FILE_PATH));
            //Toast.makeText(this, data.getDataString().substring(7,data.getDataString().length()), Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, saveFolder.getPath(), Toast.LENGTH_SHORT).show();

        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                if (grantResults.length == 2
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    finish();
                    startActivity(i);
                } else {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
                    } else {
                        builder = new AlertDialog.Builder(MainActivity.this);
                    }
                    builder.setTitle("Please grant all the permissions")
                            .setMessage("All these permissions are required for app to work seamlessly.\nChikoop will never steal any kind of your personal data.")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                                    finish();
                                    startActivity(i);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finishAffinity();
                                }
                            })
                            .setIcon(R.mipmap.ic_launcher)
                            .show();
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
        }
    }
}
