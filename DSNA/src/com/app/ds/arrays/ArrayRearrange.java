package com.app.ds.arrays;

public class ArrayRearrange {
	
	/*
	 * Rearrange array such that a[i] = i
	 * {-1, -1, 6, 1, 9, 3, 2, -1, 4, -1}
	 */
	public void rearrangeArr(int arr[]) throws Exception{
		for(int i = 0; i < arr.length; i++) {
			int ele = arr[i];
			if(i == ele)
				continue;
			while(ele != -1 && ele != i) {
				int temp = arr[ele];
				arr[ele] = ele;
				ele = temp;				
				arr[i] = -1;
			}
		}
		
		
	}
	
	/*
	 * Rearrange array in alternating positive & negative items with O(1) extra space. Resultant array should maintain the order
	 * Input:  arr[] = {1, 2, 3, -4, -1, 4}
     * output: arr[] = {-4, 1, -1, 2, 3, 4}
	 */
	
	public void rearrangeAlternatePosNeg(int arr[]) throws Exception{
		
		
	}

}
