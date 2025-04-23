package utils;

    import java.text.DecimalFormat;
    import java.text.NumberFormat;
    import java.text.ParseException;
    import java.util.Locale;

    public class NumberUtils {
        private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        private static final DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

        public static String formatCurrency(double amount) {
            return currencyFormat.format(amount);
        }

        public static String formatNumber(double number) {
            return decimalFormat.format(number);
        }

        public static double parseCurrency(String currencyStr) {
            if (currencyStr == null || currencyStr.isEmpty()) {
                return 0;
            }
            try {
                String cleanStr = currencyStr.replaceAll("[₫\\s]", "").replace(",", "");
                return Double.parseDouble(cleanStr);
            } catch (NumberFormatException e) {
                System.err.println("Lỗi khi chuyển đổi chuỗi tiền tệ: " + e.getMessage());
                return 0;
            }
        }

        public static double parseNumber(String numberStr) {
            if (numberStr == null || numberStr.isEmpty()) {
                return 0;
            }
            try {
                String cleanStr = numberStr.replaceAll("[,\\s]", "").trim();
                return Double.parseDouble(cleanStr);
            } catch (NumberFormatException e) {
                System.err.println("Lỗi khi chuyển đổi chuỗi số: " + e.getMessage());
                return 0;
            }
        }

        public static boolean isNumeric(String str) {
            if (str == null || str.isEmpty()) {
                return false;
            }
            String cleanStr = str.replaceAll("[,\\s]", "").trim();
            return cleanStr.matches("-?\\d+(\\.\\d+)?");
        }

        public static boolean isInteger(String str) {
            if (str == null || str.isEmpty()) {
                return false;
            }
            return str.matches("-?\\d+");
        }

        public static int parseInt(String str, int defaultValue) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }

        public static double parseDouble(String str, double defaultValue) {
            try {
                return Double.parseDouble(str);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
    }