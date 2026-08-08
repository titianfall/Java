package class1;

public class MovieReviewMain {

    public static void main(String[] args) {
        // 영화 리뷰 정보 선언
        MovieReview spiderman = new MovieReview();
        spiderman.title = "Spiderman: Brand New Day";
        spiderman.review = "amazing spiderman!!";

        MovieReview aboutTime = new MovieReview();
        aboutTime.title = "about time";
        aboutTime.review = "인생 시간 영화";

//        System.out.println("영화 제목: " + spiderman.title + "영화 리뷰: " +  spiderman.review);
//        System.out.println("영화 제목: " + aboutTime.title + "영화 리뷰: " +  aboutTime.review);
        MovieReview[] movieReviews = new MovieReview[]{spiderman, aboutTime};

        for (MovieReview movieReview : movieReviews) {
            System.out.println("영화 제목: " + movieReview.title + " 영화 리뷰: " + movieReview.review);
        }
    }
}
