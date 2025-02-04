package splitwise.constants;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Constants {
    private Constants() {

    }

    public final static BigDecimal EPSILON = new BigDecimal("0.01");
    public final static RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    public final static Integer ROUNDING_SCALE = 2;
}
