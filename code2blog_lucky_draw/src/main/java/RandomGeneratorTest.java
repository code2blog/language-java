import org.junit.Test;

import java.util.Random;

public class RandomGeneratorTest {

    @Test
    public void shouldPrintRandomNumbers(){
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(3));
        }
    }
}
