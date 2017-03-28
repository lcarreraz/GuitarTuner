package applab.milmedios.guitartuner;

import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class TunerActivity extends AppCompatActivity {

    private int[] tracks = { R.raw.note_e2, R.raw.note_a2, R.raw.note_d3,
            R.raw.note_g3, R.raw.note_b3, R.raw.note_e4 };

    Button[] lines_string, btns_string;

    int mCompleted = 0;


    protected TextView textCounter1;
    Boolean repeat;


    String  tuning;


    private MediaPlayer mPlayer = null;


    MediaPlayer mp;

    String[] presidents, audio_notes, audio_notes_string;
    Button btnRepeat, btnPlayAll;
    String[] notas;
    int count;

    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resources = getResources();

        textCounter1 = (TextView) findViewById(R.id.counte1);

        repeat = false;

        mp = null;
        count = 0;

        audio_notes_string = getResources().getStringArray(R.array.audio_notes);



        // audio_notes[Integer.parseInt(button.getTag().toString())]

        presidents = getResources().getStringArray(R.array.notas);

        btns_string = new Button[6];
        btns_string[0] = (Button) findViewById(R.id.btn_string_1);
        btns_string[1] = (Button) findViewById(R.id.btn_string_2);
        btns_string[2] = (Button) findViewById(R.id.btn_string_3);
        btns_string[3] = (Button) findViewById(R.id.btn_string_4);
        btns_string[4] = (Button) findViewById(R.id.btn_string_5);
        btns_string[5] = (Button) findViewById(R.id.btn_string_6);

        lines_string = new Button[6];
        lines_string[0] = (Button) findViewById(R.id.string_1);
        lines_string[1] = (Button) findViewById(R.id.string_2);
        lines_string[2] = (Button) findViewById(R.id.string_3);
        lines_string[3] = (Button) findViewById(R.id.string_4);
        lines_string[4] = (Button) findViewById(R.id.string_5);
        lines_string[5] = (Button) findViewById(R.id.string_6);

        Spinner sp = (Spinner) findViewById(R.id.spinner1);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {

                setSelectedNote(6);
                int index = parentView.getSelectedItemPosition();
                tuning = parentView.getSelectedItem().toString().split("-")[0]
                        + " tuning";
                textCounter1.setText(tuning);



                notas = presidents[index].split("-");
                audio_notes = audio_notes_string[index].split("-");

                btns_string[0].setText(notas[0]);
                btns_string[1].setText(notas[1]);
                btns_string[2].setText(notas[2]);
                btns_string[3].setText(notas[3]);
                btns_string[4].setText(notas[4]);
                btns_string[5].setText(notas[5]);

                tracks[0] = resources.getIdentifier(audio_notes[0], "raw", getPackageName());
                tracks[1] = resources.getIdentifier(audio_notes[1], "raw", getPackageName());
                tracks[2] = resources.getIdentifier(audio_notes[2], "raw", getPackageName());
                tracks[3] = resources.getIdentifier(audio_notes[3], "raw", getPackageName());
                tracks[4] = resources.getIdentifier(audio_notes[4], "raw", getPackageName());
                tracks[5] = resources.getIdentifier(audio_notes[5], "raw", getPackageName());


            }

            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        btnRepeat = (Button) findViewById(R.id.btnRepeat);
        btnPlayAll = (Button) findViewById(R.id.btnPlayAll);




    }


    public void Repeat(View v) {
        repeat = !repeat;
        if (repeat) {
            btnRepeat.setText("Stop repeat");
        } else {
            btnRepeat.setText("Repeat");
            if (mPlayer != null) {
                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                    mPlayer.release();
                    mPlayer = null;
                    textCounter1.setText(tuning);
                    setSelectedNote(6);

                }
            }
        }
    }

    public void Play(Resources resources, Button button) {

        mPlayer = MediaPlayer.create(this, resources.getIdentifier(
                audio_notes[Integer.parseInt(button.getTag().toString())],
                "raw", getPackageName()));

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {
                textCounter1.setText(tuning);
                setSelectedNote(6);
                mPlayer.release();
                mPlayer = null;
            }
        });
        mPlayer.setLooping(repeat);
        mPlayer.start();
        textCounter1.setText("Play " + notas[Integer.parseInt(button.getTag().toString())]);

    }

    public void setSelectedNote(Integer a) {

        btns_string[0].setBackgroundColor(Color.parseColor("#eeA0522D"));
        btns_string[1].setBackgroundColor(Color.parseColor("#eeA0522D"));
        btns_string[2].setBackgroundColor(Color.parseColor("#eeA0522D"));
        btns_string[3].setBackgroundColor(Color.parseColor("#eeA0522D"));
        btns_string[4].setBackgroundColor(Color.parseColor("#eeA0522D"));
        btns_string[5].setBackgroundColor(Color.parseColor("#eeA0522D"));

        lines_string[0].setBackgroundColor(getResources().getColor(
                R.color.string_1));
        lines_string[1].setBackgroundColor(getResources().getColor(
                R.color.string_2));
        lines_string[2].setBackgroundColor(getResources().getColor(
                R.color.string_3));
        lines_string[3].setBackgroundColor(getResources().getColor(
                R.color.string_4));
        lines_string[4].setBackgroundColor(getResources().getColor(
                R.color.string_5));
        lines_string[5].setBackgroundColor(getResources().getColor(
                R.color.string_6));

        if (a < 6) {
            btns_string[a].setBackgroundColor(Color.parseColor("#A52A2A"));
            lines_string[a].setBackgroundColor(Color.parseColor("#A52A2A"));
        }

    }

    public void Play_string(View v) {
        Button button = (Button) v;

        Integer a = Integer.parseInt(button.getTag().toString());

        setSelectedNote(a);

        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
                textCounter1.setText(tuning);
                Play(resources, button);

            }
        } else {

            Play(resources, button);

        }

    }

    public void Play_all(View v) {

        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
                mp.release();
                mp = null;
                textCounter1.setText(tuning);
                mCompleted = 0;
                btnPlayAll.setText("Play all");
                setSelectedNote(6);

            }
        } else {
            btnPlayAll.setText("Stop");
            textCounter1.setText("Play " + notas[0]);
            setSelectedNote(0);
            mp = MediaPlayer.create(this, tracks[0]);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mCompleted++;

                    mp.reset();
                    if (mCompleted < tracks.length) {

                        try {
                            textCounter1.setText("Play " + notas[mCompleted]);
                            setSelectedNote(mCompleted);
                            AssetFileDescriptor afd = getResources()
                                    .openRawResourceFd(tracks[mCompleted]);
                            if (afd != null) {
                                mp.setDataSource(afd.getFileDescriptor(),
                                        afd.getStartOffset(), afd.getLength());
                                afd.close();
                                mp.prepare();
                                mp.start();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    } else if (mCompleted >= tracks.length) {
                        mCompleted = 0;
                        try {
                            textCounter1.setText("Play " + notas[mCompleted]);
                            setSelectedNote(mCompleted);
                            AssetFileDescriptor afd = getResources()
                                    .openRawResourceFd(tracks[mCompleted]);
                            if (afd != null) {
                                mp.setDataSource(afd.getFileDescriptor(),
                                        afd.getStartOffset(), afd.getLength());
                                afd.close();
                                mp.prepare();
                                mp.start();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        mCompleted = 0;
                        mp.release();
                        mp = null;
                    }

                }
            });

            mp.start();

        }
    }
}
