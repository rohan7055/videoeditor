package admybrand.rohanthakur7055.com.videoeditor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.squareup.picasso.Picasso;

import java.io.File;

import VideoHandle.EpDraw;
import VideoHandle.EpEditor;
import VideoHandle.EpVideo;
import VideoHandle.OnEditorListener;

public class EditVideoActivity extends AppCompatActivity {

    EasyVideoPlayer easyVideoPlayer;
    String path,durat,imagePath;
    int duration,duration1;
    public static final String EXTRA_VIDEO_PATH="EXTRA_VIDEO_PATH";
    public static final String EXTRA_VIDEO_DURATION="EXTRA_VIDEO_DURATION";
    private static final int CHOOSE_FILE = 10;
    Button btn_trim,btn_addlogo,btn_changeaudio,btn_proceed,btn_cancel;
    int leftmargin,topmargin;

    private ImageView img;
    private ViewGroup rootLayout;
    private int _xDelta;
    private int _yDelta;
    LinearLayout llprocess;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_video);
        easyVideoPlayer = (EasyVideoPlayer) findViewById(R.id.player);
        btn_changeaudio=(Button)findViewById(R.id.btn_changeaudio);
        btn_trim=(Button)findViewById(R.id.btn_trim);
        btn_addlogo=(Button)findViewById(R.id.btn_addcoverimage);
        btn_proceed=(Button)findViewById(R.id.btn_proceed);
        btn_cancel=(Button)findViewById(R.id.btn_cancel);
        rootLayout = (ViewGroup) findViewById(R.id.view_root);
        img = (ImageView) rootLayout.findViewById(R.id.imageView);
        llprocess=(LinearLayout)findViewById(R.id.llprocessvideo);

        llprocess.setVisibility(View.GONE);

        //progress dialog
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(100);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setTitle("Processing");


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200, 200);
        img.setLayoutParams(layoutParams);
        img.setOnTouchListener(new ChoiceTouchListener());
        img.setVisibility(View.GONE);

        path = getIntent().getStringExtra("path");
        durat = getIntent().getStringExtra("duration");
        duration = Integer.parseInt(durat);
        duration1 = Integer.parseInt(durat);;

        easyVideoPlayer.setSource(Uri.parse(path));
        easyVideoPlayer.setAutoPlay(true);
        easyVideoPlayer.setLoop(true);
        easyVideoPlayer.setCallback(new EasyVideoCallback() {
            @Override
            public void onStarted(EasyVideoPlayer player) {


            }

            @Override
            public void onPaused(EasyVideoPlayer player) {

            }

            @Override
            public void onPreparing(EasyVideoPlayer player) {

            }

            @Override
            public void onPrepared(EasyVideoPlayer player) {

            }

            @Override
            public void onBuffering(int percent) {

            }

            @Override
            public void onError(EasyVideoPlayer player, Exception e) {

            }

            @Override
            public void onCompletion(EasyVideoPlayer player) {
                easyVideoPlayer.hideControls();

            }

            @Override
            public void onRetry(EasyVideoPlayer player, Uri source) {

            }

            @Override
            public void onSubmit(EasyVideoPlayer player, Uri source) {

            }
        });

        btn_trim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyVideoPlayer.stop();
                Intent i=new Intent(EditVideoActivity.this,TrimmerActivity.class);
                i.putExtra(EditVideoActivity.EXTRA_VIDEO_PATH,path);
                i.putExtra(EditVideoActivity.EXTRA_VIDEO_DURATION,durat);
                startActivity(i);
                overridePendingTransition(0,0);
            }
        });

        btn_addlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i=new Intent(EditVideoActivity.this,DragandDrop.class);
                startActivity(i);
                overridePendingTransition(0,0);*/
                easyVideoPlayer.stop();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, CHOOSE_FILE);


            }
        });

        btn_changeaudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyVideoPlayer.stop();
                addbackmusic();


            }
        });

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyVideoPlayer.stop();
                execVideo();


            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                easyVideoPlayer.stop();
                img.setVisibility(View.GONE);
                llprocess.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_FILE:
                if (resultCode == RESULT_OK) {
                    easyVideoPlayer.start();
                    imagePath = UriUtils.getPath(EditVideoActivity.this, data.getData());
                    Picasso.get()
                            .load(new File(imagePath))
                            .placeholder(R.mipmap.ic_launcher)
                            .error(R.mipmap.ic_launcher)
                            .into(img);
                    img.setVisibility(View.VISIBLE);
                    llprocess.setVisibility(View.VISIBLE);
                    //tv_file.setText(videoUrl);
                    break;
                }
        }
    }

    private final class ChoiceTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent event) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            rootLayout.invalidate();
            return true;
        }
    }

    private void execVideo(){
        if(path != null && !"".equals(path)){
            EpVideo epVideo = new EpVideo(path);
            EpDraw epDraw = new EpDraw(imagePath, Math.round(img.getX()-5.0f),Math.round(img.getY()+5.0f), img.getLayoutParams().width , img.getLayoutParams().height , false );
             epVideo.addDraw(epDraw);
            mProgressDialog.setProgress(0);
            mProgressDialog.show();
            final String outPath = MyApplication.getSavePath() + "output.mp4";
            EpEditor.exec(epVideo, new EpEditor.OutputOption(outPath), new OnEditorListener() {
                @Override
                public void onSuccess() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EditVideoActivity.this, "Edit completed:"+outPath, Toast.LENGTH_SHORT).show();

                        }
                    });

                    mProgressDialog.dismiss();


                    Intent v = new Intent(EditVideoActivity.this,FinalPreviewActivity.class);
                    v.putExtra("path",outPath);
                    v.putExtra("duration",durat);

                    //   v.setDataAndType(Uri.parse(outPath), "video/mp4");
                    startActivity(v);
                    overridePendingTransition(0,0);
                }

                @Override
                public void onFailure() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EditVideoActivity.this, "Edit failed\n", Toast.LENGTH_SHORT).show();
                        }
                    });
                    mProgressDialog.dismiss();
                }

                @Override
                public void onProgress(float v) {
                    mProgressDialog.setProgress((int) (v * 100));
                }
            });
        }else{
            Toast.makeText(this, "Choose a video", Toast.LENGTH_SHORT).show();
        }
    }

    private void addbackmusic(){
        final String outPath = MyApplication.getSavePath()+"musicout.mp4";
        mProgressDialog.setProgress(0);
        mProgressDialog.show();
        EpEditor.music(path, MyApplication.getSavePath()+ "samplesound.mp3", outPath, 1.0f, 1.0f, new OnEditorListener() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(EditVideoActivity.this, "Edit completed\n:"+outPath, Toast.LENGTH_SHORT).show();
                    }
                });
                mProgressDialog.dismiss();

                Intent v = new Intent(EditVideoActivity.this,FinalPreviewActivity.class);
                v.putExtra("path",outPath);
                v.putExtra("duration",durat);
                //   v.setDataAndType(Uri.parse(outPath), "video/mp4");
                startActivity(v);
                overridePendingTransition(0,0);
            }

            @Override
            public void onFailure() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(EditVideoActivity.this, "Edit failed\n", Toast.LENGTH_SHORT).show();
                    }
                });

                mProgressDialog.dismiss();
            }

            @Override
            public void onProgress(final float v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressDialog.setProgress((int) (v * 100));

                    }
                });

            }
        });
    }


}
