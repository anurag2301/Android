package com.anurag2301.quiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvCategory;
    TextView tvScore;
    TextView tvQuestion;
    RadioGroup rgOptions;
    RadioButton[] rb;
    Button bPrevious;
    Button bNext;
    Button bSubmit;
    Questions allQuestions;
    boolean isFirstRun = true;

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mToggle;
    NavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCategory = (TextView) findViewById(R.id.tvCategory);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        rgOptions = (RadioGroup) findViewById(R.id.rgQuestions);
        bPrevious = (Button) findViewById(R.id.bPrev);
        bNext = (Button) findViewById(R.id.bNext);
        bSubmit = (Button) findViewById(R.id.bSubmit);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        rb = new RadioButton[4];
        rb[0] = (RadioButton) findViewById(R.id.rb1);
        rb[1] = (RadioButton) findViewById(R.id.rb2);
        rb[2] = (RadioButton) findViewById(R.id.rb3);
        rb[3] = (RadioButton) findViewById(R.id.rb4);
        reset();

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSubmit();
            }
        });

        rgOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                rgChecked();
            }
        });

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickNext();
            }
        });

        bPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPrevious();
            }
        });

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.menuCat1:
                        allQuestions.onCategory = 0;
                        dispQuestion();
                        return true;
                    case R.id.menuCat2:
                        allQuestions.onCategory = 1;
                        dispQuestion();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.menuCat1:
                allQuestions.onCategory = 0;
                dispQuestion();
                return true;
            case R.id.menuCat2:
                allQuestions.onCategory = 1;
                dispQuestion();
                return true;
            case R.id.action_reset:
                reset();
        }


        return super.onOptionsItemSelected(item);
    }

    public boolean checkTotal() {
        if(allQuestions.totalQuestions == allQuestions.qAnswered) {
            return true;
        }
        return false;
    }

    public void rgChecked() {
        bSubmit.setEnabled(true);
    }

    public void clickSubmit() {
        int index = rgOptions.indexOfChild(findViewById(rgOptions.getCheckedRadioButtonId()));
        boolean ans = allQuestions.addScore(index);
        Context context = getApplicationContext();
        String text = "";
        if(ans) {
            text = "Correct Answer";
        } else {
            text = "Incorrect Answer";
        }
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        dispQuestion();
    }

    public void clickNext() {
        allQuestions.nextQuestion();
        dispQuestion();
    }

    public void clickPrevious() {
        allQuestions.prevQuestion();
        dispQuestion();
    }

    public void dispDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        String message = "";

        for(int i = 0; i < allQuestions.score.length; i++) {
            message += "Category: " + allQuestions.categoryList[i] + " Score: " + allQuestions.score[i] + "\n";
        }
        builder.setTitle("Quiz Complete")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        reset();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void reset() {
        allQuestions = new Questions();
        allQuestions.init();
        allQuestions.onCategory = 1;
        dispQuestion();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action, menu);
        return true;
    }



    public void dispQuestion() {

        if(checkTotal()) {
            dispDialog();

        }

        int category = allQuestions.onCategory;
        Log.i("Buttons", "Activating");
        bSubmit.setEnabled(false);
        bNext.setEnabled(true);
        bPrevious.setEnabled(true);
        rgOptions.setEnabled(true);
        Log.i("Buttons", "Activated");
        Log.i("Question", "Activating");
        Question q = allQuestions.getQuestionChangeCategory(category);

        Log.i("TextView","Updating UI");
        tvCategory.setText(allQuestions.categoryList[category]);
        tvQuestion.setText("Q" + (allQuestions.onQuestion[category] +1) + ". "+ q.question);
        tvScore.setText(allQuestions.getScore()+"");

        Log.i("RB","Updating rb");
        rgOptions.clearCheck();
        for(int i = 0; i < 4; i++) {
            rb[i].setEnabled(true);
            rb[i].setText(q.options.get(i));
        }

        int onQuestion = allQuestions.onQuestion[category];

        if(allQuestions.recordAns.containsKey(category*100 + onQuestion)) {

            int selected = allQuestions.recordAns.get(category*100 + onQuestion);
            rb[selected].setChecked(true);
            rgOptions.setEnabled(false);
            for(int i = 0; i < 4; i++) {
                rb[i].setEnabled(false);
            }
            bSubmit.setEnabled(false);
        }

        Log.i("OnQuestion", onQuestion + "");
        if(onQuestion == 0) {
            bPrevious.setEnabled(false);
        }

        if(onQuestion >= allQuestions.list.get(allQuestions.categoryList[category]).questionList.size()-1) {
            bNext.setEnabled(false);
        }

    }
}