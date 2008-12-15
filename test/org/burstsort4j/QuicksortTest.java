/*
 * Copyright (C) 2008  Nathan Fiedler
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * $Id$
 */

package org.burstsort4j;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the Quicksort class.
 *
 * @author nfiedler
 */
public class QuicksortTest {
    private static List<String> data;

    public QuicksortTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        try {
            data = Tests.loadData();
        } catch (IOException ioe) {
            fail(ioe.toString());
        }
    }

    @Before
    public void setUp() {
        Collections.shuffle(data);
    }

    @Test
    public void testQuicksort() {
        String[] arr = data.toArray(new String[data.size()]);
        Quicksort.quicksort(arr);
        assertTrue(Tests.isSorted(arr));
        // Now sort the sorted list: should be fine.
        Quicksort.quicksort(arr);
        assertTrue(Tests.isSorted(arr));
        // Sort a reverse sorted list
        Collections.reverse(data);
        arr = data.toArray(new String[data.size()]);
        Quicksort.quicksort(arr);
        assertTrue(Tests.isSorted(arr));
    }
}
