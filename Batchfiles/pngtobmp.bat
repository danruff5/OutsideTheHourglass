@echo off
CHOICE /C YC /M "Are you sure you want to run on %cd%, this may damage files Press Y for Yes, or C for Cancel."

IF ERRORLEVEL ==2 GOTO END
@echo on
:PINK_BG
forfiles /s /m *.png /c "magick convert @file -background "rgb(255,0,255)" -flatten @fname.png""
:OPTIMIZE
forfiles /s /m *.png /c ""%~dp0\optipng-0.7.6-win32\optipng.exe" -o7 @fname.png"
:END
