import java.util.*;
//import java.util.Scanner;
//import java.util.Random;
public class Ex12 {
    static Scanner scanner = new Scanner(System.in);

    public static String scanPlayer(){
        
        System.out.print("게임에 참여할 선수들 이름>>");
        String player = scanner.nextLine(); // ex) 황기태 하여린 윤단비 입력
        player = player.trim(); //가비지 생성 및 앞뒤 공백문자 제거
        return player; //참가자 문자열 리턴
    }
    
    public static String[] splitPlayer(String player){
        String [] playerArray = player.split(" "); // 선수 목록 공백 기준 분할
        int [] selectNumber = {}; //선수가 입력한 숫자 저장할 정수형 배열
        int playerIndex = 0; //선수 인덱스
        //bool winnerFlag = false; 
        //정수 선택 함수 (아마도 리턴할때 selectNumber[i]와 playerCount[i] 를 문자열에 합치고 split해야할듯)
        //String [] numberSelect
        for(String s : playerArray){ //선수 수만큼 반복
            System.out.print("[" + s + "] 정수 선택(1~10)>>");
            int n = Integer.parseInt(scanner.nextLine()); //정수입력후 버퍼가 남는 상황을 방지하기 위해
            selectNumber[playerIndex++] = n; //선택한 숫자 저장 배열
            //playerCount[playerIndex++] = 0; //맞춘 숫자 개수 저장 배열 초기화
        }
        //이건 여기는 좀 부적합한듯
        System.out.print("Enter키 입력>>");
        scanner.nextLine(); // 입력을 통해 프로그램을 붙잡아놓음

        return selectNumber;
    }
    public static int[] playerCountInit(int [] playerCount){
        for(int i = 0; i < playerCount.length; i ++){
            
        }
    }
    public static int[] playerGamble(String [] playerArray, int [] selectNumber){

        for(int i = 0; i < 15 ; i++){
            int rand = (int)(Math.random() * 10 + 1);
            System.out.print(rand + " "); // 1~ 10사이 랜덤 정수 출력
            for(int j = 0; j < playerArray.length; j++){
                if(selectNumber[j] == rand){
                    playerCount[j]+=1;
                }
            }
        }
        return playerCount;
    }
    public static void run(){   
        String player = scanPlayer();
        String [] playerArray = splitPlayer(player);
        int [] playerCount = playerCountInit(playerCount);
        int [] playerCount = printGamble(playerArray); //인수.. 매개변수.. 
    }
    public static void main(String[] args) {
        run(); //프로그램 시작
        
        

        
        System.out.println();
        //함수로 작성하는게 올바를듯함
        //패자 선택부분 및 제거 또는 패자부활전으로 옮겨가는 부분 구현
        String [] loserPlayer = {playerArray[0],};
        int lowerScore = playerCount[0];
        int loserIndex = 0;
        for(int i = 1; i < playerIndex; i++){
            if(lowerScore > playerCount[i]){
                loserPlayer[loserIndex] = playerArray[i];
            }
            else if(lowerScore == playerCount[i]){
                loserPlayer[++loserIndex] = playerArray[i]; 
                //패자가 n명이 되는 경우를 구현 >> 모두가 점수가 동일할 경우 제거하지 않고 무시 및 반복
            }
        }

        //패자부활전 구현 parameter는 무엇을 쓸것인가? String [] loserPlayer 만 받아오면 된다.
        //public String loserBacket(String [] loserPlayer){
        String [] losers = loserPlayer;
        //String numberSelect(String [] losers); 호출
        /*for(String s : playerArray){ //선수 수만큼 반복
            System.out.print("[" + s + "] 정수 선택(1~10)>>");
            int n = Integer.parseInt(scanner.nextLine()); //정수입력후 버퍼가 남는 상황을 방지하기 위해
            selectNumber[playerIndex++] = n; //선택한 숫자 저장 배열
            playerCount[playerIndex++] = 0; //맞춘 숫자 개수 저장 배열 초기화
        }*/
        //}

                
    }
}
