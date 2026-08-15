@echo off
chcp 65001 > nul
cd /d "%~dp0"

rem images / music / data 에 없는 자리표시 리소스만 새로 만들어 준다.
rem 이미 있는 파일은 건드리지 않으므로 진짜 리소스로 바꿔 넣어도 안전하다.

if not exist out\tools mkdir out\tools

javac -encoding UTF-8 -d out\tools tools\GenerateAssets.java
if errorlevel 1 (
    echo 컴파일 실패
    pause
    exit /b 1
)

java -cp out\tools GenerateAssets
pause
