package oop1;

public class MusicPlayerMain1 {
    public static void main(String[] args) {
        int volume = 0;
        boolean isOn = false;

        // 음악 플레이어 켜기
        isOn = true;
        System.out.println("음악 플레이어를 시작합니다");

        // 볼륨 증가
        volume++;
        System.out.println(volume);

        // 볼륨 증가
        volume++;
        System.out.println(volume);

        // 볼륨 감소
        volume--;
        System.out.println(volume);

        // 음악 플레이어 상태
        if(isOn){
            System.out.println("음악 플레이어 ON, volume: " + volume);
        }
        else {
            System.out.println("음악 플레이어 OFF");
        }

        isOn = false;
        if(isOn){
            System.out.println("음악 플레이어 ON, volume: " + volume);
        }
        else {
            System.out.println("음악 플레이어 OFF");
        }
    }
}
