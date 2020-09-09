package com.app.ds.arrays;

public class ArraySearch {

	/* Binary search is used to search an integer value in sorted array
	 * Time Complexity O(log n)
	 */
	public int binarySearch(int arr[], int low, int high, int element) throws Exception {
		if (high < low)
			return -1;
		int mid = (low + high) / 2;
		if (arr[mid] == element)
			return mid;
		if (arr[mid] > element)
			return binarySearch(arr, low, mid - 1, element);

		return binarySearch(arr, low + 1, high, element);

	}
	
	/*
	 * Search an element in a sorted and rotated array
	 */
	public int searchInSortedRotatedArray(int arr[], int l, int h, int key) throws Exception {
		int pivot = findPivot(arr, 0, arr.length - 1);
		System.out.println("Pivot ::" + pivot);
		if(pivot == -1)
			return -1;
		if(arr[pivot] == key)
			return pivot;
		if(arr[0] < key)
			return binarySearch(arr, l, pivot - 1, key);
		return binarySearch(arr, pivot + 1, h, key);
		
	}

	static int findPivot(int arr[], int low, int high) 
    { 
        // base cases 
        if (high < low) 
            return -1; 
        if (high == low) 
            return low; 
  
        /* low + (high - low)/2; */
        int mid = (low + high) / 2; 
        if (mid < high && arr[mid] > arr[mid + 1]) 
            return mid; 
        if (mid > low && arr[mid] < arr[mid - 1]) 
            return (mid - 1); 
        if (arr[low] >= arr[mid]) 
            return findPivot(arr, low, mid - 1); 
        return findPivot(arr, mid + 1, high); 
    } 

}
