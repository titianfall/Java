import java.util.*;
class Location {
	private String city;
	private double longitude; // 경도
	private double latitude; // 위도
	
	public Location(String city, double longitude, double latitude) {
		this.city = city;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public void setCity(String city) {this.city = city;}
	public String getCity() {return city;}
	public void setLogitude(double longitude) {this.longitude = longitude;}
	public double getLongitude() {return longitude;}
	public void setLatitude(double latitude) {this.latitude = latitude;}
	public double getLatitude() {return latitude;}
}
public class P7 {
    private Scanner scanner = new Scanner(System.in);
	private HashMap<String, Location> locMap = new HashMap<String, Location>();
	
	private void read() {
		System.out.println("도시,경도,위도를 입력하세요.");
		for (int i=0; i<4; i++) {
			System.out.print(">> ");
			String text = scanner.nextLine();
			StringTokenizer st = new StringTokenizer(text, ",");
			String city = st.nextToken().trim();
			double logitude = Double.parseDouble(st.nextToken().trim());
			double latitude = Double.parseDouble(st.nextToken().trim());
			
			Location loc = new Location(city, logitude, latitude);
			locMap.put(city, loc); //해시맵에 저장
		}			
	}
    	
	private void printAll() {
		Set<String> key = locMap.keySet();
		Iterator<String> it = key.iterator();
		System.out.println("---------------------------");
		while (it.hasNext()) {
			String city = it.next(); 
			Location loc = locMap.get(city); 

			System.out.print(loc.getCity() + "\t");
			System.out.print(loc.getLongitude() + "\t");
			System.out.println(loc.getLatitude());
		}
		System.out.println("---------------------------");		
	}
    private void processQuery() {
		while(true) {
			System.out.print("도시 이름 >> ");
			String city = scanner.nextLine(); 
			if(city.equals("그만"))
				return; // 종료
			
			Location loc = locMap.get(city);
			if(loc == null) {
				System.out.println(city + "는 없습니다.");
			}
			else {
				System.out.print(loc.getCity()  + "\t");
				System.out.print(loc.getLongitude() + "\t");
				System.out.println(loc.getLatitude());
			}
		}
	}
    public void run(){
        read();
        printAll();
        processQuery();
    }
    public static void main(String[] args) {
        P7 locm = new P7();
        locm.run();
    }
}
