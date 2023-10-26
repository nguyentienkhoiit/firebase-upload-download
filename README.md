
# SPRING BOOT UPLOAD AND DOWNLOAD PROCESSES WITH FIREBASE STORAGE

<br>

#### 1.CHANGE BUCKET NAME FROM application.properties with your bucket name.


#### 2.CHANGE JSON FILE WITH YOURS.

<br>
<br>

#### UPLOAD ENDPOINTS
> curl --location 'localhost:8080/api/upload'--form 'file=@"<FILE-PATH>""'

#### DOWNLOAD ENDPOINTS
> curl --location --request POST 'localhost:8080/api/download/{filename}'

