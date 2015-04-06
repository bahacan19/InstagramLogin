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
Licence
--------------

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
