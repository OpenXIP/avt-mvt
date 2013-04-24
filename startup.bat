SETLOCAL
cd ..\jdk\jre\bin
set JAVA_JRE_PATH=%CD%
cd ..\..\..\R
set R_HOME=%CD%
cd bin
set PATH=%CD%;%PATH%
cd ..
cd ..\xip-lib
set XIP_LIB_PATH=%CD%
set PATH=%XIP_LIB_PATH%;%JAVA_JRE_PATH%;%PATH%
cd ..\Data\AD-DICOM
set AD_DICOM_STORE=%CD%
cd ..\..\MVT
..\jdk\bin\java -jar build/lib/MVT.jar %1 %2 %3 %4
pause