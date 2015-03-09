package com.nomad.instagramlogtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nomad.instagramlogin.InstaLogin;
import com.nomad.instagramlogin.Keys;


public class MainActivity extends ActionBarActivity {
    Button login;
    TextView stat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InstaLogin instaLogin = new InstaLogin(MainActivity.this,
                        "c4d946f3dc8a43699aeb7c57b5cbc12d",
                        "6aba840c8c984aadbae55bad66c5eab3",
                        "https://loggedinbaby");
                instaLogin.login();
            }
        });
        stat = (TextView) findViewById(R.id.stat);
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Keys.LOGIN_REQ) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                stat.setText("Fullname : "+bundle.getString(InstaLogin.FULLNAME)+"\n"+
                "UserName : "+bundle.getString(InstaLogin.USERNAME)+"\n"+
                        "id : "+bundle.getString(InstaLogin.ID)+"\n"+
                        "pICTURE : "+bundle.getString(InstaLogin.PROFILE_PIC)+"\n"+
                        "access_token : "+bundle.getString(InstaLogin.ACCESS_TOKEN)+"\n"+
                        "bıo : "+bundle.getString(InstaLogin.BIO)+"\n");
            }
        }
    }

  /*  @Override
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
    }*/
}
