@echo off
chcp 65001 > nul
cd /d "%~dp0"

rem 내가 직접 짜는 쪽(practice 패키지)을 실행한다.

if not exist out\production mkdir out\production

echo [1/2] 컴파일 중...
dir /s /b src\*.java > out\sources.txt
javac -encoding UTF-8 -d out\production @out\sources.txt
if errorlevel 1 (
    echo 컴파일 실패
    pause
    exit /b 1
)

echo [2/2] 실행 - practice.Main
java -cp out\production practice.Main
