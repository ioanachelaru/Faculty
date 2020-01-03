package Services;

import Controller.ProductController;
import java.util.concurrent.Future;

public class CheckerClass extends Thread{
    private ProductController productController;
    private Boolean stop;

    public CheckerClass(ProductController productController) {
        this.productController = productController;
        this.stop = false;
    }


    public void Stop(){
        this.stop = true;
    }

    @Override
    public void run() {
        while(!stop) {
            Future<Void> v = productController.Check();
            try {
                v.get();
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
