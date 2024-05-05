import com.musicallyanna.mosfet.time.MakerspaceEvent;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;

public class TestHasOverlap {

    @Test
    public void testHasOverlapWithNoOverlap1() {

        final LocalDateTime start1 = LocalDateTime.of(2024, Month.MAY, 3, 22, 21, 0, 0);
        final LocalDateTime end1 = LocalDateTime.of(2024, Month.MAY, 3, 23, 21, 0, 0);

        final LocalDateTime start2 = LocalDateTime.of(2024, Month.MAY, 4, 10, 15, 0, 0);
        final LocalDateTime end2 = LocalDateTime.of(2024, Month.MAY, 4, 11, 30, 0, 0);

        final MakerspaceEvent e1 = new MakerspaceEvent(start1, end1);
        final MakerspaceEvent e2 = new MakerspaceEvent(start2, end2);

        final boolean expectedResult = false;
        final boolean actualResult = e1.hasOverlap(e2);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testHasOverlapWithNoOverlap2() {

        final LocalDateTime start1 = LocalDateTime.of(2024, Month.MAY, 4, 10, 15, 0, 0);
        final LocalDateTime end1 = LocalDateTime.of(2024, Month.MAY, 4, 11, 30, 0, 0);

        final LocalDateTime start2 = LocalDateTime.of(2024, Month.MAY, 3, 22, 21, 0, 0);
        final LocalDateTime end2 = LocalDateTime.of(2024, Month.MAY, 3, 23, 21, 0, 0);

        final MakerspaceEvent e1 = new MakerspaceEvent(start1, end1);
        final MakerspaceEvent e2 = new MakerspaceEvent(start2, end2);

        final boolean expectedResult = false;
        final boolean actualResult = e1.hasOverlap(e2);

        assertEquals(expectedResult, actualResult);
    }

    // TODO: add more test cases
}