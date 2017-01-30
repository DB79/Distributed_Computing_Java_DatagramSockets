@echo off
REM Switch to Server folder and start the server

cd FileUploadDownload\src\fileuploaddownload\Server
start java Server

REM Start
cd FileUploadDownload\src\fileuploaddownload\Client
java Client