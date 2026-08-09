package oop1;

public class MusicPlayer {
    int volume;
    boolean isOn = false;

    void on() {
        isOn = true;
        System.out.println("전원 on");
    }

    void off() {
        isOn = false;
        System.out.println("전원 off");
    }

    void volumeUp() {
        volume++;
        System.out.println("볼륨 키움");
    }

    void volumeDown() {
        volume--;
        System.out.println("볼륨 내림");
    }

    void showStatus() {
        System.out.println("음악 플레이어 상태 확인");
        if(isOn){
            System.out.println("음악 플레이어 ON, 볼륨: " + volume);
        }
        else{
            System.out.println("음악 플레이어 OFF");
        }
    }
}
