@echo off
REM Switch to Server folder and start the server

cd FileUploadDownload\src\fileuploaddownload\Server
start java Server

REM run jar file
cd ../../../../
java -jar "FileUploadDownload\dist\FileUploadDownload.jar"