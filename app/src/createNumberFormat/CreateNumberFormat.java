package createNumberFormat;
import java.text.NumberFormat;

public class CreateNumberFormat {
    public String setFormat(int num) {
        NumberFormat nf = NumberFormat.getInstance();
        String formatted = nf.format(num).replace(",", ".");
        return formatted;
    }
}
