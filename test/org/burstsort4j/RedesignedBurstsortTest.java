/*
 * Copyright (C) 2009  Nathan Fiedler
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the RedesignedBurstsort class.
 *
 * @author Nathan Fiedler
 */
public class RedesignedBurstsortTest {

    @Test
    public void testArguments() {
        RedesignedBurstsort.sort(null);
        RedesignedBurstsort.sort(new String[0]);
        String[] arr = new String[] { "a" };
        RedesignedBurstsort.sort(arr);
        arr = new String[] { "b", "a" };
        RedesignedBurstsort.sort(arr);
        assertTrue(Tests.isSorted(arr));
        arr = new String[] { "c", "b", "a" };
        RedesignedBurstsort.sort(arr);
        assertTrue(Tests.isSorted(arr));
    }

    @Test
    public void testDictWords() {
        try {
            // Use the large dictionary rather than the trivial one.
            List<String> data = Tests.loadData("dictwords.gz", true);
            Collections.shuffle(data);
            String[] arr = data.toArray(new String[data.size()]);
            System.out.format("\nDictionary words (large):\n");
            RedesignedBurstsort.sort(arr, System.out);
            assertTrue(Tests.isSorted(arr));
        } catch (IOException ioe) {
            fail(ioe.toString());
        }
    }

    @Test
    public void testDictWordsParallel() {
        try {
            // Use the large dictionary rather than the trivial one.
            List<String> data = Tests.loadData("dictwords.gz", true);
            Collections.shuffle(data);
            String[] arr = data.toArray(new String[data.size()]);
            try {
                RedesignedBurstsort.sortThreadPool(arr);
            } catch (InterruptedException ie) {
                fail(ie.toString());
            }
            assertTrue(Tests.isSorted(arr));
        } catch (IOException ioe) {
            fail(ioe.toString());
        }
    }

    @Test
    public void testSorted() {
        try {
            List<String> data = Tests.loadData();
            Collections.sort(data);
            String[] arr = data.toArray(new String[data.size()]);
            System.out.format("\nDictionary words (sorted):\n");
            RedesignedBurstsort.sort(arr, System.out);
            assertTrue(Tests.isSorted(arr));
        } catch (IOException ioe) {
            fail(ioe.toString());
        }
    }

    @Test
    public void testReversed() {
        try {
            List<String> data = Tests.loadData();
            Collections.sort(data);
            Collections.reverse(data);
            String[] arr = data.toArray(new String[data.size()]);
            System.out.format("\nDictionary words (reversed):\n");
            RedesignedBurstsort.sort(arr, System.out);
            assertTrue(Tests.isSorted(arr));
        } catch (IOException ioe) {
            fail(ioe.toString());
        }
    }

    @Test
    public void testRepeated() {
        // Make the size of the set large enough to burst buckets.
        String[] arr = new String[1310720];
        final String STR = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        Arrays.fill(arr, STR);
        System.out.format("\nRepeated 100-A string:\n");
        RedesignedBurstsort.sort(arr, System.out);
        assertTrue(Tests.isRepeated(arr, STR));
    }

    @Test
    public void testRepeatedParallel() {
        // Make the size of the set large enough to burst buckets.
        String[] arr = new String[1310720];
        final String STR = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        Arrays.fill(arr, STR);
        try {
            RedesignedBurstsort.sortThreadPool(arr);
        } catch (InterruptedException ie) {
            fail(ie.toString());
        }
        assertTrue(Tests.isRepeated(arr, STR));
    }

    @Test
    public void testRepeatedCycle() {
        String[] strs = new String[100];
        String seed = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        for (int i = 0, l = 1; i < strs.length; i++, l++) {
            strs[i] = seed.substring(0, l);
        }
        List<String> list = new ArrayList<String>();
        for (int c = 3162300, i = 0; c > 0; i++, c--) {
            list.add(strs[i % strs.length]);
        }
        System.out.format("\nRepeated A strings (cycle):\n");
        String[] arr = list.toArray(new String[list.size()]);
        RedesignedBurstsort.sort(arr, System.out);
        assertTrue(Tests.isSorted(arr));
    }

    @Test
    public void testRepeatedCycleParallel() {
        String[] strs = new String[100];
        String seed = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        for (int i = 0, l = 1; i < strs.length; i++, l++) {
            strs[i] = seed.substring(0, l);
        }
        List<String> list = new ArrayList<String>();
        for (int c = 3162300, i = 0; c > 0; i++, c--) {
            list.add(strs[i % strs.length]);
        }
        String[] arr = list.toArray(new String[list.size()]);
        try {
            RedesignedBurstsort.sortThreadPool(arr);
        } catch (InterruptedException ie) {
            fail(ie.toString());
        }
        assertTrue(Tests.isSorted(arr));
    }

    @Test
    public void testRandom() {
        List<String> data = Tests.generateData(1000000, 100);
        String[] arr = data.toArray(new String[data.size()]);
        System.out.format("\nRandom strings:\n");
        RedesignedBurstsort.sort(arr, System.out);
        assertTrue(Tests.isSorted(arr));
    }

    @Test
    public void testHamlet() {
        try {
            List<String> data = Tests.loadData("hamletwords");
            Collections.shuffle(data);
            String[] arr = data.toArray(new String[data.size()]);
            System.out.format("\nHamlet words:\n");
            RedesignedBurstsort.sort(arr, System.out);
            assertTrue(Tests.isSorted(arr));
        } catch (IOException ioe) {
            fail(ioe.toString());
        }
    }

    @Test
    public void testDictCalls() {
        try {
            List<String> data = Tests.loadData("dictcalls.gz", true);
            String[] arr = data.toArray(new String[data.size()]);
            System.out.format("\nLibrary calls:\n");
            RedesignedBurstsort.sort(arr, System.out);
            assertTrue(Tests.isSorted(arr));
        } catch (IOException ioe) {
            fail(ioe.toString());
        }
    }
}
