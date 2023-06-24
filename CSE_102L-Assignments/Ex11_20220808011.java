import java.util.HashMap;

public class Ex11_20220808011 {
    public static void main(String[] args) {
    }

    public static int numOfTriplets(int[] arr, int sum) {
        int n = arr.length;
        int count = 0;
        mySort(arr);
        for (int i = 0; i < n - 2; i++) {
            int left = i + 1;
            int right = n - 1;
            while (left < right) {
                int currentSum = arr[i] + arr[left] + arr[right];
                if (currentSum < sum) {
                    count += right - left;
                    left++;
                } else {
                    right--;
                }
            }
        }
        return count;
    }

    public static int kthSmallest(int[] arr, int k) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            Heap(arr, n, i);
        }
        for (int i = 0; i < k - 1; i++) {
            HelperMethod(arr);
        }
        return HelperMethod(arr);
    }

    public static String subSequence(String str) {
        int max = 0;
        StringBuilder returnVal = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) > max) {
                max = str.charAt(i);
                returnVal.append(str.charAt(i));
            }
        }
        System.out.println("n");
        return String.valueOf(returnVal);
    }

    public static int isSubstring(String str1, String str2) {
        int returnVal = -1;
        int str1_index = 0;
        int str2_index = 0;
        while (str1_index < str1.length()) {
            if (str1.charAt(str1_index) == str2.charAt(str2_index)) {
                if (str2_index == 0) {
                    returnVal = str1_index;
                }
                str2_index++;
                if (str2_index == str2.length()) {
                    return returnVal;
                }
            } else {
                str2_index = 0;
            }
            str1_index++;
        }
        return -1;
    }

    public static void findRepeats(int[] arr, int n) {
        HashMap<Integer, Integer> counter = new HashMap<>();
        for (int index : arr) {
            counter.put(index, counter.getOrDefault(index, 0) + 1);
        }
        for (var returnVal : counter.entrySet()) {
            if (returnVal.getValue() > n) {
                System.out.print(returnVal.getValue());
            }
        }
    }

    public static void mySort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    int temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }
    }

    public static void Heap(int[] arr, int n, int i) {
        int smallest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && arr[left] < arr[smallest]) {
            smallest = left;
        }
        if (right < n && arr[right] < arr[smallest]) {
            smallest = right;
        }
        if (smallest != i) {
            int temp = arr[i];
            arr[i] = arr[smallest];
            arr[smallest] = temp;
            Heap(arr, n, smallest);
        }
    }

    public static int HelperMethod(int[] arr) {
        int n = arr.length;
        int min = arr[0];
        arr[0] = arr[n - 1];
        Heap(arr, n - 1, 0);
        return min;
    }

}

