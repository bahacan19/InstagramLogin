## InstagramLogin
Tiny Instagram Auth Library for Android Apps
## Usage

add dependency to your build.gradle file

<code> compile project(':instagramlogin')</code>

To start the login phase

<code>InstaLogin instaLogin = new InstaLogin(MainActivity.this,
                        YOUR CIENT ID,
                        YOUR CLIENT SECRET,
                        YOUR SUCCESS URL);
                instaLogin.login();
                </code>
                
                
To handle the login data in your <code> onActivityResult </code>



<code>
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
    
     </code>
     
     
     Contributions are welcomed !
## License
 Apache License
