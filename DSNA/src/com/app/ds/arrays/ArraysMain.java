package com.app.ds.arrays;

public class ArraysMain {

	static int sortedArr[] = { 1, 5, 10, 18, 25, 35, 45, 55, 77, 100 };
	
	static int sortedRotatedArr[] = {18, 25, 35, 45, 55, 77, 100, 1, 5, 10 };
	
	static int rearrangeArr[] = {-1, -1, 6, 1, 9, 3, 2, -1, 4, -1};
	
	static ArrayUtils utils = new ArrayUtils();
	public static void main(String[] args) {
		try {
			System.out.println("Start");
			//arraySearch();
			//rotatedArrSearch();
			rearrangeArr();
			System.out.println("End");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static void arraySearch() throws Exception {
		ArraySearch search = new ArraySearch();
		int key = 2;
		int index = search.binarySearch(sortedArr, 0, sortedArr.length - 1, key);
		System.out.println("index :: " + index);
	}

	static void rotatedArrSearch() throws Exception{
		ArraySearch search = new ArraySearch();
		int key = 5;
		int index = search.searchInSortedRotatedArray(sortedRotatedArr, 0, sortedRotatedArr.length - 1, key);
		System.out.println("index :: " + index);
	}

	static void rearrangeArr() throws Exception{
		ArrayRearrange a = new ArrayRearrange();
		a.rearrangeArr(rearrangeArr);
		utils.printArr(rearrangeArr);
	}
}
