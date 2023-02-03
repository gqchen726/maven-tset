package ga.tianyuge.test;

import ga.tianyuge.bean.A;
import ga.tianyuge.service.impl.AServiceImpl;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/11/10 16:22
 */
public class OverWriteTest {
    public static void main(String[] args) {
//        new OverWriteTest().a();
//        new OverWriteTest().b();
        new OverWriteTest().c();
        new OverWriteTest().d();
    }

    private void a() {
        System.out.println("private a()");
        new AServiceImpl().a();
    }

    public void b() {
        System.out.println("public b()");
        new AServiceImpl().a();
    }

    private void c() {
        System.out.println("private c()");
        new A().a();
    }

    public void d() {
        System.out.println("public d()");
        new A().a();
    }
}
