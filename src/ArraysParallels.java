class ArraysParallels {
    public static final int SIZE = 10000000;
    private int half = SIZE/2;
    private double[] arr;
    private long initialTime;

    public ArraysParallels() {
        this.arr = new double[SIZE];
        for (int i = 0; i<SIZE; i++) {
            arr[i]=1;
        }
        this.initialTime = System.currentTimeMillis();
    }

    public void singleThread () {
        for (int i=0; i<SIZE; i++) {
            arr[i] = (arr[i] * Math.sin(0.2 + (double) i / 5) * Math.cos(0.2 + (double) i / 5) * Math.cos(0.4 + (double) i / 2));
        }
        System.out.println("singleThread time: " + (System.currentTimeMillis() - initialTime));
    }

    public void doubleThread () {
        double [] arr1 = new double[half];
        double [] arr2 = new double[half];
        double [] arr3 = new double[SIZE];

        System.arraycopy(arr, 0, arr1, 0, half);
        System.arraycopy(arr, 0, arr2, 0, half);

        Thread t1 = new Thread(()-> {
            for (int i=0; i<half; i++)
            {
                arr1 [i] = (arr1 [i] * Math.sin(0.2 + (double) i / 5) * Math.cos(0.2 + (double) i / 5) * Math.cos(0.4 + (double) i / 2));
            }
            System.out.println("Thread1 time: " + (System.currentTimeMillis()-initialTime));
        });

        Thread t2 = new Thread(()-> {
            for (int i=0; i<half; i++)
            {
                arr2[i] = (arr2[i] * Math.sin(0.2 + (double) (half + i) / 5) * Math.cos(0.2 + (double) (half + i) / 5) * Math.cos(0.4 + (double) (half + i) / 2));
            }
            System.out.println("Thread2 time: " + (System.currentTimeMillis()-initialTime));
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(arr1,0,arr3,0,half);
        System.arraycopy(arr2,0,arr3,half,half);
        System.out.println("doubleThread time: " + (System.currentTimeMillis()-initialTime));

    }

    public static void main(String[] args) {
        ArraysParallels one = new ArraysParallels();
        one.singleThread();
        ArraysParallels two = new ArraysParallels();
        two.doubleThread();
    }
}