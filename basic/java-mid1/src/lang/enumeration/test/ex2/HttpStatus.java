package lang.enumeration.test.ex2;

public enum HttpStatus {
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;
    private final String message;
    private HttpStatus(int status, String message) {
        this.code = status;
        this.message = message;
    }

    public static HttpStatus findByCode(int code) {
        for(HttpStatus status : values()) {
            if(status.getCode() == code) {
                return status;
            }
        }
        return null;
    }

    public boolean isSuccess() {
        return code >= 200 && code < 300;
    }

    // GETTER
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
