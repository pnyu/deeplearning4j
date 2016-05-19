package jcuda.jcublas.ops;

import org.junit.Before;
import org.junit.Test;
import org.nd4j.jita.allocator.enums.AllocationStatus;
import org.nd4j.jita.conf.Configuration;
import org.nd4j.jita.conf.CudaEnvironment;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ops.impl.transforms.RectifedLinear;
import org.nd4j.linalg.api.ops.impl.transforms.SoftMax;
import org.nd4j.linalg.api.ops.impl.transforms.arithmetic.AddOp;
import org.nd4j.linalg.factory.Nd4j;

/**
 * @author AlexDBlack
 * @author raver119@gmail.com
 */
public class EndlessTests {
    private static final int RUN_LIMIT = 1000000;

    @Before
    public void setUp() {
        CudaEnvironment.getInstance().getConfiguration()
                .setFirstMemory(AllocationStatus.DEVICE)
                .setExecutionModel(Configuration.ExecutionModel.SEQUENTIAL)
                .setAllocationModel(Configuration.AllocationModel.CACHE_ALL)
                .setMaximumBlockSize(256)
                .enableDebug(true)
                .setVerbose(true);


        System.out.println("Init called");
    }

    @Test
    public void testTransformsForeverSingle(){
        INDArray arr = Nd4j.ones(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            Nd4j.getExecutioner().exec(new RectifedLinear(arr));
        }
    }

    @Test
    public void testTransformsForeverSingle2(){
        INDArray arr = Nd4j.ones(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            Nd4j.getExecutioner().exec(new SoftMax(arr));
        }
    }

    @Test
    public void testTransformsForeverPairwise(){
        INDArray arr = Nd4j.ones(100,100);
        INDArray arr2 = Nd4j.ones(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            Nd4j.getExecutioner().exec(new AddOp(arr,arr2,arr));
        }
    }

    @Test
    public void testAccumForeverFull(){
        INDArray arr = Nd4j.ones(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            arr.sumNumber();
        }
    }

    @Test
    public void testAccumForeverAlongDimension(){
        INDArray arr = Nd4j.ones(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            arr.sum(0);
        }
    }

    @Test
    public void testAccumForeverAlongDimensions(){
        INDArray arr = Nd4j.linspace(1, 10000, 10000).reshape(10, 10, 100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            arr.sum(0,1);
        }
    }

    @Test
    public void testIndexAccumForeverFull(){
        INDArray arr = Nd4j.ones(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            Nd4j.argMax(arr,Integer.MAX_VALUE);
        }
    }

    @Test
    public void testStdDevForeverFull(){
        INDArray arr = Nd4j.ones(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            arr.stdNumber();
        }
    }

    @Test
    public void testIndexAccumForeverAlongDimension(){
        INDArray arr = Nd4j.ones(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            Nd4j.argMax(arr,0);
        }
    }

    @Test
    public void testIndexAccumForeverAlongDimensions(){
        INDArray arr = Nd4j.linspace(1, 10000, 10000).reshape(10, 10, 100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            Nd4j.argMax(arr,0, 1);
        }
    }


    @Test
    public void testBroadcastForever(){
        INDArray arr = Nd4j.ones(100,100);
        INDArray arr2 = Nd4j.ones(1,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            arr.addiRowVector(arr2);
        }
    }

    @Test
    public void testScalarForever(){
        INDArray arr = Nd4j.zeros(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            arr.addi(1.0);
        }
    }
}
