package oop1;

public class MusicPlayerMain2 {
    public static void main(String[] args) {
        MusicPlayerData data = new MusicPlayerData();

        // 음악 플레이어 켜기
        on(data);
        System.out.println("음악 플레이어를 시작합니다");

        // 볼륨 증가
        volumeUp(data);
        System.out.println(data.volume);

        // 볼륨 증가
        volumeUp(data);
        System.out.println(data.volume);

        // 볼륨 감소
        volumeDown(data);
        System.out.println(data.volume);

        // 음악 플레이어 상태
        showStatus(data);

        off(data);
        showStatus(data);
    }

    private static void off(MusicPlayerData musicPlayerData) {
        musicPlayerData.isOn = false;
    }

    private static void showStatus(MusicPlayerData musicPlayerData) {
        if(musicPlayerData.isOn){
            System.out.println("음악 플레이어 ON, volume: " + musicPlayerData.volume);
        }
        else {
            System.out.println("음악 플레이어 OFF");
        }
    }

    private static void volumeDown(MusicPlayerData musicPlayerData) {
        musicPlayerData.volume--;
    }

    private static MusicPlayerData volumeUp(MusicPlayerData musicPlayerData) {
        return musicPlayerData;
    }

    private static void on(MusicPlayerData musicPlayerData) {
        musicPlayerData.isOn = true;
    }
}
