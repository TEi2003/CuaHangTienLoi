package utils;

    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.Date;

    public class DateUtils {
        private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
        private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        static {
            DATE_FORMAT.setLenient(false); // Không cho phép ngày không hợp lệ (VD: 31/04)
            DATE_TIME_FORMAT.setLenient(false);
        }

        public static String formatDate(Date date) {
            if (date == null) {
                return "";
            }
            return DATE_FORMAT.format(date);
        }

        public static String formatDateTime(Date date) {
            if (date == null) {
                return "";
            }
            return DATE_TIME_FORMAT.format(date);
        }

        public static Date parseDate(String dateStr) throws ParseException {
            if (dateStr == null || dateStr.isEmpty()) {
                return null;
            }
            return DATE_FORMAT.parse(dateStr);
        }

        public static Date parseDateTime(String dateTimeStr) throws ParseException {
            if (dateTimeStr == null || dateTimeStr.isEmpty()) {
                return null;
            }
            return DATE_TIME_FORMAT.parse(dateTimeStr);
        }

        public static Date getCurrentDate() {
            return new Date();
        }
    }