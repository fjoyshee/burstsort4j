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
 * Unit tests for the LazyFunnelsort class.
 *
 * @author Nathan Fiedler
 */
public class LazyFunnelsortTest {

    @Test
    public void testInsertionMerge() {
        try {
            // Read in a small set of strings for easy debugging.
            List<String> data = Tests.loadData();
            Collections.shuffle(data);
            data = data.subList(0, 20);
            String[] arr = data.toArray(new String[data.size()]);
            // Split into separate arrays and sort each of them.
            String[] a1 = new String[5];
            System.arraycopy(arr, 0, a1, 0, 5);
            Arrays.sort(a1);
            String[] a2 = new String[5];
            System.arraycopy(arr, 5, a2, 0, 5);
            Arrays.sort(a2);
            String[] a3 = new String[5];
            System.arraycopy(arr, 10, a3, 0, 5);
            Arrays.sort(a3);
            String[] a4 = new String[5];
            System.arraycopy(arr, 15, a4, 0, 5);
            Arrays.sort(a4);
            // Set up the inputs for the insertion d-way merger.
            List<String[]> inputs = new ArrayList<String[]>();
            inputs.add(a1);
            inputs.add(a2);
            inputs.add(a3);
            inputs.add(a4);
            String[] output = new String[20];
            // Test the merger.
            LazyFunnelsort.insertionMerge(inputs, output, 0);
            assertTrue(Tests.isSorted(output));
        } catch (IOException ioe) {
            fail(ioe.toString());
        }
    }

//    @Test
//    public void testArguments() {
//        Burstsort.sort(null);
//        Burstsort.sort(new String[0]);
//        String[] arr = new String[] { "a" };
//        Burstsort.sort(arr);
//        arr = new String[] { "b", "a" };
//        Burstsort.sort(arr);
//        assertTrue(Tests.isSorted(arr));
//        arr = new String[] { "c", "b", "a" };
//        Burstsort.sort(arr);
//        assertTrue(Tests.isSorted(arr));
//    }
//
//    @Test
//    public void testDictWords() {
//        try {
//            // Use the large dictionary rather than the trivial one.
//            List<String> data = Tests.loadData("dictwords.gz", true);
//            Collections.shuffle(data);
//            String[] arr = data.toArray(new String[data.size()]);
//            System.out.format("\nDictionary words (large):\n");
//            Burstsort.sort(arr, System.out);
//            assertTrue(Tests.isSorted(arr));
//        } catch (IOException ioe) {
//            fail(ioe.toString());
//        }
//    }
//
//    @Test
//    public void testDictWordsParallel() {
//        try {
//            // Use the large dictionary rather than the trivial one.
//            List<String> data = Tests.loadData("dictwords.gz", true);
//            Collections.shuffle(data);
//            String[] arr = data.toArray(new String[data.size()]);
//            try {
//                Burstsort.sortThreadPool(arr);
//            } catch (InterruptedException ie) {
//                fail(ie.toString());
//            }
//            assertTrue(Tests.isSorted(arr));
//        } catch (IOException ioe) {
//            fail(ioe.toString());
//        }
//    }
//
//    @Test
//    public void testSorted() {
//        try {
//            List<String> data = Tests.loadData();
//            Collections.sort(data);
//            String[] arr = data.toArray(new String[data.size()]);
//            System.out.format("\nDictionary words (sorted):\n");
//            Burstsort.sort(arr, System.out);
//            assertTrue(Tests.isSorted(arr));
//        } catch (IOException ioe) {
//            fail(ioe.toString());
//        }
//    }
//
//    @Test
//    public void testReversed() {
//        try {
//            List<String> data = Tests.loadData();
//            Collections.sort(data);
//            Collections.reverse(data);
//            String[] arr = data.toArray(new String[data.size()]);
//            System.out.format("\nDictionary words (reversed):\n");
//            Burstsort.sort(arr, System.out);
//            assertTrue(Tests.isSorted(arr));
//        } catch (IOException ioe) {
//            fail(ioe.toString());
//        }
//    }
//
//    @Test
//    public void testRepeated() {
//        // Make the size of the set large enough to burst buckets.
//        String[] arr = new String[1310720];
//        final String STR = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
//                    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
//        Arrays.fill(arr, STR);
//        System.out.format("\nRepeated 100-A string:\n");
//        Burstsort.sort(arr, System.out);
//        assertTrue(Tests.isRepeated(arr, STR));
//    }
//
//    @Test
//    public void testRepeatedParallel() {
//        // Make the size of the set large enough to burst buckets.
//        String[] arr = new String[1310720];
//        final String STR = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
//                    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
//        Arrays.fill(arr, STR);
//        try {
//            Burstsort.sortThreadPool(arr);
//        } catch (InterruptedException ie) {
//            fail(ie.toString());
//        }
//        assertTrue(Tests.isRepeated(arr, STR));
//    }
//
//    @Test
//    public void testRepeatedCycle() {
//        String[] strs = new String[100];
//        String seed = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
//                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
//        for (int i = 0, l = 1; i < strs.length; i++, l++) {
//            strs[i] = seed.substring(0, l);
//        }
//        List<String> list = new ArrayList<String>();
//        for (int c = 3162300, i = 0; c > 0; i++, c--) {
//            list.add(strs[i % strs.length]);
//        }
//        System.out.format("\nRepeated A strings (cycle):\n");
//        String[] arr = list.toArray(new String[list.size()]);
//        Burstsort.sort(arr, System.out);
//        assertTrue(Tests.isSorted(arr));
//    }
//
//    @Test
//    public void testRepeatedCycleParallel() {
//        String[] strs = new String[100];
//        String seed = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
//                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
//        for (int i = 0, l = 1; i < strs.length; i++, l++) {
//            strs[i] = seed.substring(0, l);
//        }
//        List<String> list = new ArrayList<String>();
//        for (int c = 3162300, i = 0; c > 0; i++, c--) {
//            list.add(strs[i % strs.length]);
//        }
//        String[] arr = list.toArray(new String[list.size()]);
//        try {
//            Burstsort.sortThreadPool(arr);
//        } catch (InterruptedException ie) {
//            fail(ie.toString());
//        }
//        assertTrue(Tests.isSorted(arr));
//    }
//
//    @Test
//    public void testRandom() {
//        List<String> data = Tests.generateData(1000000, 100);
//        String[] arr = data.toArray(new String[data.size()]);
//        System.out.format("\nRandom strings:\n");
//        Burstsort.sort(arr, System.out);
//        assertTrue(Tests.isSorted(arr));
//    }
//
//    @Test
//    public void testHamlet() {
//        try {
//            List<String> data = Tests.loadData("hamletwords");
//            Collections.shuffle(data);
//            String[] arr = data.toArray(new String[data.size()]);
//            System.out.format("\nHamlet words:\n");
//            Burstsort.sort(arr, System.out);
//            assertTrue(Tests.isSorted(arr));
//        } catch (IOException ioe) {
//            fail(ioe.toString());
//        }
//    }
//
//    @Test
//    public void testDictCalls() {
//        try {
//            List<String> data = Tests.loadData("dictcalls.gz", true);
//            String[] arr = data.toArray(new String[data.size()]);
//            System.out.format("\nLibrary calls:\n");
//            Burstsort.sort(arr, System.out);
//            assertTrue(Tests.isSorted(arr));
//        } catch (IOException ioe) {
//            fail(ioe.toString());
//        }
//    }
}